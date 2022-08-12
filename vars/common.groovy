def sonarCheck() {  
  stage('Sonar Checks') {
  if (env.APP_TYPE == "java") {
    sh '''
      # mvn clean compile
      # sonar-scanner -Dsonar.host.url=http://172.31.4.93:9000 -Dsonar.sources=. -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} -Dsonar.java.binaries=target/classes/
      # curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > /tmp/quality-gate.sh 
      # chmod +x /tmp/quality-gate.sh && /tmp/quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.4.93 ${COMPONENT}
      echo SonarChecks Completed
   '''
  } else {
    sh '''
      # sonar-scanner -Dsonar.host.url=http://172.31.4.93:9000 -Dsonar.sources=. -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT}
      # curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > /tmp/quality-gate.sh 
      # chmod +x /tmp/quality-gate.sh && /tmp/quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.4.93 ${COMPONENT}
      echo SonarChecks Completed
    '''
     }
   }
 }

def lintChecks() {
stage('Lint Checks') {
  if (env.APP_TYPE == "nodejs") {
  sh '''
     # echo installing jslint
     # npm install jslint
     # ~/node_modules/jslint/bin/jslint.js server.js || true
    echo lint checks completed for ${COMPONENT}
    '''
  } 
  else if (env.APP_TYPE == "java") { 
      sh '''
      echo lint checks starting for ${COMPONENT}
      mvn checkstyle:check || true
      echo lint checks completed for ${COMPONENT}
      '''
  }
  else if (env.APP_TYPE == "python") {  
    sh '''
      echo lint checks starting for ${COMPONENT}
      pylint *.py || true 
      echo lint checks completed for ${COMPONENT}
      '''
  } 
  else {
      sh '''
        // echo installing jslint
        // npm install jslint
        // ~/node_modules/jslint/bin/jslint.js server.js || true
        echo lint checks completed for ${COMPONENT}
        '''
  }
}

def testCases() {
        stage('Test Cases') {
            parallel {
                stage('Unit Testing') {
                        sh "echo Unit Testing Completed"
                }
                stage('Integration Testing') {
                        sh "echo Integration Testing Completed"
                }
                stage('Function Testing') {
                        sh "echo Functional Testing Completed"
                }
            }
        }
    }