#!/usr/bin/env groovy

node {
    checkout scm

    sh '''
        ls -lhrt
    '''

    def rootDir = pwd()
    println("Current Directory: " + rootDir)


      def test = load '${WORKSPACE}/test.groovy'
        test.step1()
        test.step2()
        test.step3()
       



          


}          
