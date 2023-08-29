pipeline {
    agent any

    tools{
     jdk 'Java17'
     }
    environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
      }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Build Docker Image'){
            steps{
                sh'docker build -t spring_app:latest .'
            }
        }
       stage('Run Docker Container'){
           steps{
               sh 'docker tag spring_app moirotsos/sensor_measurement:latest'
           }
       }
       stage('Push the image to docker hub'){
           steps{
               script{
                   sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'

                   sh 'docker push moirotsos/sensor_measurement:latest'
               }
           }
       }
    }
    post {
    always {
      sh 'docker logout'
    }
  }
}