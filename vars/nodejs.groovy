def call() {
pipeline {
    agent any 
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                      lintcheck(COMPONENT)
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 


// defining the lint check function
def lintcheck(COMPONENT) {    
    echo "lint checks started for ${COMPONENT}"
     sh "echo installing jslint"
     sh "npm install jslint"
     ~/node_modules/jslint/bin/jslint.js server.js || true"
                sh "echo lint checks completed"

}

info("Hai" , "twitter.com")           