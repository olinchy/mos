#!/bin/bash
while true; do
  echo '--------printing cache in log.log-----------------'
  grep "cacheTotalBytes=" build/address-server/log/log.*
  date
  echo '--------------------------------------------------'
  sleep 60
done
    
