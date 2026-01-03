#!/bin/bash
set -e

apt update -y

apt install -y curl unzip maven

apt install -y openjdk-21-jre-headless

apt install -y openjdk-11-jre-headless

cd /opt
curl -L -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.113/bin/apache-tomcat-9.0.113.zip

unzip apache-tomcat-9.0.113.zip

chmod +x /opt/apache-tomcat-9.0.113/bin/*.sh