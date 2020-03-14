#!/usr/bin/env bash

#该例子使用了-n选项，后接数值1，指示read命令只要接受到一个字符就退出。只要按下一个字符进行回答，read命令立即
#接受输入并将其传给变量。无需按回车键。
read -n1 -p "Do you want to continue [Y/N]?" answer
case $answer in
    Y | y)
        echo "fine ,continue";;
    N | n)
        echo "ok,good bye";;
    *)
        echo "error choice";;
esac

# 默读（输入不显示在监视器上）
read  -s  -p "Enter your password:" pass
echo "your password is $pass"
exit 0



