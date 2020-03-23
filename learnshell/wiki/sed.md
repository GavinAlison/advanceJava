



SHELL脚本之SED详解 (SED命令 , SED -E , SED S/ NEW / OLD / ... )
(一)

 

Sed是一个非交互性文本流编辑器。它编辑文件或标准输入导出的文本拷贝。vi中的正则表达式命令在sed中大多可以通用。

##sed常用选项

-e script 指定sed编辑命令

-f scriptfile 指定的文件中是sed编辑命令

-n 寂静模式，抑制来自sed命令执行过程中的冗余输出信息，比如只显示那些被改变的行。

-i[SUFFIX], –in-place[=SUFFIX] 替换和备份源文件

edit files in place (makes backup if extension supplied)

##1． 参数p: 打印匹配行

$ sed -n ’2p’/etc/passwd 打印出第2行
$ sed -n ’1,3p’/etc/passwd 打印出第1到第3行
$ sed -n ‘$p’/etc/passwd 打印出最后一行
$ sed -n ‘/user/p’ /etc/passwd 打印出含有user的行
$ sed -n ‘/\$/p’ /etc/passwd 打印出含有$元字符的行，$意为最后一行
$ sed -n ‘$=’ ok.txt	打印总行数
##2．参数a和i: 插入文本和附加文本(插入新行)

$ sed -n ‘/FTP/p’/etc/passwd 打印出有FTP的行
$ sed ‘/FTP/a\ 456′ /etc/passwd 在含有FTP的行后面新插入一行，内容为456
$ sed ‘/FTP/i\ 123′ /etc/passwd在含有FTP的行前面新插入一行，内容为123
$ sed ‘/FTP/i\ “123″’ /etc/passwd在含有FTP的行前面新插入一行，内容为”123″
$ sed ’5 a\ 123′ /etc/passwd 在第5行后插入一新行，内容为123
$ sed ’5 i\ “12345″’ /etc/passwd 在第5行前插入一新行，内容为”123表达式45″
sed-搜索结果后面增加嵌入代码

#!/bin/bash
#export LANG=zh_CN
find ./ -name “*.html” -exec grep 10000008/100 ‘{}’ \; -exec sed -i ‘/10000008/a\<\!--\# include file=\"\/code.html\"--\>‘ {} \;
#
##3．参数d：删除文本

删空格

sed -i ‘s/[ ]*//g’ ~/$cid.txt
删除无内容空行

sed ‘/^$/d’ file.conf > file.new.conf
sed -i ‘/^$/d’ ~/$cid.txt
删第一行

sed -i ’1d’ ~/$cid.txt
删前两行

sed -i ’1,2d’ ~/$cid.txt
del多行

cat SCTP.log |sed ’1d;2d;$d’
删除最后一行：

sed -e ‘$d’ file
删除最后两行：

sed ‘N;$!P;D’ file
sed ‘N;$!P;$!D;$d’ file
删除最后n行：

$vi sed.sh
$!/bin/bash
A=$(sed -n ‘$=’ file)
sed $(($A-n+1)),${A}d file

$ sed ‘/user/d’/etc/passwd
删除带有string的行(del include love row head)

sed -i ‘/^love/d’ file
sed -i ‘/love/d’ file
vi删除包含strings前4行，后34行

:/strings/-4,+34d
删除配置文件中#号注释行

sed ‘s#\#.*##’ file.conf > file.new.conf
sed ‘s,\#.*,,’
删除配置文件中//号注释行

sed ‘s#//.*##’ file.conf > file.new.conf
sed ‘s@//.*@@’
删除由空格和Tab而成的空行

sed ‘/^[[:space:]]*$/d’ file.conf > file.new.conf
sed ‘/^[[:space:]]*$/d’
删除尾行的空格

sed -e ‘s/.$//’ file > file.new.conf
在尾行添加1个空格

