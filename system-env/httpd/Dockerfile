FROM httpd:2.4
RUN echo "Include conf/extra/tomcat-proxy.conf" >> /usr/local/apache2/conf/httpd.conf
COPY tomcat-proxy.conf /usr/local/apache2/conf/extra/
EXPOSE 80
CMD ["httpd-foreground"]