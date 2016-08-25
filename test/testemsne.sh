#!/bin/sh
c=$(dirname $0)
cd $c

./prepare

python kernel/framework/main.py -p ./projects/Test_EmsNe.xml -o ./reports/test_emsne
