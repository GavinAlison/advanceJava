#!/usr/bin/env bash


SHELL_DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)
source ${SHELL_DIR}/check.sh


TIMES=0
TIMEOUT=20
TPID=0
getPidFile(){
    if [[ -e ${PROJECT_HOME}/tpid ]]; then
        while read line
        do
            TPID=${line}
        done < ${PROJECT_HOME}/tpid
    fi
    echo "file pid: ${TPID}"
}

shutdown(){

    if [[ $2 = "c" ]]; then
            getPID
        else
            getPidFile
    fi

    if [[ ${TPID} -ne 0 ]]; then
        if [[ $1 -ne 0 ]]; then
            echo "=========================Stopping Service=============================="
            kill ${TPID}
            #echo $?

            if [[ $? -eq 0 ]]; then
                echo ""
            else
                echo "[Service Stop Failed]"
                return
            fi

            echo -n "Stopping ${APPLICATION_MAIN} (PID=${TPID})...";
        fi


        while [[ ${TPID} -ne 0 ]] && [[ ${TIMES} -lt ${TIMEOUT} ]]
        do
            getPID
            ((TIMES++))
            sleep 1
            echo -n "."
        done

    else
        echo "${APPLICATION_MAIN} is not running"
        return
    fi

    sleep 1
    echo "."
    getPID
    if [[ ${TPID} -eq 0 ]]
    then
        if [[ -e ${PROJECT_HOME}/tpid ]]; then
            rm -f ${PROJECT_HOME}/tpid
        fi
        echo "Service Stop Success."
    elif [[ ${TIMES} -gt ${TIMEOUT} ]] || [[ ${TIMES} -eq ${TIMEOUT} ]]
    then
        echo "Service Stop Time out, please kill -9 ${TPID}"
    fi

}

shutdown 3 $1