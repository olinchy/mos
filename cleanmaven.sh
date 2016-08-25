#!/bin/bash
c=$(dirname $0)
source $c/buildworld.sh maven
mvn clean -f $c/develop/source-code/
rm -rf ~/.m2/repository/com/zte/mos
$c/tools/svn/cleanview.sh $@
