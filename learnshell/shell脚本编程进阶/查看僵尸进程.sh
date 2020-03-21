#!/usr/bin/env bash

#查看僵尸进程
ps -ux | awk '{print $8, $11,$12}' | grep Z







