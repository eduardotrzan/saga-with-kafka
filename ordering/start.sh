#!/bin/bash

java -jar -Dspring.profiles.active=${springProfile} server.jar
