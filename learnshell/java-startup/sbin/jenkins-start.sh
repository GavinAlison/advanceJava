#!/usr/bin/env bash
moduleName="anyest-center-admin"
publishDir=$1
#PRG_NAME=CenterAdminApplication
backupDir=$2
PRG_NAME=CenterAdminApplication

PROFILE=$3

echo "************************"
echo "*** publishDir=$publishDir ***"
echo "*** moduleName=$moduleName ***"
echo "*** backupDir=$backupDir ***"
echo "*** PRG_NAME=$PRG_NAME ***"
echo "*** $publishDir$moduleName ***"
echo "*** pwd=`pwd`  ***"
echo "************************"



if [ ! -d  "${publishDir}${moduleName}" ]; then
    mkdir "${publishDir}${moduleName}"
fi
if [ ! -d  "$backupDir" ]; then
    mkdir "$backupDir"
fi

cd "${publishDir}${moduleName}"
echo "pwd=`pwd`"
source /etc/profile


shutdown(){
    kill -9 ${TPID}
}

TPID=0

getPID(){
    TPID=`pgrep -f $PRG_NAME`
}

getPID

if [[ $TPID -ne 0 ]] ; then
    shutdown
fi

i=1
while [[ $i -le 60 ]]
  do
    if [[ $TPID ]];
        then
          echo "$PRG_NAME is running as process $process_id"
          sleep 1
          i=$(( $i + 1 ))
        else
          echo "$PRG_NAME is end"
          break
    fi
  done

echo "***********************************************"
echo "***   server stoped,   begin startting      ***"
echo "***********************************************"

cd ..
#文件夹数据备份
curDate=`date +%Y-%m-%d%H:%M:%S`
backFileName="${moduleName}-${curDate}"
#进入备份文件夹
cd ${backupDir}
#判断文件是否存在 -f文件 -d文件夹
if [[ -d $backFileName ]]; then
echo "*********** $backFileName 存在并移除 *********** "
rm -rf $backFileName
fi

cd ${publishDir}

#旧tar包文件夹重命名移动到bak备份文件夹
mv ${moduleName} "${backupDir}/${backFileName}"
mkdir ${moduleName}

sleep 1
`tar -zxvf ${moduleName}*.tar.gz --strip-components 1 -C "${publishDir}${moduleName}"`
sleep 20

echo "cd ${publishDir}${moduleName}"

cd ${moduleName}
chmod +x sbin/startup.sh
sh sbin/startup.sh $3

