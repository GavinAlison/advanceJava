#!/usr/bin/env bash
# sed 的讲解
# https://www.cnblogs.com/ftl1012/p/9250171.html

#sed常见命令参数

#p==print
#d：delete
#=：打印匹配行的行号
#-n 取消默认的完整输出，只要需要的
#-e 允许多项编辑
#-i 修改文件内容
#-r 不需要转义
#1
#注意：& 符号在sed命令中代表上次匹配的结果

#hhh     pts/1        192.168.25.1     Sat Jun 30 22:04   still logged in
#reboot   system boot  2.6.32-358.el6.i Sat Jun 30 22:04 - 22:43  (00:38)
#omc     pts/0        192.168.25.1     Sat Jun 30 20:16 - down   (00:00)
#reboot   system boot  2.6.32-358.el6.i Sat Jun 30 19:38 - 20:16  (00:37)
#root     pts/1        192.168.25.1     Sat Jun 30 12:20 - down   (00:55)
#root     pts/0        192.168.25.1     Sat Jun 30 11:53 - down   (01:22)
#reboot   system boot  2.6.32-358.el6.i Sat Jun 30 11:52 - 13:15  (01:23)
#root     pts/0        192.168.25.1     Sat Jun 30 05:40 - down   (02:51)
#reboot   system boot  2.6.32-358.el6.i Sat Jun 30 05:38 - 08:32  (02:54)
#root     pts/0        192.168.25.1     Fri Jun 29 21:01 - down   (06:21)

#只打印第三行
sed -n '3p' yum.log
#只查看文件的第3行到第9行
sed -n '3,9p' yum.log
#过滤特定字符串,显示正行内容
sed -n '/root/p' yum.log
#显示包含"hhh"的行到包含"omc"的行之间的行
sed -n '/hhh/,/omc/p' yum.log
#打印1-5行，并显示行号
sed -n -e '1,5p' -e '=' yum.log
#仅仅显示匹配字符串的行号
sed -n '/root/p' yum.log
#打印匹配行的内容和符号【相当于后面又根据关键词查询了一次】
sed -n -e '/root/p' -e '/root/=' yum.log
#用world 替换yum.log文件中的root【真实写入】
sed -i 's/root/world/g' yum.log
sed -i 's#hhh#HHHH#g' h.txt     与上同
#s==search  查找并替换
#g==global  全部替换
#-i: implace

#打印最后一行
sed -n '$p' yum.log

#在文件第一行添加happy,文件结尾添加new year
sed -e '1i happy' -e '$a new year' yum.log     #  【界面显示】
sed -i -e '1i happy' -e '$a new year' yum.log   # 【真实写入文件】

#在文件第一行和第四行的每行下面添加hahaha
sed '1,4i hahaha' yum.log

#& 符号在sed命令中代表上次匹配的结果

sed 's/world/hello_&/g' yum.log

#删除第3到第9行,只是不显示而已
sed  '3,9d' /var/log/yum.log

#删除包含"hhh"的行到包含"omc"的行之间的行
sed '/hhh/,/omc/d' yum.log

#删除包含"omc"的行到第十行的内容
sed '/omc/,10d' yum.log

#范围可以用数字、正则表达式、或二者的组合表示

#显示中5到10行里匹配root，把行内所有的root替换为FTL，并打印到屏幕上
sed '5,10 s/root/FTL/g' yum.log # 【仅显示用】
sed -i '5,10 s/root/FTL/g' yum.log # 【-i 会真正替换信息】

#-e是编辑命令，用于多个编辑任务
#删除1到5行后，替换reboot为shutdown
sed -e '1,5d' -e '=' -e 's/reboot/shutdown/g'  yum.log

#打印3到6行的内容，并匹配reboot替换为shutdown
sed -n -e '3,6p' -e 's/reboot/shutdown/g'  yum.log

#-r命令是读命令, sed使用该命令将一个文本文件中的内容加到当前文件的特定位置上
cat test.txt
sed '/root/ r /var/log/test.txt' yum.log
# 【yum.log  匹配root后读入text.txt的内容在匹配内容之下，仅显示用】

#-w是写入命令

#【yum.log匹配root后写入root.txt'，真实写入】
sed  '/root/ w /home/omc/root.txt' yum.log
#【添加-n参数后，不打印源文件】
sed -n '/root/ w /home/omc/root.txt' yum.log


#打印1-5行，并显示行号
sed -n -e '1,5p' -e '='  yum.log

#sed的正则匹配
#正则匹配IP和子网掩码
ifconfig | sed -n '2p'
ifconfig | sed -n '2p' | sed -r 's#.*r:(.*) B.*k(.*)#\1 \2#g'
# (.*)表示匹配的项,之后可以用\1取出第一个括号内匹配的内容，
# \2取出第二个括号内匹配的内容
