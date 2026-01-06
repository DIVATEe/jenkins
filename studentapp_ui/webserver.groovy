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
                sh '''mvn clean verify sonar:sonar \
                -Dsonar.projectKey=studentapp \
                -Dsonar.host.url=http://52.66.215.114:9000 \
                -Dsonar.login=sqp_ad616655247c1bee0b6ac4822e974dba1282704c'''
            }
        }

         stage('deploy') {
            steps {
            sh '''sudo mv /home/ubuntu/workspace/webserver/target/studentapp-2.2-SNAPSHOT.war student.war
            sudo cp -rf /home/ubuntu/workspace/webserver/student.war /opt/apache-tomcat-9.0.113/webapps/
            sudo aws s3 cp /opt/apache-tomcat-9.0.113/webapps/student.war s3://cbz-app/
            sudo cp /home/ubuntu/workspace/webserver/mysql-connector.jar /opt/apache-tomcat-9.0.113/lib/
            sudo cp /home/ubuntu/workspace/webserver/context.xml /opt/apache-tomcat-9.0.113/conf/
            sudo sh /opt/apache-tomcat-9.0.113/bin/catalina.sh start
            sudo echo "we are in deploy stage"'''
            }
        }
    }
} 