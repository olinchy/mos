#!/bin/bash
c=$(dirname $0)
cd $c

./test_mos_core.sh
./test_mos_core_ref.sh


