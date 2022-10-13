#!/bin/bash

#PROFILE="local"
#
#if [[ $1 != null ]]
#then
#  PROFILE=$1
#fi
#
#JAVA_OPT=-Dspring.profiles.active\=${PROFILE}

chmod +x ./gradlew
./gradlew clean build

java -jar ./build/libs/*.jar
  