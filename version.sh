#!/bin/bash

###############
#    init
###############
trap "exit 1" TERM
export TOP_PID=$$

error()
{
   # print an error and exit
   echo "------ [ERROR] $1 ------" 1>&2
   # exit 1
   kill -s TERM $TOP_PID
}

help()
{
   cat << HELP
   USAGE EXAMPLE: $0 -s        show mos version
                  $0 -v 1.2    modify mos version to 1.2
HELP
   exit 0
}

over_ok()
{
    echo
    echo [INFO] run $0 on $(hostname) end, $(date)
    echo
    exit 0
}

r_input()
{
    isshow="false"
    mos_version=""
    
    while [ -n "$1" ]; do
    case "$1" in
       -h) help;shift 1;;
       -s) isshow=true;shift 1;;
       -v) [ -z "$2" ] && error "error: mos version is required for option -v. -h for help";mos_version=$2;shift 2;;
       --) shift;break;;
       -*) error "error: no such option $1. -h for help";;
       *) break;;
    esac
    done

    if [ "$isshow" = "true" ] && [ ! -z "$mos_version" ]; then
        error "error: option -s and -v can't be used together. -h for help"
    fi
}

getversion()
{
    sed -n '1,/<packaging>/p' ${base_dir}/develop/source-code/pom.xml | grep '<version>' | sed -e 's,.*<version>,,'     -e 's,</version>.*,,'
}

setversion()
{
    mvn -version
    mvn versions:set -DnewVersion=${1} -f ${base_dir}/develop/source-code/
    work_dir=${base_dir}/develop
    #grep -r '</parent>' --include=pom.xml ${work_dir} | sed 's,:.*,,' | xargs -i sed -i "/<parent>/,/<\/parent>/s,<v    ersion>[^<]\{1\,\}</version>,<version>${1}</version>," {} || error "modify mos version error."
    #sed -i "1,/<packaging>/s,<version>[^<]\{1\,\}</version>,<version>${1}</version>," ${work_dir}/source-code/pom.xml || error "modify source-code/pom.xml mos version error."
    err=$(mktemp)
    for i in $(find ${work_dir} -name pom.xml); do
        sed -n '/<parent>/,/<\/parent>/p' $i | grep '<version>' | grep -v "<version>${1}</version>" && echo "find not ${1} in $i">>$err
    done
    grep . $err && error "find not ${1}. check mos version result error."
    rm -rf $err
    sed -n '1,/<packaging>/p' ${work_dir}/source-code/pom.xml | grep '<version>' | grep -v "<version>${1}</version>" && error "find not ${1}. check source-code/pom.xml mos version result error."
}

###############
#    run
###############
r_input $*

base_dir=$(dirname $0)
source ${base_dir}/.bashrc || error "run .bashrc error"
if [ "$isshow" = "true" ]; then
    getversion
elif [ ! -z "$mos_version" ]; then
    setversion "$mos_version"
    echo [INFO] modify mos version to $mos_version success.
else
    help
fi
