#!/bin/bash
CUR="$(PWD)"
rm -rf local/mysql/*
cd ../angular-ui
rm -rf ../angular-ui/dist
ng build --prod --base-href /angular-ui/
cd ${CUR}
cd ../spring-api
./gradlew -PenvironmentName=local clean bootWar
cd ${CUR}
docker-compose build
docker-compose up