def lintChecks() {
  sh '''
    echo lint checks starting for ${COMPONENT}
    mvn checkstyle:check || true
    echo lint checks completed for ${COMPONENT}
    '''
}

def sonarCheck() {
  sh '''
     sonar-scanner -Dsonar.host.url=http://172.31.9.36:9000 -Dsonar.sources=. -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=shipping -Dsonar.java.binaries=target/classes/
    '''
}

def call() {     // call is the default which will be called
pipeline {
    agent any 
    environment { 
        SONAR = credentials('sonar')
    }
    stages {
        // This should run for every commit on feature branch
        stage('Lint checks') {
            steps {
                script {
                    mvn --version
                    mvn clean compile
                    pwd && ls -ltr
                    lintChecks()
                    }
                }
            }

        stage('Sonar Code Quality Check') {
            steps {
                script {
                     sonarCheck()
                    }
                }
            }
        } // end of the stages
    }  // end of the pipeline
}  // end of function call 

