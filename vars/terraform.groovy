

    node {
        stage('terraform init'){
            sh "terrafile -f env-${ENV}/Terrafile"
            sh "sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars""
        }
    }
