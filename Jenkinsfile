
pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code will be checked out from GitHub'
            }
        }

        stage('Build Java Application') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t cicd-docker-project:1.0 .'
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                sh '''
                    set -e

                    ECR_REGISTRY="552952091041.dkr.ecr.ap-south-1.amazonaws.com"
                    ECR_REPOSITORY="cicd-docker-project"

                    aws ecr get-login-password --region ap-south-1 \
                      | docker login --username AWS --password-stdin "$ECR_REGISTRY"

                    docker tag cicd-docker-project:1.0 \
                      "$ECR_REGISTRY/$ECR_REPOSITORY:1.0"

                    docker push "$ECR_REGISTRY/$ECR_REPOSITORY:1.0"
                '''
            }
        }
    }
}
