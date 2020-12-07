#!/bin/bash
CUR="$(PWD)"
cd ../angular-ui || exit
rm -rf ../angular-ui/dist
ng build --configuration stash --base-href /angular-ui/
cd "${CUR}" || exit
cd ../spring-api || exit
rm -rf env/local/mysql/*
rm -rf build bin
./gradlew -PenvironmentName=stash clean bootWar
cd "${CUR}" || exit
docker-compose build
docker-compose up