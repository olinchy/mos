#!/bin/sh

export VERSION="2.04.01.06"

PWD=`pwd`
ROOT=$PWD

usage()
{
    BIN=`basename $0`
    echo "The following cmds are supported:"
    echo
    echo "./$BIN 8120 "
    echo "./$BIN 8150 "
    echo "./$BIN 8250 "
    echo "./$BIN ems "
    echo "./$BIN all"
    echo 
    exit 1
}

_gen_model()
{
   MODELDIR=$1
   PKG=$2

   cd $ROOT/tools/modelgen/ems
   ./gen_models.sh $MODELDIR $PKG
   ./gen_enums.sh $MODELDIR $PKG
}

remove_old_model()
{
    PKG=$1
    rm -rf $ROOT/develop/source-code/model/mos_model_$PKG\_mw/src/com/zte/mos/domain/model/autogen/$PKG/mo/*.*
    rm -rf $ROOT/develop/source-code/model/mos_model_$PKG\_mw/src/com/zte/mos/domain/model/autogen/$PKG/enums/*.*
}


remove_all()
{
    remove_old_model nr8120
    remove_old_model nr8150
    remove_old_model nr8250
    remove_old_model ems
}



gen_model()
{
    _gen_model $@
}

option=$1
shift
case $option in
8120)remove_old_model nr8120
     gen_model $ROOT/develop/model/nr8000/nr8120 nr8120
     gen_model $ROOT/develop/model/nr8000/all nr8120
;;
8150)remove_old_model nr8150
     gen_model $ROOT/develop/model/nr8000/nr8150 nr8150
     gen_model $ROOT/develop/model/nr8000/all nr8150
;;
8250)remove_old_model nr8250
     gen_model $ROOT/develop/model/nr8000/nr8250 nr8250
     gen_model $ROOT/develop/model/nr8000/all nr8250
;;
ems)remove_old_model ems
    gen_model $ROOT/develop/model/ems/ ems
;;
all)remove_all
    gen_model $ROOT/develop/model/nr8000/nr8120 nr8120
    gen_model $ROOT/develop/model/nr8000/all nr8120
    gen_model $ROOT/develop/model/nr8000/nr8150 nr8150
    gen_model $ROOT/develop/model/nr8000/all nr8150
    gen_model $ROOT/develop/model/nr8000/nr8250 nr8250
    gen_model $ROOT/develop/model/nr8000/all nr8250
    gen_model $ROOT/develop/model/ems/ ems
;;
*) usage
;;
esac
