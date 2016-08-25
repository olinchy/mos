#!/bin/sh
jvm_opts="$jvm_opts -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=5006,server=y,suspend=n"
java -Djava.ext.dirs=./ $jvm_opts -cp ./mos-process-daemon-1.2.jar  com.zte.mos.process.daemon.MOSEntrance com.zte.mos.appdaemon &
echo $! > pid