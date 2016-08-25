@echo off
FOR /F %%i IN ('cd') DO set version=%%~nxi
java -Djava.ext.dirs=./ -cp ./mos-process-daemon-1.1.jar  com.zte.mos.process.daemon.MOSEntrance com.zte.mos.appdaemon