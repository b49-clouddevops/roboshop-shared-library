def lintChecks() {
  sh '''
     # echo installing jslint
     # npm install jslint
     # ~/node_modules/jslint/bin/jslint.js server.js || true
    echo lint checks completed for ${COMPONENT}
    '''
}

def call() {     // call is the default which will be called
pipeline {
    agent any 
    environment { 
        SONAR = credentials('sonar')
        NEXUS = credentials('nexus')
    }
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                     lintChecks()
                    }
                }
            }
        stage('Sonar Code Quality Check') {
            steps {
                script {
                     common.sonarCheck()
                    }
                }
            }

        stage('Test Cases') {
            parallel {
                stage('Unit Testing') {
                    steps {
                        // mvn test or npm test
                        sh "echo Unit Testing Completed"
                    }
                }
                stage('Integration Testing') {
                    steps {
                        // mvn verify or npm verify
                        sh "echo Integration Testing Completed"
                    }
                }
                stage('Function Testing') {
                    steps {
                        sh "echo Functional Testing Completed"
                    }
                }
            }
        }

        stage('Prepare Artifacts') {
            when { 
               expression { env.TAG_NAME != null }
                }   
            steps {
                sh "npm install"   // Generates the nodes_modules
                sh "zip ${COMPONENT}.zip node_modules/ server.js" 
                sh "echo Artifacts Preparation Completed................!!!"
            }
        }

        stage('Uploading Artifacts') {
            when { 
               expression { env.TAG_NAME != null }
                }   
            steps {
               sh "curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file pom.xml http://172.31.8.134:8081/repository/${COMPONENT}/${COMPONENT}.zip"
               }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 

