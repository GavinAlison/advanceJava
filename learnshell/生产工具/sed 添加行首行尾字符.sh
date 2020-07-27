#!/bin/bash

# 在每行的头添加字符，比如"HEAD"，命令如下：
sed 's/^/HEAD&/g' test

# 在每行的行尾添加字符，比如“TAIL”，命令如下：

sed 's/$/&TAIL/g' test

# 几点说明：

# 1."^"代表行首，"$"代表行尾

# 2.'s/$/&TAIL/g'中的字符g代表每行出现的字符全部替换，如果想在特定字符处添加，g就有用了，否则只会替换每行第一个，而不继续往后找了

# 如果想导出文件，在命令末尾加"> outfile_name"；如果想在原文件上更改，添加选项"-i"，

cat test

sed 's/a/INSERT/g' test

cat test

sed -i 's/a/INSERT/g' test
cat test


#  转换test的换行符 
sed  -i  's/\r//' test






