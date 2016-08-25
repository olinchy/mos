#!/bin/bash
if [ $# == 3 ]; then
    export MODEL_FOLDER=$1
    export MODEL=$2
    export FORCED_PERSISTENT_TYPE=$3
    export MODEL_PACKAGE="nr"$2
else
    export MODEL_FOLDER="mos_model_ne"
    export MODEL="8250"
    export FORCED_PERSISTENT_TYPE='All'
    export MODEL_PACKAGE="nr8250"
fi 

./buildworld.sh mosjava 

