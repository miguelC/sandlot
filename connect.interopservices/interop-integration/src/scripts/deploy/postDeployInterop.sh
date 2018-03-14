#! /bin/bash

SCRIPT_FILE="postDeploy.sh"

cd /usr/tomcat/apache-tomcat-7.0.62/webapps/interop/WEB-INF/classes/deployment
echo Running $SCRIPT_FILE
sudo chmod +x $SCRIPT_FILE
sudo ./$SCRIPT_FILE

echo Finished!