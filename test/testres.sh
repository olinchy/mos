#!/bin/sh
c=$(dirname $0)
cd $c

python kernel/framework/main.py -p ./projects/Test_Res.xml -o ./reports/test_res
