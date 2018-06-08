FROM tomcat:7-jre8-alpine

# copy the WAR bundle to tomcat
COPY /target/test-generation-system.war /usr/local/tomcat/webapps/test-generation-system.war

# command to run
CMD ["catalina.sh", "run"]