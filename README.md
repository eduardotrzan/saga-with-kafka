# Order Payment

This is a Restful Service that simulates a Saga Pattern in microservices. There are two microservices:
- Ordering: responsible for handling order requests
- Paying: responsible for processing the payment for an order request

Besides the spring boot application mentioned above, there are:
- Swagger Documentation: Each microservice is documented using swagger to generate information about the endpoints
- Tracer with Zipkin: Requests are capable of being tracked through microservices using opentracer implementation for spring with zipkin and brave
- Kafka: Message exchange is done via events and saga pattern is implemented using kafka as medium.

## 1. For Running Application Locally ONLY

Pre-requirement: Have Postgres 12 or higher installed. Verify each application-local.yml for the ports configured.

### 1.1 Database

#### Creating a database

Run in terminal: 
```bash
createdb -h localhost -p 5436 -U root ordering
createdb -h localhost -p 5436 -U root paying
```

Run as SQL
```sql
CREATE DATABASE ordering;
CREATE DATABASE paying;
```

#### Create a User

Run as SQL
```sql
CREATE USER ordering WITH PASSWORD 'ordering';
CREATE USER paying WITH PASSWORD 'paying';
```

#### Grant Permissions

Run as SQL
```sql
ALTER USER ordering WITH SUPERUSER;
ALTER DATABASE ordering OWNER TO ordering;
GRANT ALL PRIVILEGES ON DATABASE ordering TO ordering;
GRANT ALL PRIVILEGES ON SCHEMA public TO ordering;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ordering;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ordering;

ALTER USER paying WITH SUPERUSER;
ALTER DATABASE paying OWNER TO paying;
GRANT ALL PRIVILEGES ON DATABASE paying TO paying;
GRANT ALL PRIVILEGES ON SCHEMA public TO paying;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO paying;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO paying;
```

### 1.2 Application

#### Pre-requirement: 
- Have Java 14+ installed;
- Have maven 3.6.3+ installed;

#### Lombok

This is a plugin to help avoid boiler plate in the code. 

Site: https://projectlombok.org/

Git: https://github.com/mplushnikov/lombok-intellij-plugin#installation

##### Installation

Make sure to have lombok properly installed as showed in the github. There are some configurations to be done in the IDE.

#### Run Options
1 - As java application from the maven sub-module `ordering-server` or `paying-server`: 
```
Application.java
```
The default profile is `local`


2 - As maven sub-module `server` run: 

```bash
mvn spring-boot:run
```

3 - As java jar:
```bash
java -jar -Dspring.profiles.active=local server.jar
``` 


## 2. For Running Docker ONLY

### 2.1 Database
Imagine can be found at `misc/database` folder of each *SERVICE*

#### Run Docker Dockerfile
```bash
docker image build -t ordering-db .

docker image build -t paying-db .
```

#### Run Docker compose 
```bash
docker-compose up
```

#### Accessing Container's bash
```bash
docker exec -ti ordering-server /bin/bash

docker exec -ti paying-server /bin/bash
```


### 2.2 Application
Make sure to be in the root folder of each *SERVICE*
Always make sure to build application before creating image `mvn clean install`

#### Run Docker Dockerfile
```bash
docker image build -t ordering-server .

docker image build -t paying-server .
```

#### Run Docker compose 
```bash
docker-compose up
```


### 2.3 Full Environment Docker Launch
It's important to notice that the instructions above are only to run the applications themselves. However you will need other containers
in order to fully run the environment. Make sure you are in the root this *PROJECT* (~/<path>/saga-with-kafka).


Before proceeding: 
- make sure you have built each image as explained above in `2.2 Application` section
- create local docker image for kafka by running `docker image build -t kafka-dc .` in `~/<root_folder>/misc/kafka`


You can concatenate docker composers that were configured specifically for each requirement and the environment will share the instances among
themselves. Run:

```bash
docker-compose -f misc/kafka/docker-compose.yml -f misc/tracer/docker-compose.yml -f ordering/docker-compose.yml -f paying/docker-compose.yml up
```

The above will:
- Load Kafka image which uses kafka server and Zookeeper
- Load Openzipkin for tracing the request in the system
- Load ordering service with its own database
- Load paying service with its own database 


## 3. Using the Application
- Download [Postman](https://www.getpostman.com/);
- Import Postman collections from `~/doc/api/saga.postman_collection.json`;
- Import Postman environment from `~/doc/api/saga.postman_environment.json`;
- Use the Valid and Invalid calls to use the system.


## 4. Tips
### Cache Issues
Case images get cached and run the following command:
```bash
docker stop $(docker ps -a -q) && docker rm $(docker ps -a -q) && docker rmi $(docker images -a -q)
```
The command above will stop all running dockers and wipe clean all images you currently have.

Note: if you don't wanna loose some image, don't run the comment, but do it for the image/container names above.

### Accessing Container's bash
```bash
docker exec -ti carshop-server /bin/bash
```

### Opening Swagger UI
Access in your browser the url: http://localhost:8484/<service>/swagger-ui.html#/
