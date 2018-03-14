After installation of the interopServices-[version].war file and navigating to this folder
run:

$> sudo chmod +x postDeploy.sh
$> sudo ./postDeploy.sh

The reason for sudo is that tomcat runs under root user so you need admin privileges for
this script to run properly as it will be moving and deleting files.

The file ../postDeployInterop.sh contains code that can be placed in your 
<user home>/.bash_profile and you can run it by typing the command portDeployInterop.

The postDelpoy.sh script does the following:

1. Looks in the current directory for a sub-directory matching the hostname of the server
   Go on command prompt and type "hostname" if you want to know which directory the files
   are taken from
2. Copies the files context.xml and sandlotRoutes.xml from the "hostname" folder and 
   overwrites the files in ../context.xml and ../routes/sandlotRoutes.xml respectively.
3. Deletes everything but the "hostname" sub-directory in this directory.
   
 This should be all needed to reconfigure the camel context in the target server.