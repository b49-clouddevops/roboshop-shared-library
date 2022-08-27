def call() {
    properties([
        parameters([
            choice(choices: ['dev\nprod'], description: "Chose the Env", name: "ENV"),
        ]),
    ])

    node {
        git branch: 'main', url: "https://github.com/b49-clouddevops/${REPONAME}"
        stage('terraform init'){
            sh "terrafile -f env-${ENV}/Terrafile"
            sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
        }

        stage('terraform plan') {
                sh "echo doing a terrafomr plan"
                sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
        }

        stage('terraform apply') {
                sh "terraform apply -var-file=env-${ENV}/${ENV}.tfvars -auto-approve"
        }
    }
}
