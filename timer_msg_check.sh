#!/bin/bash
while true; do
  echo '--------printing msg in sum in log.log-----------------'
  grep "debugging in massive<Message>, msg in sum: " build/address-server/log/log.*
  date
  echo '--------------------------------------------------'
  sleep 60
done
    
