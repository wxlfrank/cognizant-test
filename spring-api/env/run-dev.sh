#!/bin/bash

rm -rf local/mysql/*

docker-compose build
docker-compose up
cd ..
rm -rf build bin
#sleep 5
#./gradlew -PenvironmentName=dev clean bootRun