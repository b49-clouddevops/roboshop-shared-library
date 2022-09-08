def call() {

// If the env.VARIABLE is null ( for DB's) it will add the past as pwd i.e, ./
   if(!env.TERRAFORM_DIR)  {
        env.TERRAFORM_DIR="./"
   }

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
            sh "cd ${TERRAFORM_DIR}"
            sh "pwd"
            sh "ls -ltr"
            sh "terrafile -f env-${ENV}/Terrafile"
            sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
        }

        stage('terraform plan') {
                sh "cd ${TERRAFORM_DIR}"
                sh "echo doing a terrafomr plan"
                sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
        }

        stage('terraform apply') {
                sh "cd ${TERRAFORM_DIR}"
                sh "terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve"
            }
        }
    }



// terraform-mutable will be there only for components and not for the DB's. Since we use the same code here, we need to ensure for DB's it' don't have to cd to terraform_dir
// and executre tf commands. But for APP Components, it's to go to terraform-mutable and execute the terraform commands