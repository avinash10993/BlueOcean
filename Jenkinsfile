pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        git(url: 'https://github.com/avinash10993/testautothon-2019.git', branch: 'test', changelog: true)
      }
    }
  }
}