#!/usr/bin/env groovy

node {
    checkout scm

    sh '''
        ls -lhrt
    '''

    def rootDir = pwd()
    println("Current Directory: " + rootDir)


      def test = load "${WORKSPACE}/test2.groovy"
          test.step1()

      def test2 = load "${WORKSPACE}/test2.groovy"          
          test2.step2()

      def test3 = load "${WORKSPACE}/test2.groovy"    
          test3.step3()
}
