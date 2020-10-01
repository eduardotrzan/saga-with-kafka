FROM openjdk:14-jdk-alpine3.10

RUN apk add --no-cache bash

COPY paysmart-orchestrator-server/target/server.jar /opt/paysmart-orchestrator/server/server.jar
COPY start.sh /opt/paysmart-orchestrator/server/start.sh

RUN chmod -R o+x /opt/paysmart-orchestrator/server/ && chmod 777 /opt/paysmart-orchestrator/server/

ENV JAVA_OPTS=-javaagent:/opt/paysmart-orchestrator/server/server.jar

WORKDIR /opt/paysmart-orchestrator/server/

EXPOSE 8484

CMD /bin/bash /opt/paysmart-orchestrator/server/start.sh ${springProfile}
