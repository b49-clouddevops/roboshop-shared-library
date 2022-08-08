def lintChecks() {
  sh '''
    echo lint checks starting for ${COMPONENT}
    pylint *.py
    echo lint checks completed for ${COMPONENT}
    '''
}

def call() {     // call is the default which will be called
pipeline {
    agent any 
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                     lintChecks()
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 
