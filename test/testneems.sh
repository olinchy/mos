#!/bin/sh
c=$(dirname $0)
cd $c

./prepare

python kernel/framework/main.py -p ./projects/Test_NeEms.xml -o ./reports/test_neems
