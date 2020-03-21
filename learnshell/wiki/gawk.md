## gawk 使用方法简介

gawk 是最初 Unix 系统上 awk 程序的 GNU 版本。相对于作为流式编辑器的 sed 而言，它提供了更为强大的编程语言特性。

### 其功能与特性包括：

-   定义变量来存储数据
-   通过代数运算符和字符串操作符来处理数据
-   使用结构化编程语句如 if-then 和循环等
-   从数据文件中提取出有价值的字段再重新组合以生成结构化的报表

### 基本语法
`gawk options program file`

构成 gawk 脚本的语句须包含在一对大括号（ {} ）中，而作为命令选项的整个脚本需要包含在一对引号中：
```bash
$ cat test.txt
Hello World!
$ gawk '{print $0}' test.txt
Hello World!
```
### 1. 使用字段变量
gawk 会自动地将每行文本中的每个数据字段赋值给一个指定的变量，默认情况下，预先定义的变量为：
```
$0 表示一整行文本
$1 表示该行文本的第一个字段
$2 表示该行文本的第二个字段
$n 表示该行文本的第 n 个字段
```
文本行中的数据字段是通过预先定义的字段分隔符来分隔开的，默认为空格（包括 TAB ）
```
$ cat data.txt
One line of test text.
Two lines of test text. 
Three lines of test text.
$ gawk '{print $1}' data.txt 
One
Two
Three
```
可以通过 -F 选项指定另外的分隔符（如 ' , ' ）
```
$ cat user.csv
Jack,male,20
Rose,female,18
Mike,male,24
$ gawk -F, '{print $1}' user.csv
Jack
Rose
Mike
```
### 2. 多个命令
gawk 语言允许在脚本语句中组合多个命令使用，只需要在各命令之间使用分号（ ; ）分隔开即可
```
$ echo "My name is Rich" | gawk '{$4="Christine"; print $0}' 
My name is Christine
```
也可以这样：
```
$ gawk '{
> $4="Christine"
> print $0}'
My name is Rich
My name is Christine 
```
其中 My name is Rich 是运行时程序获取的用户输入，My name is Christine 是程序运行后的输出

### 3. BEGIN & END
默认情况下，gawk 从输入中读取一行文本，再对该文本执行程序指令。而有时候需要在读取待处理数据之前先执行某些指令，此时就要用到BEGIN关键字。
同样的，END 关键字允许你指定在数据处理完成后才执行的脚本。
```
$ gawk 'BEGIN {print "The data File Contents:"} 
> {print $0}
> END {print "End of File"}' data.txt 
The data File Contents:
One line of test text.
Two lines of test text. 
Three lines of test text.
End of File 
```
### 4. 从文件中获取脚本
gawk 允许先将其程序脚本保存在某个文件中，再通过 -f 选项指定该文件的文件名。而在脚本文件中，各命令不再需要通过 ';' 符号分隔，直接分行列出即可。
```
$ cat script.gawk
BEGIN {
print "Users and their age" 
print "User \t Age"
FS=","
}
{ 
print $1 " \t " $3
}
END {
print "There are three people" 
}
$ gawk -f script.gawk user.csv
Users and their age
User     Age
Jack     20
Rose     18
Mike     24
There are three people
```
上述脚本文件中的 FS="," 用于定义字段分隔符，效果等同于 -F 选项。

### 高级特性
### 1. 变量
程序语言最重要的特性之一就是定义和引用变量。gawk 语言支持两种类型的变量：内建变量和用户自定义变量。

gawk 程序在处理文本数据时，一次只读取一小段文本，称为 Record 。默认的 Record 分隔符即为换行符。而每条 Record 又可进一步划分成字段（Data Field），并按顺序依次赋值给 $1，$2，$n 等。默认的字段分隔符为空格（包括 TAB）

