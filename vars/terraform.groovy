def call {

    properties([
        parameters([
            choice(choices: ['dev\nprod], description: "Increase version's number: MAJOR.MINOR.FIX", name: "VERSIONING"),
        ]),
    ])

    node {
        stage('terraform init'){
            sh "terrafile -f env-${ENV}/Terrafile"
            sh "sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars""
        }
    }
}