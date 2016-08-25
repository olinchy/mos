#!/bin/sh
PWD=`pwd`
ROOT=$PWD

gen_update_db_script()
{
   DBTYPE=$1
   OUTPUT=/mnt/$2/

   find $ROOT/develop/upgrade/old -type d -name "mo" >outfile
   while read line
   do
      OLD_VERSION="$OLD_VERSION*$line/"
   done < outfile
   rm -f outfile

   find $ROOT/develop/upgrade/new -type d -name "mo" >outfile
   while read line
   do
      NEW_VERSION="$NEW_VERSION*$line/"
   done < outfile
   rm -f outfile

   cd $ROOT/tools/modelgen/ems
   ./upgrade_db_script.sh $DBTYPE $OUTPUT $OLD_VERSION $NEW_VERSION
}


dbType=$1
oldVersion=$2
newVersion=$3
output=$oldVersion"-"$newVersion
gen_update_db_script $dbType $output

