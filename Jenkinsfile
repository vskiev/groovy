#!/usr/bin/env groovy

node {
    // Git checkout before load source the file
    checkout scm

    // To know files are checked out or not
    sh '''
        ls -lhrt
    '''

    def rootDir = pwd()
    println("Current Directory: " + rootDir)

    // point to exact source file
    //def example = load "${rootDir}/Example.groovy"
    //example.exampleMethod()
    //example.otherExampleMethod()

      def test = load "${WORKSPACE}/test2.groovy"
          test.step1()
        //   test.step2()
        //   test.step3()
}
