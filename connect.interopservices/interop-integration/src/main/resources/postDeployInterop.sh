#!/bin/bash

postDeployInterop(){
  cd /usr/tomcat/apache-tomcat-7.0.62/webapps/interopServices/WEB-INF/classes/deployment
  sudo chmod +x postDeploy.sh
  sudo ./postDeploy.sh
}