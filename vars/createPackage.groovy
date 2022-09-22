def call() {
    sh "mvn clean package"
    sh mv target/shipping-1.0.jar shipping.jar"
}
