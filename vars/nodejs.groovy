def call() {
pipeline {
    agent any 
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                    nodejs.lintcheck()
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 


// defining the lint check function
def lintcheck(message, URL) {    
    echo "INFO: ${message} ,url value is ${URL}"
}

info("Hai" , "twitter.com")           