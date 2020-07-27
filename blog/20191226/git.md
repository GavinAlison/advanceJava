# git 一些命令
## git checkout 
简单的练习一下git checkout ,明白git checkout的原理与用法

`git checkout feature-a`
切换到feature-a分支，

底层实现原理： 将当前的HEAD指针由master最后一次commit id指向转换成`feature-a`分支的最后一次commit id.

`git checkout -b <new branch> [start_point]`
检出当前分支的开始commit id到新的分支
`git checkout branch`
switch branch
`git checkout <commit> [--] <paths>`
将paths文件检出到当前工作区

## git branch <branch> <start_point>
以某个commit创建新分支.
 在通常情况下，我们都会在当前分支的基础上，创建新分支。
 
## git checkout --detach <branch>
切换到分支的游离状态，默认以该分支下的最后一次提交ID.

## git checkout -B <branch>
可以强制创建新的分支. -B 强制

## git checkout --orphan <branch>
它会基于当前所在分支新建一个赤裸裸的分支，没有任何的提交历史，但是当前分支的内容一一俱全。

## git checkout --merge <branch>
这个命令适用于在切换分支的时候，将当前分支修改的内容一起打包带走，同步到切换的分支下。

有两个需要注意的问题。

第一，如果当前分支和切换分支间的内容不同的话，容易造成冲突。

第二，切换到新分支后，当前分支修改过的内容就丢失了。

## git checkout -p <branch>

这个命令可以用来打补丁。这个命令主要用来比较两个分支间的差异内容，并提供交互式的界面来
选择进一步的操作。这个命令不仅可以比较两个分支间的差异，还可以比较单个文件的差异哦！


