FROM openjdk:14-jdk-alpine3.10

ENV SCALA_VERSION 2.13
ENV KAFKA_VERSION 2.6.0
ENV KAFKA_HOME /opt/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION"

COPY start-kafka.sh /usr/bin/start-kafka.sh

# Install Kafka, Zookeeper and other needed things
RUN apk update && apk upgrade && \
    apk --no-cache add --update wget bind-tools bash && \
    rm -rf /var/lib/apt/lists/* && \
    wget -q http://apache.mirror.iweb.ca/kafka/"$KAFKA_VERSION"/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz -O /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    tar xfz /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz -C /opt && \
    rm /tmp/kafka_"$SCALA_VERSION"-"$KAFKA_VERSION".tgz && \
    chmod 777 /usr/bin/start-kafka.sh "$KAFKA_HOME"/bin

WORKDIR "$KAFKA_HOME"

# 2181 is zookeeper, 9092 is kafka
EXPOSE 2181 9092

CMD /bin/bash /usr/bin/start-kafka.sh
