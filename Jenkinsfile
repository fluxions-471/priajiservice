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

        stage("Sonarqube Analysis") {
                    steps {
                        script {
                            def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification"]

                            modules.each { module ->
                                dir("${module}") {
                                    pwd()
                                    withSonarQubeEnv(credentialsId: 'jenkins-sonarqube-token') {
                                        sh "mvn clean install sonar:sonar"
                                    }
                                }
                            }
                        }
                    }
                }
        stage('Login to Docker Hub') {
                    steps {
                        withCredentials([string(credentialsId: 'dockerhub-aji', variable: 'DOCKER_HUB_TOKEN')]) {
                            sh "echo $DOCKER_HUB_TOKEN | docker login --username priajiabror --password-stdin"
                        }
                    }
                }
        stage("Docker Build & Push Image") {
                    steps {
                        script {
                            def modules = ["apigw", "customer", "eureka-server", "fraud", "notification"]

                            modules.each { module ->
                                dir("${module}") {
                                    pwd()
                                    sh "mvn clean install jib:build"
                                }
                            }
                        }
                    }
                }
        stage('Run Docker Compose') {
                    steps {
                        script {
                            dir('priajiservices') {
                                pwd()
                                sh 'docker compose up -d'
                            }
                        }
                    }
                }
    }
}
