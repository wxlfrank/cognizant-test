FROM tomcat:9.0
COPY tomcat-server.xml /usr/local/tomcat/conf/server.xml
COPY wait_for_it.sh /usr/local/
RUN chmod +x /usr/local/wait_for_it.sh
EXPOSE 8009
EXPOSE 8080
CMD ["/usr/local/wait_for_it.sh", "mysql:3306", "--", "catalina.sh", "run"]