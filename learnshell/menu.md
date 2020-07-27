# shell的分类

-   文件查看   cat/more/less/tail/tee/>/<
-   文本处理   grep/awk/gawk/sed/wc/sort/uniq
-   文件目录   pwd/cd/ls/file/mkdir/cp/mv/rm/ln/locate/find/xargs/touch/stat
-   系统管理   ps/kill/mount/unmount/df/du/free
-   数值计算   bc/expr/$[]/
-   日期时间   date/
-   网络有关   
-   游戏娱乐   
-   学习共享   scp/
-   其他      tar/zip/unzip/sleep/export/exit/source/dirname/basename/$0/$1/$@/$*/$#
-   用户与组   useradd/userdel/groupadd/groupmod/chmod/
-   流程控制   if/test/while-lopp/for-loop/case/for-in/

# 干掉缓存
echo 3 > /proc/sys/vm/drop_caches

grep -v 是反向查找的意思，比如 grep -v grep 就是查找不含有 grep 字段的行