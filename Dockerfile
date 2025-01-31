FROM amazoncorretto:17.0.7-alpine
COPY target/*.jar /zadanie_6.jar
COPY entrypoint.sh /entrypoint.sh
ENV CATALOG /var/communications
RUN chmod +x /entrypoint.sh
RUN mkdir -p ${CATALOG}
ENV JVM_OPTIONS -agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n
ENV JAVA_APP /zadanie_6.jar
EXPOSE 8080 5005
# App parameters

ENTRYPOINT ["/entrypoint.sh"]