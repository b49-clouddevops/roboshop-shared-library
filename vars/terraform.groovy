def call() {
    properties([
        parameters([
            choice(choices: 'dev\nprod', description: "Chose the Env", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "Choose apply or destroy", name: "ACTION"),
        ]),
    ])

    node {
        sh "rm -rf *"
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
                sh "terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve"
            }
        }
    }



// terraform-mutable will be there only for components and not for the DB's. Since we use the same code