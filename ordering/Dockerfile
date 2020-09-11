FROM openjdk:14-jdk-alpine3.10

RUN apk add --no-cache bash

COPY ordering-server/target/server.jar /opt/ordering/server/server.jar
COPY start.sh /opt/ordering/server/start.sh

RUN chmod -R o+x /opt/ordering/server/ && chmod 777 /opt/ordering/server/

ENV JAVA_OPTS=-javaagent:/opt/ordering/server/server.jar

WORKDIR /opt/ordering/server/

EXPOSE 8484

CMD /bin/bash /opt/ordering/server/start.sh ${springProfile}
