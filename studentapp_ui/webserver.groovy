pipeline {
    agent { label 'webserver' }

    stages {
        stage('pull') {
            steps {
                git 'https://github.com/AnupDudhe/studentapp-ui.git'
                sh '''
                echo we are in pull stage'''
            }
        }
        stage('build') {
            steps {
                sh '''mvn clean package
                echo we are in build stage'''
            }
        }
        stage('test') {
            steps {
                sh '''mvn clean verify sonar:sonar \\
                -Dsonar.projectKey=student-apps \\
                -Dsonar.host.url=http://3.110.134.171:9000 \\
                -Dsonar.login=sqp_925643351c72c64c1ee6b703d7f0d850eb7d0e8f'''
            }
        }

         stage('deploy') {
            steps {
            sh '''sudo mv /home/ubuntu/workspace/studentapp/target/studentapp-2.2-SNAPSHOT.war student.war
            sudo cp -rf /home/ubuntu/workspace/studentapp/student.war /opt/apache-tomcat-9.0.113/webapps/
            sudo sh /opt/apache-tomcat-9.0.113/bin/catalina.sh start
            sudo echo "we are in deploy stage"'''
            }
        }
    }
} 