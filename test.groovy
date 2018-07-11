import groovy.json.JsonBuilder
import groovy.json.JsonParser
import groovy.json.JsonParserType
import groovy.json.JsonSlurper
import groovy.io.*



int getSimpleTest(String url) {
    def get = new URL(url).openConnection();
    def getRC = get.getResponseCode();
    println(getRC); //responce code

    if ((getRC.equals(200)) && (get.getContentType() == "application/json;charset=UTF-8")) {
        def JsonSlurper = new JsonSlurper()
        def object = JsonSlurper.parseText(get.getInputStream().getText());
        println(object);

        return 200;
    }

    if ((getRC.equals(200)) && (get.getContentType() != "application/json;charset=UTF-8")) {

        return 200;
    }


    if (!getRC.equals(200)) {
        return 404;
    }

}



int getWithJsonValidation(String url)
{
    def get = new URL(url).openConnection();
    def getRC = get.getResponseCode();
    println(getRC); //responce code

    if ((getRC.equals(200)) && (get.getContentType() == "application/json;charset=UTF-8")) {
        def JsonSlurper = new JsonSlurper()
        def object = JsonSlurper.parseText(get.getInputStream().getText());
//        println(object);

        HashMap map = new HashMap()
        map.putAll((HashMap) object);
        println(map.toString() + "<----this is map")
        return 200
    }



    if ((getRC.equals(200)) && (get.getContentType() != "application/json;charset=UTF-8")) {

        return 200
    }


    if (!getRC.equals(200)) {
        return 404
    }

}



def postSomething(String url)
{
def post = new URL(url).openConnection();
def message = '{"message":"this is a message"}'
post.setRequestMethod("POST")
post.setDoOutput(true)
post.setRequestProperty("Content-Type", "application/json")
post.getOutputStream().write(message.getBytes("UTF-8"));
def postRC = post.getResponseCode();
println(postRC);
if(postRC.equals(200)) {
    println(post.getInputStream().getText());
}

}




static def getDirList() {


    def dirlist = []

    new File("/home/vsavko/supertest/API_TEST").eachDirRecurse() { file ->
        dirlist << file.getAbsolutePath()
    }//println(dirlist)

return dirlist
}



def getFileList(String [] dirs) {

    def filelist = []

    for (i in dirs) {
        new File(i).eachFile() {
            file -> filelist << file.getAbsolutePath()
//            if (file.getName() == "init")
//               {
//
//               }
//

        }
    }
return filelist
}



def openFileAndGetData(String filePatch)
{
    def emptylist = []
//    String path = "/home/vsavko/file.txt";

      try {
          File file = new File(filePatch)
          emptylist = file.readLines()

//          println(emptylist)
//          println(emptylist.size())
      }
      catch (FileNotFoundException e) {
          println(e.printStackTrace())
      }
      catch (IOException e1) {
          println(e1.printStackTrace() + "file not found")
      }

    return  emptylist.get(0)
}






def LoadJson(String path)
{
//    def inputFile = new File(path)
//    def InputJSON = new JsonSlurper().parse(inputFile, 'UTF-8')
//    return InputJSON

    return new JsonSlurper().parseText(new File(path).text)
}


def saveJson(Object content, String patch)
{
    new File(patch).write(new JsonBuilder(content).toPrettyString())
}




def getDirFiles() {


    for (i in (getDirList())) {
        new File(i).eachFile() { file ->

            if (file.getName() == "init") {
                String data = openFileAndGetData(file.getAbsolutePath())
//                println(data)
                getSimpleTest(data)
                

            }
              if (file.getName() == "input.json") {
                  println(file.getAbsolutePath())
                  def object = LoadJson(file.getAbsolutePath())
                  println(object)
                  HashMap map = new HashMap()
                  map.putAll((HashMap) object)

                  println(map.toString() + "<----this is map")


//                 getWithJsonValidation(data)

              }

              if (file.getName() == "output.json") {
//                  println(file.getAbsolutePath())

              }


        }

    }
}

getDirFiles()











//      def emptylist = []
//      def emptyMap = [:]
//      String path = "/home/vsavko/file.txt";
//
//      try {
//          File file = new File(path)
//          emptylist = file.readLines()
//
//          println(emptylist)
//          println(emptylist.size())
//      }
//      catch (FileNotFoundException e) {
//          println(e.printStackTrace())
//      }
//      catch (IOException e1) {
//          println(e1.printStackTrace() + "file not found")
//      }




//      for (String i in emptylist) {
//          def get = new URL(i).openConnection();
//          def getRC = get.getResponseCode();
//          println(getRC); //responce code
//
//          if (getRC.equals(200)) {
//              //println(get.getInputStream().getText());
//              def JsonSlurper = new JsonSlurper()
//              def object = JsonSlurper.parseText(get.getInputStream().getText());
//              println(object);
//
//              HashMap map = new HashMap()
//              map.putAll((HashMap) object);
//              println(map.toString() + "<----this is map")
//
//          }
//      }


//def parseJSON(JSonText = [] ) {
//    println(JSonText);
//
//}

//
//// POST
//def post = new URL("https://httpbin.org/post").openConnection();
//def message = '{"message":"this is a message"}'
//post.setRequestMethod("POST")
//post.setDoOutput(true)
//post.setRequestProperty("Content-Type", "application/json")
//post.getOutputStream().write(message.getBytes("UTF-8"));
//def postRC = post.getResponseCode();
//println(postRC);
//if(postRC.equals(200)) {
//    println(post.getInputStream().getText());
//}

//====
//reding content urls from file or some other sources to jenkins

