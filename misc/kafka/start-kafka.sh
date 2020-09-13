#!/bin/bash

# Run Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties &

# Run Kafka
bin/kafka-server-start.sh config/server.properties