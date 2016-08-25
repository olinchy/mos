#!/bin/bash

trap "exit 1" TERM
export TOP_PID=$$

error()
{
   echo "------ [ERROR] $1 ------" 1>&2
   kill -s TERM $TOP_PID
}

[ -z "$MOS_HOME" ] && error "MOS_HOME is null. run env.sh first."
mwppu=$MOS_HOME/deploy/package/ums-server/procs/ppus/mw.ppu
sdk_lib=$MOS_HOME/sdk_lib

rm -rf $sdk_lib || error "rm sdk_lib error."
svn co http://10.86.55.132/svn/ZXMW-NetNumen/03-Dev_Branch/EMS-MW-DEV/CODE/template-private/ums-client/procs/ppus/mw.ppu/mw-common-api.pmu/mw-common-lib.par/lib $sdk_lib || error "svn co sdk error."
rm -rf $sdk_lib/*.* || error "rm sdk_lib/*.* error."
cp -rf $mwppu/mw-common-api.pmu/mw-common-lib.par/lib/*.* $sdk_lib/ || error "cp sdk error."

cp -rf $mwppu/mw-svr.pmu/pageabletable.par/*.jar $sdk_lib/ || error "cp pageabletable error."
cp -rf $mwppu/mw-svr.pmu/ueplogger.par/*.jar $sdk_lib/ || error "cp ueplogger error."
cp -rf $mwppu/mw-svr.pmu/uepservice.par/*.jar $sdk_lib/ || error "cp uepservice error."

svn st $sdk_lib | grep '^? ' | sed 's,?[ ]\{1\,\},,' | xargs -i svn add {} || error "svn add sdk error."
svn st $sdk_lib | grep '^! ' | sed 's,![ ]\{1\,\},,' | xargs -i svn del {} || error "svn del sdk error."
svn ci -m "upload sdk." $sdk_lib || error "svn co sdk error."
svn st $sdk_lib | grep . && error "svn st sdk error."
rm -rf $sdk_lib || error "rm sdk_lib error."
echo [INFO] upload sdk ok.
