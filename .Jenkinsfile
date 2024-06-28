/*def buildNumber = currentBuild.number
pipeline {
    agent any
    tools {
        jdk 'Java_Home'
        gradle 'GRADLE_HOME'
    }
    stages {
        stage('Checkout'){
            steps {
                 script {
                        git branch: 'develop',
                            credentialsId: 'UsingAccessToken',
                            url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git'
                        slackSend color: 'good', message: "/#########" + String.format('%tF %<tH:%<tM:%<tS', java.time.LocalDateTime.now()) + "#########/"
                    }
            }
            post {
                success {
                    slackSend color: 'bad', message: "Git Checkout: Success"
                }
                failure {
                    script {
                        slackSend color: 'bad', message: "Git Checkout: Failure"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Git Checkout Failure at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
                aborted {
                    script {
                        slackSend color: 'bad', message: "Git Checkout: Aborted"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                 "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                 "Builts Branch: Git Checkout Aborted at Build #" + buildNumber + "\r" +
                                 "                                                      "
                        slackSend color: 'bad', message: "Checkout: Aborted"
                    }
                }
            }
        }
        stage('Build'){
            steps {
                script {
                    bat "gradle clean build"
                }
            }
            post {
                success {
                    slackSend color: 'bad', message: "Build: Success"
                }
                failure {
                    script {
                        slackSend color: 'bad', message: "Build: Failure"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Build Failure at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
                aborted {
                    script {
                        slackSend color: 'bad', message: "Build: Aborted"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Build Aborted at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
            }
        }
        stage('Test'){
            steps{
                bat "gradle test"
            }
            post {
                success {
                    slackSend color: 'bad', message: "Test: Success"
                }
                failure {
                    script {
                        slackSend color: 'bad', message: "Test: Failure"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Test Failure at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
                aborted {
                    script {
                        slackSend color: 'bad', message: "Test: Aborted"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Test Aborted at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                bat "gradle jar"
            }
            post {
                success {
                    slackSend color: 'bad', message: "Deploy: Success"
                }
                failure {
                    script {
                        slackSend color: 'bad', message: "Deploy: Failure"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Deploy Failure at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
                aborted {
                    script {
                        slackSend color: 'bad', message: "Deploy: Aborted"
                        def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                        def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                                "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                                "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                                "Builts Branch: Deploy Aborted at Build #" + buildNumber + "\r" +
                                "                                                      "
                        slackSend color: 'bad', message: strResult
                    }
                }
            }
        }
        stage('Notification') { 
            steps {
                script {
                    def scmVars = checkout([$class: 'GitSCM', branches: [[name: 'develop']], 
                            userRemoteConfigs: [[url: 'https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git']]])
                    def strResult = "Revision: <" + scmVars.GIT_COMMIT + ">" + "\r" + 
                            "Repository: " + "https://gitlap.brycen.com.vn/xeex/xeex_chat_be.git" + "\r" +
                             "                * " + "refs/remotes/" + scmVars.GIT_BRANCH + "\r" +
                             "Builts Branch: Success at Build #" + buildNumber + "\r" +
                             "                                                      "
                    slackSend color: 'good', message: strResult
                }
            }
        }
	}
}
*/