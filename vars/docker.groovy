def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b49-clouddevops/${COMPONENT}"
        stage('Docker Build') {           
            sh "docker build -t 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
        }

        if(env.TAG_NAME != null) {
        stage('Docker Push') {
            sh "docker tag 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}" 
            sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 834725375088.dkr.ecr.us-east-1.amazonaws.com"
            sh "docker push 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
            }
        }
    }
}


        // common.lintChecks()
        // common.sonarCheck()
        // common.testCases()
        // if(env.TAG_NAME != null) {
        // common.artifacts()
        // }