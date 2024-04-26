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
                    def commitMessage1 = sh(script: "git --no-pager log --pretty=format:\"%h %s\" --since=\"1 minute ago\" --until=\"now\" -n 1", returnStdout: true).trim()
                    echo "Commit Message is : ${commitMessage1}"
                    if (commitMessage1 != null){
                        sh "mvn clean package"
                    }
                }
            }
        }
        stage('Check Changes || Push & Pull Docker Image') {
            steps {
                script {
                    def modules = ["amqp", "apigw", "clients", "customer", "eureka-server", "fraud", "notification", "security"]
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
            discordSend description: "Build # ${env.BUILD_NUMBER} - Failed - Current time ${new Date()}", enableArtifactsList: true, footer: '', image: '', link: env.BUILD_URL, result: 'FAILURE', scmWebUrl: 'https://github.com/fluxions-471/priajiservice', showChangeset: true, thumbnail: '', title: env.JOB_NAME, webhookURL: DISCORD_WEBHOOK
        }
        success {
            discordSend description: "Build # ${env.BUILD_NUMBER} - Successfully - Current time ${new Date()}", enableArtifactsList: true, footer: '', image: '', link: env.BUILD_URL, result: 'SUCCESS', scmWebUrl: 'https://github.com/fluxions-471/priajiservice', showChangeset: true, thumbnail: '', title: env.JOB_NAME, webhookURL: DISCORD_WEBHOOK
        }
    }
}