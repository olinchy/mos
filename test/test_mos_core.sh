#!/bin/bash
c=$(dirname $0)
cd $c

mkdir -p ./reports/test_ems_ref
python kernel/framework/main.py -p ./projects/Test_MOS_Core.xml -o ./reports/test_mos_core
