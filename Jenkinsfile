pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code will be checked out from GitHub'
            }
        }

        stage('Build Java Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t cicd-docker-project:1.0 .'
            }
        }
    }
}
