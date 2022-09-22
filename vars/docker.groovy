def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b49-clouddevops/${COMPONENT}"
        stage('Docker Build') {
            sh "docker  build ."
        }

    }
}


        // common.lintChecks()
        // common.sonarCheck()
        // common.testCases()
        // if(env.TAG_NAME != null) {
        // common.artifacts()
        // }