sed ‘s/[0-9]$/& /g’ file > flile.new.conf
##4． 参数s：替换文本,替换命令用替换模式替换指定模式，格式为：

[ a d d r e s s [，address]] s/pattern-to-find/replacement-pattern/[g p w n]

$ sed ‘s/user/USER/’/etc/passwd 将第1个user替换成USER,g表明全局替换
$ sed ‘s/user/USER/g’/etc/passwd 将所有user替换成USER
$ sed ‘s/user/#user/’/etc/passwd 将第1个user替换成#user,如用于屏蔽作用
$ sed ‘s/user//’/etc/passwd 将第1个user替换成空
$ sed ‘s/user/&11111111111111/’/etc/passwd 如果要附加或修改一个很长的字符串，可以使用（ &）命令，&命令保存发现模式以便重新调用它，然后把它放在替换字符串里面，这里是把&放前面
$ sed ‘s/user/11111111111111&/’/etc/passwd 这里是将&放后面 在包含字符串test的任意行上，将111替换成222
$ sed ‘/test/s/111/222/g’ sample.txt
##5. 快速一行正则表达式命令 下面是一些一行命令集。（[ ]表示空格，[TAB]表示t a b键）

‘s/\ . $//g’ 删除以句点结尾行
‘-e/abcd/d’ 删除包含a b c d的行
‘s/[ ] [ ] [ ] */[ ]/g’ 删除一个以上空格，用一个空格代替
‘s/^ [ ] [ ] *//g’ 删除行首空格
‘s/\ . [ ] [ ] */[ ]/g’ 删除句点后跟两个或更多空格，代之以一个空格
‘/^ $/d’ 删除空行
‘s/^ .//g’ 删除第一个字符
‘s/COL \ ( . . . \ )//g’ 删除紧跟C O L的后三个字母
‘s/^ \///g’ 从路径中删除第一个\
‘s/[ ]/[TAB]//g’ 删除所有空格并用t a b键替代
‘S/^ [TAB]//g’ 删除行首所有t a b键
‘s/[TAB] *//g’ 删除所有t a b键
##6、用sed删除文件中的换行符，杀鸡哪需牛刀？

tr -d ‘\n’ file
真的需要sed

sed -nr ‘ H;
$ {
x;
s/\n//g;
p
}’
##7、sed获取ip

[root@c01 ~]# ifconfig eth0|sed -ne ‘s/^.*addr:\([0-9.]*\).*$/\1/p’
192.168.100.180

[root@c01 ~]# ifconfig eth0 | sed -e ‘/inet/!d’ -e ‘s/.*addr://’ -e ‘s/[ ].*//’
192.168.100.180
##8、\/转义字符

echo /usr/local/bin | sed ‘s/\/usr\/local\/bin/\/common\/bin/’
find . -name “*.html” |xargs sed -i ‘s#/canApp/evaluation/html/	index.html#http://www.wallcopper.com/eva/#g’
都可以把当前目录下的.c .h文件中的TAB替换成4个空格。
find . -name “*.[c|h]” | xargs sed -i ‘s/\t/ /g’
##9、参数f：

以下三个命令相等，先在包含字符串test的任意行上，将111替换成222，再将字符f替换成hello

$ sed ‘/test/s/111/222/g ; s/f/hello/g’ sample.txt
$ sed -e ‘/test/s/111/222/g’ -e ‘s/f/hello/g’ sample.txt
选项-f: 将要执行的所以编译命令放入在文件中

$ more scher
/test/s/111/222/g
s/f/hello/g’ sample.txt
$ sed -f scher sample.txt
##10、参数q： 表示跳离sed。最多与一个地址参数配合。

打印出含有a或b字符的行，一旦遇到数字，即刻停止打印。

$ sed -n -e ‘/[0-9]/q’ -e ‘/[a b]/p’
到china quit
sed ‘china/q’ myfile.txt
##11、参数-i后缀:替换并将源文件备份改后缀