控制数据字段和 Record 的内建变量：
```
变量名	描述
FIELDWIDTHS	用一串由空格分隔的数字定义每个数据字段的具体宽度
FS	定义数据字段分割符（输入）
RS	定义 Record 分割符（输入）
OFS	定义数据字段分割符（输出）
ORS	定义 Record 分割符（输出）
```
默认的 OFS 为空格

```
$ gawk 'BEGIN{FS=","} {print $1,$2,$3}' user.csv
Jack male 20
Rose female 18
Mike male 24
$ gawk 'BEGIN{FS=",";OFS=":"} {print $1,$2,$3}' user.csv
Jack:male:20
Rose:female:18
Mike:male:24
```
当定义了 FIELDWIDTHS 变量时，gawk 在读取数据时就会忽略字段分割符（FS），转而使用字段宽度来分割数据。
```
$ cat numbers.txt
1005.3247596.37
11522.349194.00
05810.1298100.1
$ gawk 'BEGIN{FIELDWIDTHS="3 5 2 5"} {print $1,$2,$3,$4}' numbers.txt
100 5.324 75 96.37
115 22.34 91 94.00 
058 10.12 98 100.1
```
有些时候会遇到如下组织方式的文本文件：
```
$ cat people.txt
Riley Mullen
123 Main Street
Chicago, IL 60601
(312)555-1234

Frank Williams
456 Oak Street
Indianapolis, IN 46201
(317)555-9876
```
此时可将字段分隔符（FS）设置为 "\n"，Record 分隔符（RS）设置为空字符串，则 gawk 程序会将空行作为一条 Record 的终止点。
```
$ gawk 'BEGIN{FS="\n"; RS=""} {print $1,$4}' people.txt
Riley Mullen (312)555-1234
Frank Williams (317)555-9876
```
其他内建变量
```
变量	描述
ARGC	当前命令行参数的数目
ARGV	由命令行参数组成的数组
CONVFMT	数字的转换格式，默认值为 %.6 g
ENVIRON	包含当前系统环境变量的关联数组（字典）
ERRNO	当读取或关闭文件出现错误时返回的系统错误
FILENAME	gawk 处理的数据文件的文件名
FNR	当前正在处理的 Record 序号
IGNORECASE	设置为非零值时忽略大小写
NF	数据文件中的字段序号
NR	已处理的 Record 总数
OFMT	数字的输出格式，默认为 %.6 g
$ gawk 'BEGIN{FS=",";
> print ARGC,ARGV[0],ARGV[1];
> print ENVIRON["HOME"]}
> {print FILENAME,FNR ":" $1}' user.csv
2 gawk user.csv
/Users/starky
user.csv 1:Jack
user.csv 2:Rose
user.csv 3:Mike
```
ARGV 的索引是从 0 开始的，表示第一个命令行参数（呃，所以通常就是 gawk 这个命令本身）。程序脚本（引号中的内容）不算在参数内。

### 用户自定义变量
-   在脚本中定义变量
```
$ gawk '
> BEGIN{
> testing = "This is a test" 
> print testing
> testing = 45
> print testing
> }'
This is a test
45
$ gawk 'BEGIN{x = 4; x = x * 2 + 3; print x}'
11
```
-   在命令行参数中定义变量
```
$ cat script1.gawk
BEGIN{FS = ","}
{print $n}
$ gawk -f script1.gawk n=1 user.csv
Jack
Rose
Mike
```
### 2. 数组
定义数组：var[index]=element
```
$ gawk 'BEGIN{
> var[1] = 34
> var[2] = 3
> total = var[1] + var[2] 
> print total
> }' 
37
```
遍历数组：
```
for (var in array) 
{
    statements
}
$ gawk 'BEGIN{
> var["a"] = 1
> var["g"] = 2
> var["m"] = 3
> var["u"] = 4
> for (test in var) 
> {
>     print "Index:",test," Value:",var[test] 
> }
> }'
Index: u  Value: 4
Index: m  Value: 3
Index: a  Value: 1
Index: g  Value: 2
```
关联数组遍历的顺序是随机的

