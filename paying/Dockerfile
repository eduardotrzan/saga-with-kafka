FROM openjdk:14-jdk-alpine3.10

RUN apk add --no-cache bash

COPY paying-server/target/server.jar /opt/paying/server/server.jar
COPY start.sh /opt/paying/server/start.sh

RUN chmod -R o+x /opt/paying/server/ && chmod 777 /opt/paying/server/

ENV JAVA_OPTS=-javaagent:/opt/paying/server/server.jar

WORKDIR /opt/paying/server/

EXPOSE 8484

CMD /bin/bash /opt/paying/server/start.sh ${springProfile}
