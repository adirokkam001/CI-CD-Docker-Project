
pipeline {
    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-amazon-corretto.x86_64'
        PATH = "${JAVA_HOME}/bin:${PATH}"
        ECR_REGISTRY = '552952091041.dkr.ecr.ap-south-1.amazonaws.com'
        ECR_REPOSITORY = 'cicd-docker-project'
        APP_HOST = '15.252.97.141'
        IMAGE_TAG = '1.0'
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

                    aws ecr get-login-password --region ap-south-1 |
                      docker login --username AWS --password-stdin "$ECR_REGISTRY"

                    docker tag cicd-docker-project:1.0 \
                      "$ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG"

                    docker push "$ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG"
                '''
            }
        }

        stage('Deploy to Application EC2') {
            steps {
                sh '''
                    set -e

                    IMAGE="$ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG"

                    ssh -i /var/lib/jenkins/.ssh/deploy_key \
                      -o StrictHostKeyChecking=accept-new \
                      ubuntu@"$APP_HOST" "
                        set -e
                        aws ecr get-login-password --region ap-south-1 |
                          docker login --username AWS --password-stdin $ECR_REGISTRY

                        docker pull $IMAGE
                        docker stop cicd-demo || true
                        docker rm cicd-demo || true
                        docker run -d \
                          --name cicd-demo \
                          --restart unless-stopped \
                          -p 8081:8081 \
                          $IMAGE
                    "
                '''
            }
        }
    }
}
