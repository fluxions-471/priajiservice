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
        DISCORD_WEBHOOK = credentials('discord-webhook')
    }
    stages {
        stage("Build Application"){
            steps {
                script{
                    dir('priajiservices') {
                        writeFile file: '/tmp/buildStart.txt', text: new Date().toString()
                        def startDate = new Date().parse('dd/MM/yyyy HH:mm:ss', f.text)
                        echo "Build started at: ${startDate}"
                        sh "mvn clean package"
                    }
                }
            }
        }
        stage('Check Changes || Push & Pull Docker Image') {
            steps {
                script {
                    def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification"]
                    def commitMessage = sh(script: "git log --pretty=format:\"%h %s\" | head -n 1", returnStdout: true).trim()
                    for (module in modules) {
                        if (commitMessage.contains(module)) {
                            echo "Changes detected in module: ${module}"
                            dir("${module}") {
                                docker.withRegistry('', DOCKER_PASS) {
                                    sh "mvn clean install jib:build"
                                    docker.image("${DOCKER_USER}/${module}:latest").pull()
                                }
                            }
                        } else {
                            echo "No Changes in module: ${module}"
                        }
                    }
                }
            }
        }
//         stage('Pull Docker Image') {
//             steps {
//                 script {
//                     def modules = ["apigw", "customer", "eureka-server", "fraud", "notification"]
//                     dir('priajiservices'){
//                         for (module in modules) {
//                             docker.withRegistry('',DOCKER_PASS) {
//                                 docker.image("${DOCKER_USER}/${module}:latest").pull()
//                             }
//                         }
//                     }
//                 }
//             }
//         }
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
    post {
        failure {
            discordSend description: "Build # ${env.BUILD_NUMBER} - Failed within ${env.BUILD_DURATION}", enableArtifactsList: true, footer: '', image: '', link: env.BUILD_URL, result: 'FAILURE', scmWebUrl: 'https://github.com/fluxions-471/priajiservice', showChangeset: true, thumbnail: '', title: env.JOB_NAME, webhookURL: DISCORD_WEBHOOK
        }
        success {
            script {
                def endDate = new Date()
                def tookTime = groovy.time.TimeCategory.minus(endDate, startDate).toString()
                echo "Build ended at: ${endDate}"
                echo "Build took: ${tookTime}"
                discordSend description: "Build # ${env.BUILD_NUMBER} - Successfully completed within ${tookTime}", enableArtifactsList: true, footer: '', image: '', link: env.BUILD_URL, result: 'SUCCESS', scmWebUrl: 'https://github.com/fluxions-471/priajiservice', showChangeset: true, thumbnail: '', title: env.JOB_NAME, webhookURL: DISCORD_WEBHOOK
            }
        }
    }
}