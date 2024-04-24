/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout from SCM') {
            steps {
                git branch: 'main', credentialsId: 'github-aji', url: 'https://github.com/fluxions-471/priajiservice.git'
            }
        }

        stage('Build Application') {
            steps {
                sh "mvn clean install"
            }
        }
//         stage("Sonarqube Analysis") {
//                     steps {
//                         script {
//                             def modules = ["apigw", "clients", "customer", "eureka-server", "fraud", "notification"]
//
//                             modules.each { module ->
//                                 dir("${module}") {
//                                     pwd()
//                                     withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
//                                         sh "mvn sonar:sonar"
//                                     }
//                                 }
//                             }
//                         }
//                     }
//                 }
        stage("Sonarqube Analysis") {
                    steps {
                        script {
                            withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
                                sh "mvn sonar:sonar"
                            }
                        }
                    }
                }
//      mvn clean compile jib:build
    }
}
