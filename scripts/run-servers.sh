#!/bin/bash

GENERIC_ADMIN_PASS=adminpass
GENERIC_PORT=3000
serverNb=1

if [ -z "$1" ]
  then
    $serverNb=$1
fi

# Start
echo "$serverNb server(s) to be started..."
for serverNum in $(seq 0 $(($serverNb-1))); do
  pass=$GENERIC_ADMIN_PASS
  port=$(($GENERIC_PORT + $serverNum))
  java -jar target/xke-memory-server-1.0-SNAPSHOT-shaded.jar $pass $port &
done