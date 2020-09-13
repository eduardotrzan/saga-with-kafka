#!/bin/bash

bin/zookeeper-server-start.sh -daemon config/zookeeper.properties && (bin/kafka-server-start.sh config/server.properties || echo "kafka start failed. restarting ... " && sleep 3 && bin/kafka-server-start.sh config/server.properties)