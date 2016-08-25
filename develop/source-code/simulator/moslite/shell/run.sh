#!/bin/sh
java -Djava.ext.dirs=./ -cp ./mos_core4sim-1.2.jar  com.mos.lite.process_starter.Main NR8960A nr8960a v242
echo $! > pid
