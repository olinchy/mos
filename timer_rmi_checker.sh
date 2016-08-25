#!/bin/bash
while true; do
  echo '--------printing rmi connections in log.log-----------------'
  grep "debugging in massive<Message>, rmi connections count:" build/address-server/log/log.*
  date
  echo '--------------------------------------------------'
  sleep 60
done
    