### 3. 模式匹配
正则表达式
```
$ cat user.csv
Jack,male,20
Rose,female,18
Mike,male,24
$ gawk 'BEGIN{FS=","} /Jack/{print $0}' user.csv
Jack,male,20
```
匹配符（~）
匹配符（~）用来对 Record 中的特定字段使用正则表达式。!~ 表示不匹配。
```
$ cat data
This is line 1
Another line
line three
This is line four
$ gawk '$3 ~ /line/{print $0}' data
This is line 1
This is line four
$ gawk '$3 !~ /line/{print $0}' data
Another line
line three
```
数学表达式
```
$ cat user.csv
Jack,male,20
Rose,female,18
Mike,male,24
$ gawk 'BEGIN{FS=","} $3 >= 20{print $0}' user.csv
Jack,male,20
Mike,male,24
```
4. 结构化命令
```
if
if (condition) statement

$ cat numbers
2
4
6
8
10
$ gawk '{
> if ($1 < 5)
> {
>   x = $1 - 2
>   print x
> } else
> {
>   x = $1 / 2
>   print x
> }}' numbers
0
2
3
4
5
```
while
```
while (condition)
{
  statements
}
cat number
130 120 135
160 113 140
145 170 215
$ gawk '{
> total = 0
> i = 1
> while (i <= 3)
> {
>   total += $i
>   i++
> }
> avg = total / 3
> print "Average:",avg
> }' number
Average: 128.333
Average: 137.667
Average: 176.667
```
for
```
for( variable assignment; condition; iteration process)

$ gawk '{
> total = 0
> for (i = 1; i < 4; i++)
> {
>     total += $1
> }
> avg = total / 3
> print "Average:",avg
> }' number
Average: 130
Average: 160
Average: 145
```
5. 格式化输出
printf 命令格式：printf "format string", var1, var2 . . .
常用格式控制符如下表所示：
```
控制字符	描述
c	将数字显示为对应的 ASCII 字符
d 或 i	显示整数
e	将数字以科学记数法显示
f	显示浮点数
g	以科学计算法或浮点数显示（看哪种更短）
o	以八进制显示
s	显示字符串
x	以十六进制显示
X	以十六进制显示，使用大写的 A-F
$ gawk 'BEGIN{
> x = 10 * 100
> printf "The answer is: %e\n", x
> }'
The answer is: 1.000000e+03
```
除控制字符以外，还可以使用另外三种修饰符以对输出进行更多的控制。
```
width ：该数值用于指定输出的最小宽度。如长度不够，用空格补充
prec ：该数值用于指定浮点数的精确度，或者字符串能包含字符的最大数量
-（减号）：格式化输出时，使用左对齐
$ gawk 'BEGIN{FS="\n"; RS=""} {printf "%16s %s\n", $1, $4}' people.txt
    Riley Mullen (312)555-1234
  Frank Williams (317)555-9876
$
$ gawk 'BEGIN{FS="\n"; RS=""} {printf "%-16s %s\n", $1, $4}' people.txt
Riley Mullen     (312)555-1234
Frank Williams   (317)555-9876
```
参考下面的示例， %10.1f 中的 10 用于指定字段的最小宽度（右对齐，前面用空格补），.1 用于指定精确度。
```
$ gawk '{
> total = 0
> for (i = 1; i < 4; i++)
> {
>   total += $i
> }
> avg = total / 3
> printf "Average: %10.1f\n",avg
> }' number
Average:      128.3
Average:      137.7
Average:      176.7
```
6. 内建函数
数学函数
```
函数	描述
atan2(x,y)	x / y 的正切
cos(x)	x 的余弦
exp(x)	x 以 e 为底的指数
int(x)	x 的整数部分
log(x)	x 的自然对数
rand()	生成介于 0 和 1 之间的随机数
sin(x)	x 的正弦
sqrt(x)	x 的平方根
srand(x)	指定生成随机数的种子
$  gawk 'BEGIN{x=exp(100); print x}'
26881171418161356094253400435962903554686976
$  gawk 'BEGIN{x=exp(1000); print x}'
inf
```
字符串函数
```
函数	描述
gensub(r, s, h [, t])	该函数用于检索字符串（默认为 $0 ，如 t 指定，则检索字符串 t），用正则表达式 r 进行匹配，并将匹配结果替换为 s 。如 h 为 "g" 或 "G" ，则执行全局替换；如 h 为数字，则只将第 h 个匹配项替换为 s
gsub(r, s [,t]	该函数用于检索字符串（默认为 $0 ，如 t 指定，则检索字符串 t），用正则表达式 r 进行匹配，并将匹配结果替换为 s （全局替换）
index(s, t)	该函数用于返回字符串 t 在字符串 s 中的位置索引（如 s 不包含 t ，则返回 0）
length([s])	该函数用于返回字符串 s 的长度，如 s 未指定，则返回 $0 的长度
match(s, r )	该函数用于返回字符串 s 中正则表达式 r 的位置索引
split(s, a [,r])	该函数用于将字符串 s 根据 FS 符分割后的字段保存在数组 a 中。如已指定正则表达式 r ，则根据 r 而不是 FS 进行分割
sprintf(format, variablies)	该函数用于返回一个格式化后的字符串，该字符串类似 printf 函数的输出
sub(r, s [,t])	该函数用于检索指定字符串 t （如果未指定 t ，则检索 $0），并使用 s 替换第一个符合条件的匹配结果
tolower(s)	将字符串 s 中的所有字符转换成小写
toupper(s)	将字符串 s 中的所有字符转换成大写
$ gawk 'BEGIN{
> x = "hello world, hello gawk, hello text"
> y = gensub("hello","nihao","g",x)
> print y}'
nihao world, nihao gawk, nihao text
$ gawk 'BEGIN{
> x = "hello world, hello gawk, hello text"
> y = gensub("hello","nihao",2,x)
> print y}'
hello world, nihao gawk, hello text
$
$ gawk 'BEGIN{
> x = "hello world, hello gawk, hello text"
> gsub("hello","nihao",x)
> print x}'
nihao world, nihao gawk, nihao text
$
$ gawk 'BEGIN{
> x = "hello world, hello gawk, hello text"
> split(x,var)
> print var[2] var[4] var[6]}'
world,gawk,text
$ gawk 'BEGIN{
> x = "hello world, hello gawk, hello text"
> split(x,var,",")
> print var[1] var[2] var[3]}'
hello world hello gawk hello text
```
时间函数
```
函数	描述
mktime(datespec)	将普通格式（ YYYY MM DD HH MM SS ）的时间日期转换成时间戳
strftime(format [,timestamp])	将指定时间戳（如未指定，使用当前时间戳）转换成指定的时间日期格式
systime()	返回当前时间的时间戳
$ gawk 'BEGIN{
> date = systime()
> day = strftime("%A, %B %d, %Y", date)
> print day
> }'
Saturday, June 30, 2018
```
用户自定义函数
定义函数
```
function name([variables])
{
    statements
}
```
使用函数
```
$ gawk '
> function myprint()
> {
>     printf "%-16s - %s\n", $1, $4
> }
> BEGIN{FS="\n"; RS=""}
> {
>     myprint()
> }' people.txt
Riley Mullen     - (312)555-1234
Frank Williams   - (317)555-9876
```
函数库
创建函数库
```
$ cat funclib
function myprint()
{
    printf "%-16s - %s\n", $1, $4
}
function myrand(limit)
{
    return int(limit * rand())
}
function printthird()
{
    print $3
}
```
调用函数库
```
$ cat script
BEGIN{ FS="\n"; RS=""}
{
    myprint()
}
$
$ gawk -f funclib -f script people.txt
Riley Mullen     - (312)555-1234
Frank Williams   - (317)555-9876
```
参考书籍
Linux Command Line and Shell Scripting Bible 3rd Edition

> 链接：https://www.jianshu.com/p/b46685e7bcbc
