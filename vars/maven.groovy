def lintChecks() {
  sh '''
    echo lint checks starting for ${COMPONENT}
    mvn checkstyle:check || true
    echo lint checks completed for ${COMPONENT}
    '''
}

def sonarCheck() {
  sh '''
     sonar-scanner -Dsonar.host.url=http://172.31.14.223:9000 -Dsonar.sources=. -Dsonar.login=admin -Dsonar.password=password -Dsonar.projectKey=shipping -Dsonar.java.binaries=target/classes/
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

