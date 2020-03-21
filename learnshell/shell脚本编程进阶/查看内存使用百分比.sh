#!/usr/bin/env bash

#查看内存使用百分比

free | sed -n '2p' | gawk 'x = int(( $3 / $2 ) * 100) {print x}' | sed 's/$/%/'


# 清除缓存

echo 3>/proc/sys/vm/drop_caches