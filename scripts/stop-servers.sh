#!/bin/bash

# Stop
echo "Kill all java process"
JAR_PID=`ps | grep 'xke-memory-server-1.0-SNAPSHOT-shaded.jar' | grep -v 'grep' | awk ' {print $1}'`
for KILLPID in $JAR_PID; do
  kill -9 $KILLPID
  echo "Process $KILLPID killed"
done