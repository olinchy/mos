#!/bin/sh
PWD=`pwd`
ROOT=$PWD

usage()
{
    BIN=`basename $0`
    echo "The following cmds are supported:"
    echo
    echo "./$BIN 8120 [sql|oracle]"
    echo "./$BIN 8150 [sql|oracle]"
    echo "./$BIN 8250 [sql|oracle]"
    echo "./$BIN ems [sql|oracle]"
    echo "./$BIN all"
    echo 
    exit 1
}

gen_nr8000_sql()
{
   SELFDIR=$ROOT/develop/model/nr8000/$1/mo/
   COMMONDIR=$ROOT/develop/model/nr8000/$2/mo/
   EN_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-en/
   ZH_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-zh/
   DBTYPE=$3

   cd $ROOT/tools/modelgen/ems

   ./create_table_script.sh  $DBTYPE $EN_DIR $SELFDIR $COMMONDIR
   ./create_table_script.sh  $DBTYPE $ZH_DIR $SELFDIR $COMMONDIR
}

gen_ems_sql()
{
   SELFDIR=$ROOT/develop/model/$1/mo/
   EN_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-en/
   ZH_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-zh/
   DBTYPE=$2
   cd $ROOT/tools/modelgen/ems
   ./create_table_script.sh $DBTYPE $EN_DIR $SELFDIR
   ./create_table_script.sh $DBTYPE $ZH_DIR $SELFDIR
}

gen_all()
{
   EN_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-en/
   ZH_DIR=$ROOT/develop/source-code/deploy/template-private/\install/dbscript/dbscript-zh/
   find $ROOT/develop/model -type d -name "mo" >outfile
   while read line
   do
      MODIR="$MODIR $line/"
   done < outfile
   rm -f outfile
   cd $ROOT/tools/modelgen/ems
   ./create_table_script.sh sql $EN_DIR $MODIR
   ./create_table_script.sh sql $ZH_DIR $MODIR
   ./create_table_script.sh oracle $EN_DIR $MODIR
   ./create_table_script.sh oracle $ZH_DIR $MODIR
}


option=$1
case $option in
8120) dbType=$2
gen_nr8000_sql 8120 all $dbType
;;
8150)dbType=$2
gen_nr8000_sql 8150 all $dbType
;;
8250)dbType=$2
gen_nr8000_sql 8250 all $dbType
;;
ems)dbType=$2
gen_ems_sql ems  $dbType
;;
all)gen_all
;;
*) usage
;;
esac
