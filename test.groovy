#!/usr/bin/env groovy
import groovy.json.JsonException
import groovy.json.JsonSlurper

class Result {
    boolean mResult = false
    def resultSet = [:]

    def resultADD(int testNumber, String result) {
        resultSet.put(testNumber, result)
    }

    def String getAllResults() {
        return resultSet.toMapString()
    }


    def setMresultTrue()
    {
        mResult = true
    }

    def setMresultFalse()
    {
        mResult = false
    }

    def boolean testPass() {
        for (r in resultSet) {
            def data = r.getValue()
            if (data == "true") {
//                println("we are true")
                setMresultTrue()

            } else {
//                println(" in false")
                setMresultFalse()
                break
            }
        }

     return mResult
    }
}

def getInitLinks() {
    def lst = []
    new File("${WORKSPACE}/src/API_TEST/").eachFile() { file ->
        pathName = "${WORKSPACE}/src/API_TEST/" + file.getName()
        fileContent = new File(pathName + "/init").getText()
        lst.add(fileContent)
    }

    return lst
}

def getInputContent() {
    new File("${WORKSPACE}/src/API_TEST").eachFile() { file ->
        pathName = "${WORKSPACE}/src/API_TEST/" + file.getName()
        return new JsonSlurper().parseText(new File(pathName + "/input.json").getText("UTF-8"))
    }
}

def step1() {
    def links = getInitLinks()
    for (int i = 0; i < links.size(); i++) {
        def get = new URL((String) links.get(i)).openConnection()
        def getRC = get.getResponseCode()

        if (getRC.equals(200)) {
            println("Link OK " + "in test " + i + " " + links.get(i))
        } else {
            println("Link NOT OK " + "in test " + i + " " + links.get(i))
        }

    }
}

def fileExist(String path) {
    def filePath = path

    def file = new File(filePath)

    assert file.exists(): "file not found"
    assert file.canRead(): "file cannot be read"

    def jsonSlurper = new JsonSlurper()
    def object

    try {
        object = jsonSlurper.parse(file)
    } catch (JsonException e) {
        println "File is not valid"
        throw e
    }

    return object
}


def Post(String url, String message) {
    def post = new URL(url).openConnection();
//    message = '{"message":"this is a message"}'
    post.setRequestMethod("POST")
    post.setDoOutput(true)
    post.setRequestProperty("Content-Type", "application/json")
    post.getOutputStream().write(message.getBytes("UTF-8"));
    def postRC = post.getResponseCode();
    println(postRC);
    if (postRC.equals(200)) {
        println(post.getInputStream().getText())
    }
    if (postRC.equals(201)) {
        println(post.getInputStream().getText())
    }

}


def compareJsons(String url, String filePatch) {
    def get = new URL(url).openConnection()
    def getRC = get.getResponseCode()
    if (getRC.equals(200)) {
        def JsonSlurper = new JsonSlurper()
        def map1 = JsonSlurper.parseText(get.getInputStream().getText())
        def map2 = JsonSlurper.parseText(new File(filePatch).text)

        if (map1 == map2) {
            println("jsons are valid ")
            return true
        } else {
            print("jsons NOT valid")
            return false
        }
    }
}


def step2() {
    new File("${WORKSPACE}/src/API_TEST").eachFile() { file ->
        pathName = "${WORKSPACE}/src/API_TEST/" + file.getName()
        if (new File(pathName + "/init").exists() && new File(pathName + "/input.json").exists()) {
            println(pathName)
            def link = new File(pathName + "/init").text
            def filepatch = pathName + "/input.json"
            def status = compareJsons(link, filepatch)
            if (status) {
                println("json valid in STEP 2 by patch: " + pathName)

            } else {
                println("json NOT valid in STEP 2 by patch: " + pathName)
            }
            println(status)

        }
    }
}


def step3() {
    new File("${WORKSPACE}/src/API_TEST").eachFile() { file ->
        pathName = "${WORKSPACE}/src/API_TEST/" + file.getName()
        if (new File(pathName + "/init").exists() && new File(pathName + "/input.json").exists() &&
                new File(pathName + "/output.json").exists()) {
            println(pathName)
            def link = new File(pathName + "/init").text
            def inputpatch = pathName + "/input.json"
            def outputpatch = new File(pathName + "/output.json").text
            println("3 files are present")
            def status = compareJsons(link, inputpatch)
            if (status) {
                println("json valid on STEP 3 by patch: " + pathName)
                println("posting json")
                Post(link, outputpatch)
            } else {
                println("json NOT valid in STEP 3 by patch: " + pathName)
                println("json posting SKIPPED")
            }
            println(status)

        }
    }

}

step1()

step2()

step3()
def rr = new Result()
rr.resultADD(1, "true")
rr.resultADD(2, "true")
rr.resultADD(3, "true")
rr.resultADD(4, "true")

println(rr.getAllResults())
println(rr.testPass())

return this