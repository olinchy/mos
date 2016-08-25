#!/bin/bash
while true; do
  echo '--------printing memory in log.log-----------------'
  grep "debugging in massive<Message>, memory--" build/address-server/log/log.*
  date
  echo '--------------------------------------------------'
  sleep 60
done
    
