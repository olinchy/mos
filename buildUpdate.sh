#!/bin/bash
if [ "$1" == "debug" ]; then
  ./buildworld.sh ems-mos-update-starter
else
  ./buildworld.sh mosjava ems-mos-update-starter
fi
