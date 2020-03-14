#!/usr/bin/env bash


# testing the at command
#crontab是执行定时任务的命令,那么at又是什么呢?
# 其实at也是定时任务命令,不同的是crontab是执行循环任务,at执行一次性任务

#at命令的基本形式为：
#at [-f script] [-m -l -r] [time] [date]
#-f script 是所要提交的脚本或命令。
#-l 列出当前所有等待运行的作业。atq命令具有相同的作用。
#-r 清除作业。为了清除某个作业，还要提供相应的作业标识（ID）；
# 有些UNIX变体只接受atrm作为清除命令。
#-m 作业完成后给用户发邮件。
#time at命令的时间格式非常灵活；可以是H、HH . HHMM、HH:MM或H:M，其中H和M分别是小时和分钟。还可以使用a.m.或p.m.。
#date 日期格式可以是月份数或日期数，而且at命令还能够识别诸如today、tomorrow这样的词。


#查看任务使用
atq
at -l
#删除任务使用
atrm 1

at -f 4.sh 21:38
