#!/bin/bash
c=$(readlink -f $(dirname $0))
bl=$(mktemp)
source $c/.bashrc
mkdir -p ${MOS_HOME}/build
find ./ -name ".buildme.yml" -exec readlink -f {} \; > $bl
version=$($c/version.sh -s)
export NE_VERSION=${version}
export MOS_VERSION=${NE_VERSION}
$c/tools/build/buildworld.py $NE_VERSION build $bl
rm -rf $bl
make -C ${MOS_HOME}/build $@
