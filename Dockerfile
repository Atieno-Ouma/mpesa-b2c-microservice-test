FROM tomcat:10-jdk21

WORKDIR /usr/local/tomcat/webapps/

COPY target/BackendTest-0.0.1-SNAPSHOT.war ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
