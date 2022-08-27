

node {
    stage('terraform init'){
         sh "terrafile -f env-${ENV}/Terrafile"
    }
}