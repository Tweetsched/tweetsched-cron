#!/bin/bash

nohup java -jar tweetsched-cron-1.0.jar &
PID=$!
echo $PID > pid.txt
