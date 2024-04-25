/* groovylint-disable-next-line CompileStatic */
pipeline {
    agent any
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    environment {
        DOCKER_USER = "priajiabror"
        DOCKER_PASS = credentials("docker-aji")
        JENKINS_API_TOKEN = credentials("JENKINS_API_TOKEN")
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
        stage("Build Application"){
            steps {
                sh "mvn clean package"
            }

        }
//         stage("Sonarqube Analysis") {
//             steps {
//                 script {
//                     def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification"]
//
//                     modules.each { module ->
//                         dir("${module}") {
//                             pwd()
//                             withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
//                                 sh "mvn clean install sonar:sonar"
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//         stage('Login to Docker Hub') {
//                     steps {
//                         withCredentials([string(credentialsId: 'dockerhub-aji', variable: 'DOCKER_HUB_TOKEN')]) {
//                             sh "echo $DOCKER_HUB_TOKEN | docker login --username priajiabror --password-stdin"
//                         }
//                     }
//                 }
//         stage("Docker Build & Push Image") {
//                     steps {
//                         script {
//                             def modules = ["apigw", "customer", "eureka-server", "fraud", "notification"]
//
//                             modules.each { module ->
//                                 dir("${module}") {
//                                     pwd()
//                                     sh "mvn clean install jib:build"
//                                 }
//                             }
//                         }
//                     }
//                 }
        stage("Build & Push Docker Image") {
                    steps {
                        script {
                            def modules = ["apigw", "customer", "eureka-server", "fraud", "notification"]

                            modules.each { module ->
                                dir("${module}") {
                                    def app_name = ${module}
                                    def image_name = "${DOCKER_USER}/${APP_NAME}"
                                    def image_tag = "latest"
                                    docker.withRegistry('',DOCKER_PASS) {
                                            docker_image = docker.build "${image_name}"
                                    }
                                    docker.withRegistry('',DOCKER_PASS) {
                                        docker_image.push('latest')
                                    }
                                }
                            }
                        }
                    }

                }
//         stage('Run Docker Compose') {
//             steps {
//                 script {
//                     dir('priajiservices') {
//                         pwd()
//                         sh 'docker compose up -d'
//                     }
//                 }
//             }
//         }
    }
}
