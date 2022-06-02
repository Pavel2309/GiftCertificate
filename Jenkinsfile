#!groovy
pipeline {
    agent any
    tools {
        gradle 'Gradle-7.2'
        jdk 'jdk-17'
    }
    stages {
        stage('Clone sources') {
            steps {
                git url: 'https://github.com/Pavel2309/GiftCertificate.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean test war --no-daemon'
            }
        }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube-9.4') {
                    sh './gradlew sonarqube --no-daemon'
                }
            }
        }
    }
}