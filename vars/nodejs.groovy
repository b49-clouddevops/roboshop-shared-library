def lintChecks() {
  sh '''
    echo installing jslint
    npm install jslint
    ~/node_modules/jslint/bin/jslint.js server.js || true
    echo lint checks completed for ${COMPONENT}
    '''
}

def sonarCheck() {
  sh '''
    sonar-scanner -Dsonar.host.url=http://172.31.4.93:9000 -Dsonar.sources=. -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=shipping -Dsonar.java.binaries=target/classes/
    curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > /tmp/quality-gate.sh 
    chmod +x /tmp/quality-gate.sh && /tmp/quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.4.93 ${COMPONENT}
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

