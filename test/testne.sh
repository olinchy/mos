#!/bin/bash
c=$(dirname $0)
cd $c
python kernel/framework/main.py -p ./projects/Test_Ne.xml -o ./reports/test_ne
