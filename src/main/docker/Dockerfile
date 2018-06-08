FROM tomcat:7-jre8-alpine

# copy the WAR bundle to tomcat
COPY /target/newdiploma-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/app.war

# command to run
CMD ["catalina.sh", "run"]