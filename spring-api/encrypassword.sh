#!/bin/bash
IFS=" " read -r -a encrypted <<< "$(./gradlew -PmainClass=org.test.api.JasyptEncryptor -PappArgs="['$1']" -q runJava)"
echo "${encrypted[@]}"