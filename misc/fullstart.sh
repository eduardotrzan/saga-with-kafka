#!/bin/bash

cd kafka/
docker stop kafka-dc && docker rm kafka-dc
docker rmi kafka-dc -f
docker image build -t kafka-dc .

cd ../

cd tracer
docker stop tracer-dc && docker rm tracer-dc
docker rmi tracer-dc -f
docker image build -t tracer-dc .

cd ../../

mvn clean install

cd ordering/misc/database
docker stop ordering-db && docker rm ordering-db
docker rmi ordering-db -f
docker image build -t ordering-db .

cd ../../
docker stop ordering-server && docker rm ordering-server
docker rmi ordering-server -f
docker image build -t ordering-server .

cd ../

cd paying/misc/database
docker stop paying-db && docker rm paying-db
docker rmi paying-db -f
docker image build -t paying-db .

cd ../../
docker stop paying-server && docker rm paying-server
docker rmi paying-server -f
docker image build -t paying-server .

cd ../

docker-compose -f misc/kafka/docker-compose.yml -f misc/tracer/docker-compose.yml -f ordering/docker-compose.yml -f paying/docker-compose.yml up