$ sed -i.bak ‘s/123/efg/g’ a.txt 备份源文件
##12、参数r:在某行插入文件

$ sed -i ’2 r readfile.txt’ writefile.txt
##13、参数w：读入文件中的内容存入到另一文件中（temp.txt)。最多与一个地址参数配合。

将sample.txt文件中含test字符串的数据行，copy至temp.txt档中储存(原temp.txt中无内容）

$ sed -e ‘/test/w temp.txt’ sample.txt
##14、参数y：转换数据中的字符。最多与两个地址参数配合。

1.将文件中的小写字母替换成大写字母。

$ sed -e ‘y/abcdefghijklmnopqrstuvwxyz/ABCDEFGHIJKLMNOPQRSTUVWXYZ/’ sample.txt
其中前后字符个数必须相同.

##15、参数!:表示不执行函数参数。 将文件中除了important字符串，其余全删除。

$ sed -e ‘/important/!d’ sample.txt
##16、参数=：表示印出资料的行数。最多与两个地址参数配合。 1.印出文件资料并显示行数。

$ sed -e ‘=’ sample.txt
计算总行数

$ sed -n ‘$=’ a.txt
##17、其它参数 参数#：表示对文字的注释。

参数N：表示添加下一笔资料到pattern space内。最多与两个地址参数配合。（添加下一笔：添加一笔资料后，继续添加这个资料的下一行数据到pattern space内） 1，将文件中的数据合并。文件内容如下： UNIX LINUX

$ sed -e ‘N’ -e’s/\n/\,/g’ sample.txt
结果如下： UNIX,LINUX

参数D：表示删除pattern space内的第一行资料。最多与两个地址参数配合。

参数P：打印出pattern space内的第一行资料。

$ sed -n -e ‘N’ -e ‘P’ sample.txt
利用N将下一行资料（即：偶数行）添加到pattern space内，在利用P打印出奇数行。 解释过程：由于N的作用，使得每次添加一条资料到pattern space后，先不执行操作，而是继续添加这个资料的下一行到pattern space内， 然后P参数打印出第一行资料。

参数h：表示暂存pattern space的内容至hold space（覆盖）。最多与两个地址参数配合。

参数H：表示暂存pattern space的内容至hold space（添加）。最多与两个地址参数配合。

参数g：表示将hold space的内容放回至pattern space内。（覆盖掉原pattern space内的数据）

参数G：表示将hold space的内容放回至pattern space内。（添加大到原pattern space数据之后）

将文件中所有空行删除，并在每一行后面增加一个空行。

$ sed ‘/^$/d;G’ sample.txt
将文件中所有空行删除，并在每一行后面增加两个空行。

$ sed ‘/^$/d;G;G’ sample.txt
参数x：表示交换hold space与pattern space内的数据。

将文件第三行的数据替换成第一行的数据。

$ sed -e ’1h’ -e ’3x’ sample.txt
参数b：

参数t：

如果使用sed对文件进行过滤，最好将问题分成几步，分步执行，且边执行边测试结果。

经验告诉我们，这是执行一个复杂任务的最有效方式。

参考：

http://www.2cto.com/os/201111/112217.html

http://markmail.org/download.xqy?id=w5pncjwnrs77lz3s&number=2

http://www.grymoire.com/Unix/Sed.html

http://sed.sourceforge.net/sed1line.txt

转自：

http://www.wallcopper.com/linux/769.html

 

 ( 二 )

一， 基本概述

（1）基本介绍

sed是一款流编辑工具，用来对文本进行过滤与替换工作，  sed通过输入读取文件内容，但一次仅读取一行内容进行某些指令处理后输出，sed更适合于处理大数据文件。
（2）工作原理

sed在处理文本文件的时候，会在内存上创建一个模式空间，然后把这个文件的每一行调入模式空间用相应的命令处理，处理完输出；接着处理下一行，直到最后。

（3）与vim的区别

vim需要通知处理文件的哪几行才会去处理，sed默认会处理文件的所有行，除非你告诉它不处理哪几行。

二， sed的基本语法

（1）sed [选项]  [定址commands] [inputfile]

关于定址：

定址可以是0个、1个、2个；通知sed去处理文件的哪几行。

0个：没有定址，处理文件的所有行

1个：行号，处理行号所在位置的行

2个：行号、正则表达式，处理被行号或正则表达式包起来的行

（2）

选项：

       --version            显示sed版本hao

       --help               显示帮助文档

       -n 关闭默认输出，默认将自动打印所有行

       -e 多点编辑，允许多个脚本指令被执行。

       -r 支持扩展正则+ ? () {} |

       -i 可以修改原文件，慎用！

       -f 支持使用脚本

命令：

       p打印行

       d        删除行

       s           替换

       n替换第几个匹内容

       w另存为

       a         之后添加一行

       i         当前行之前插入文本

       y        替换匹配内容

 

三， 实际案例讲解

（1）p（与-n合用）

查看passwd文件的1~3行

@1，打印一到三行

[root@tx3 ~]# cp /etc/passwd /t1

[root@tx3 ~]#  cat -n t1 | sed  -n '1,3p'

    1root:x:0:0:root:/root:/bin/bash

    2bin:x:1:1:bin:/bin:/sbin/nologin

    3daemon:x:2:2:daemon:/sbin:/sbin/nologin

 

@2，查看passwd文件的lp~halt行

[root@tx3 ~]#  cat -n t1 |sed -n '/lp/,/halt/p'

    5lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin

    6sync:x:5:0:sync:/sbin:/bin/sync

    7shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown

    8halt:x:7:0:halt:/sbin:/sbin/halt

@3， 打印基数行（从第一行开始，每隔一行一输出）



@4，打印uid是0或1的行

[root@tx3 ~]# sed -n '/x:[01]:/p' t1

root:x:0:0:root:/root:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin

 

（2）d 删除

@1，删除第一行



@2，删除第一行和第三行（与-e选项合用）



@3，；选项的使用和-e一样



@4，取反：!

[root@tx3 ~]#  cat -n t1 | sed '1!d'

    1root:x:0:0:root:/root:/bin/bash

（3）s 查找替换 s///

语法：

       '[address]s/pattern/replacement/flags'

                       old     new

flags：

           n：1－512 之间的正整数，表示替换模式里面出现的第几次内容

           p：打印

           g：全局修改

           w：另存为

       这几个选项是可以复合使用的，但是写的要有意义。（pg ；nw）

注：这里为部分截取

@1默认会替换行里面的第1个匹配

[root@tx3 ~]# sed 's/root/ROOT/' t1

ROOT:x:0:0:root:/root:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin

@2全部替换

[root@tx3 ~]# sed 's/root/ROOT/g' t1

ROOT:x:0:0:ROOT:/ROOT:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin

@3替换第2个匹配

[root@tx3 ~]# sed 's/root/ROOT/2' t1

root:x:0:0:ROOT:/root:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin

@4打印

[root@tx3 ~]# sed 's/root/ROOT/p' t1

ROOT:x:0:0:root:/root:/bin/bash

ROOT:x:0:0:root:/root:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin‘

[root@tx3 ~]# sed -n 's/root/ROOT/p' t1

ROOT:x:0:0:root:/root:/bin/bash

operator:x:11:0:operator:/ROOT:/sbin/nologin

@5另存为

[root@tx3 ~]# sed -n 's/root/ROOT/w /tx' t1

[root@tx3 ~]# cat /tx

ROOT:x:0:0:root:/root:/bin/bash

operator:x:11:0:operator:/ROOT:/sbin/nologin

@6在文件的每行前面添加 # 注释

[root@tx3 ~]# sed 's/^/#/' t1

#root:x:0:0:root:/root:/bin/bash

#bin:x:1:1:bin:/bin:/sbin/nologin

#daemon:x:2:2:daemon:/sbin:/sbin/nologin

@7删掉文件的第1个字符

[root@tx3 ~]# sed 's/^.//1' t1

oot:x:0:0:root:/root:/bin/bash

in:x:1:1:bin:/bin:/sbin/nologin

aemon:x:2:2:daemon:/sbin:/sbin/nologin

 

@8删掉文件的第2个字符

[root@tx3 ~]# sed 's/.//2' t1

rot:x:0:0:root:/root:/bin/bash

bn:x:1:1:bin:/bin:/sbin/nologin

 

（4）i 从当前行插入

在第一行前插入hello

[root@tx3 ~]# sed '1i hello' t1

hello

root:x:0:0:root:/root:/bin/bash

bin:x:1:1:bin:/bin:/sbin/nologin

（5）a 从当前行后添加

在第一行后添加hello

[root@tx3 ~]# sed '1a hello' t1

root:x:0:0:root:/root:/bin/bash

hello

bin:x:1:1:bin:/bin:/sbin/nologin

 

（6）-r 支持扩展正则

[root@tx3 ~]# sed -r 's/^(.)(.)/\1/' t1

rot:x:0:0:root:/root:/bin/bash      //相当于把前两个字符替换成第一个字符，这样就实现了删除第二个字符

bn:x:1:1:bin:/bin:/sbin/nologin

demon:x:2:2:daemon:/sbin:/sbin/nologin

其他方法：

@1#sed 's/.//2' t1

@2#sed 's/^../\1/' t1

 

（7）y 替换，按照位置来匹配替换  y///      不识别re

 

[root@tx3 ~]# sed 'y/abcdefghijklmnopqrstuvwxyz/ABCDEFGHIJKLMNOPQRSTUVWXYZ/' t1

ROOT:X:0:0:ROOT:/ROOT:/BIN/BASH

BIN:X:1:1:BIN:/BIN:/SBIN/NOLOGIN

DAEMON:X:2:2:DAEMON:/SBIN:/SBIN/NOLOGIN

（8）替换匹配行

@1[root@tx3 ~]# sed -n '1c ROOT' t1

ROOT

（9）q 退出

[root@tx3 ~]# sed '1q ' t1

root:x:0:0:root:/root:/bin/bash

（10）-i        可以修改原文件



（11）-f 支持脚本



四．sed如何处理数据

 

    PATT：模式空间，处理文件的输入行，处理文件的空间，最多保存8192字节，相当于车间sed把流内容在这里处理。

    HOLD：保留空间，用来保存已经处理的输入行；最多保存8192字节；默认有一个空行，相当于仓库，加工的半成品在这里临时储存。

 

COMM：命令

h：将模式空间里的内容，复制到保留空间里，覆盖原来的内容 >

H：将模式空间里的内容，追加到保留空间里，保留原来的内容 >>

g：将保留空间里的内容，复制到模式空间里，覆盖原来的内容

G：将保留空间里的内容，追加到模式空间里，保留原来的内容

n：对匹配行的下一行进行处理

x：交换模式空间和保留空间的内容

（1）h;G

@1.将第一行的内容放到打三行后面



@2.在每行的后面添加1个空行

注：1.保留空间里默认有一个空白行。2，‘G’默认处理全局



@3.把第1行到第4行的数据复制到第5行后面

注：1h是为了覆盖保留空间里的空白行



@4. 把第1行到第4行的数据剪切到第5行后面



（2）n    对匹配行的下一行进行处理



（3）x     交换模式空间和保留空间的内容



本文出自 “tanxin” 博客，请务必保留此出处http://tanxin.blog.51cto.com/6114226/1208944





>### link    
https://www.cnblogs.com/tureno/articles/6677942.html        
https://www.cnblogs.com/BurnovBlog/p/10825436.html