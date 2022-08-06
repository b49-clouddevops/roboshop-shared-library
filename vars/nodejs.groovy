def lintChecks() {
    sh '''
    echo installing jslint
    npm install jslint
    ~/node_modules/jslint/bin/jslint.js server.js || true
    echo lint checks completed
}

def call() {
pipeline {
    agent any 
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                        echo "lint checks started for ${COMPONENT}"
                        sh "echo installing jslint"
                        sh "npm install jslint"
                        sh "node_modules/jslint/bin/jslint.js server.js || true"
                        sh "echo lint checks completed"
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 

