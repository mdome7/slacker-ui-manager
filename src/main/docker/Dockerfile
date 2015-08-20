# https://registry.hub.docker.com/_/java/

FROM java:8

RUN mkdir newrelic
COPY newrelic.yml newrelic/newrelic.yml
COPY newrelic.jar newrelic/newrelic.jar

COPY related-categories-api-*-jar-with-dependencies.jar api-server.jar

ENV APIPORT=8082

expose 8082

CMD ["java", "-javaagent:newrelic/newrelic.jar", "-jar", "api-server.jar"]
