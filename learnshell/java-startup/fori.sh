#!/usr/bin/env bash
for file in `ls /home/huangyong/data/client/tmp`
do
  curl http://192.168.0.196:9001/send?subFile=${file}
done

for logFile in `ls ${LOG_DIR}`
do
    if [[ ${logFile} =~ \.log$ ]]; then # [[ $file =~ \.log$ ]] 匹配以.log结尾的文件
        LOG_FILE=${logFile}
        ftime=8
        break
    fi
done