#!/bin/bash
while true; do
  echo '--------printing find result in log.log-----------------'
  grep "find all mo finished after" mass_test_find/log.log
  echo '--------------------------------------------------'
  grep "find all ne finished after" mass_test_find/log.log
  echo '--------------------------------------------------'
  sleep 800
done
    
