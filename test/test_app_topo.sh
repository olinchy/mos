#!/bin/sh
c=$(dirname $0)
cd $c

python kernel/framework/main.py -p ./projects/Test_App_topo.xml -o ./reports/test_app_topo
