def lintChecks() {
  sh '''
    echo installing jslint
    npm install jslint
    ~/node_modules/jslint/bin/jslint.js server.js || true
    echo lint checks completed
    '''
}

def call() {
pipeline {
    agent any 
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                     sample.lintCheck()
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 

