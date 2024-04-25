/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    environment {
        DOCKER_USER = "priajiabror"
        DOCKER_PASS = 'dockerhub-aji2'
        JENKINS_API_TOKEN = credentials('JENKINS_API_TOKEN')
    }
    stages {
        stage("Build Application"){
            steps {
                sh "mvn clean package"
            }
        }
        stage('Check Changes') {
            steps {
                script {
                    def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification"]
                    for (module in modules) {
                        if (fileExists("${module}/.git")) {
                            def changes = sh(script: "git diff --quiet HEAD~ HEAD -- ${module}", returnStatus: true)
                            if (changes == 0) {
                                echo "Changes detected in module: ${module}"
                                buildModule(module)
                            }
                        }
                    }
                }
            }
        }
//         stage("Docker Build & Push Image") {
//             steps {
//                 script {
//                     def modules = ["apigw", "customer", "eureka-server", "fraud", "notification"]
//
//                     modules.each { module ->
//                         dir("${module}") {
//                             pwd()
//                             docker.withRegistry('',DOCKER_PASS) {
//                                 sh "mvn clean install jib:build"
//                             }
//                         }
//                     }
//                 }
//             }
//         }
        stage('Pull Docker Image') {
            steps {
                script {
                    def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification"]
                    for (module in modules) {
                        sh 'docker pull ${module}:tag'
                    }
                }
            }
        }
        stage('Run Docker Compose') {
            steps {
                script {
                    dir('priajiservices') {
                        pwd()
                        docker.withRegistry('',DOCKER_PASS) {
                            sh "docker compose up -d"
                        }
                    }
                }
            }
        }
    }
    def buildModule(module) {
        stage("Build ${module}") {
            steps {
                dir("${module}") {
                    docker.withRegistry('', DOCKER_PASS) {
                        sh "mvn clean install jib:build"
                    }
                }
            }
        }
    }
}
