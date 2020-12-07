#!/bin/bash

CUR="$(PWD)"
cd ../spring-api || exit
rm -rf build
./gradlew -PenvironmentName=local bootWar
cd "${CUR}" || exit
docker-compose -f docker-compose-api.yml build
docker-compose -f docker-compose-api.yml up