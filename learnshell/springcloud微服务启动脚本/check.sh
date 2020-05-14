#!/usr/bin/env bash

#利用pwd命令获取当前工程目录，实际获取到的是该shell脚本的目录。再利用sed命令将/bin替换为空
PROJECT_HOME=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd | sed 's/\/sbin//')
SHELL_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)

source ${SHELL_DIR}/profiles.sh
source ${SHELL_DIR}/JAVA_ENV.sh

echo "work directory: ${PROJECT_HOME}"


TPID=0

getPID(){
    TPID=`pgrep -f ${APPLICATION_MAIN}`
    #javaps=`jps -l | grep $APPLICATION_MAIN`
    #javaps=`ps -ef|grep $APPLICATION_MAIN|grep -v grep|grep -v kill|awk '{print $2}'`
#    if [ -n "$javaps" ]; then
#        TPID=`echo $javaps`
#        TPID=`echo $javaps | awk '{print $1}'`
#    else
#        TPID=0
#    fi
}


#tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
getPID
if [[ ${TPID} -ne 0 ]]; then
        echo "check: ${APPLICATION_MAIN} is running, ps info: ${TPID}"
else
        echo "check: ${APPLICATION_MAIN} is not running."
fi
