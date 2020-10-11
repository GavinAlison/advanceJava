# 目录
## 【栈】
-   用数组实现一个顺序栈
-   用z链表实现一个链式栈
-   编程模拟实现一个浏览器的前进、后退功能

## leetcode题

### 栈
-   Valid Parentheses（有效的括号）    
英文版：https://leetcode.com/problems/valid-parentheses/    
中文版：https://leetcode-cn.com/problems/valid-parentheses/    

-   Longest Valid Parentheses（最长有效的括号）    
英文版：https://leetcode.com/problems/longest-valid-parentheses/    
中文版：https://leetcode-cn.com/problems/longest-valid-parentheses/    

-   Evaluate Reverse Polish Notatio（逆波兰表达式求值）   
英文版：https://leetcode.com/problems/evaluate-reverse-polish-notation/    
中文版：https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/    

--------------------

## 内容content

顺序栈和链栈      

### 顺序栈     
利用一组地址连续的存储单元依次存放栈底到栈顶的数据元素，栈底位置固定不变，栈顶位置随着入栈和出栈操作而变化。

-   算法思想
```
使用数组实现，记录数组的大小，栈顶指针位置   
init -->  传递容量，设置数组的长度，并设置capacity大小， top =-1
push -->  先判断是否满(通过数组的长度与top+1比较), 未满可插入，top++, 数组[top] = value 
pop  -->  先判断是否为空(通过top与-1)， 不为空可推出， capacity--, 记录数组[top]， top--, 返回记录数
peek -->  先判断是否为空(通过top与-1)， 不为空可取出
display --> 利用变量head，记录top值，作为一个遍历指针，遍历数组，知道head==-1
```
-   代码实现

### 链栈    
链栈是一种特殊的线性链表，和所有链表一样，是动态存储结构，无需预先分配存储空间。

-   算法思想
```
用链表实现栈的先进后出，实现栈的push，pop，peak方法。主要就是利用链表的头结点作为栈顶的元素。
1.当要push的时候，相当于新new一个头结点，然后让新节点指向单链表的头结点。以新节点作为单链表的头节点即可。
2.当要pop的时候，只要将链表的头指针后移到它的next，将next作为新的头结点即可
3.当要peak的时候，只要返回头结点的值就好了。
```

逻辑结构属于操作受限的线性表  
特点  
先进后出,只允许在栈顶操作       



#### 解答：
-   用数组实现一个顺序栈：--> [ArrayStack](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/ArrayStack.java)
-   用z链表实现一个链式栈: -->  [LinkedStack](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/LinkedStack.java)
-   编程模拟实现一个浏览器的前进、后退功能：-->  [Browser](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/Browser.java)

-   Valid Parentheses（有效的括号）  --->  [StackDemo##isValid](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/StackDemo.java)
-   Longest Valid Parentheses（最长有效的括号）  --> [StackDemo##longestValidParentheses](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/StackDemo.java)
-   Evaluate Reverse Polish Notatio（逆波兰表达式求值） ---> [StackDemo##evalRPN](https://github.com/GavinAlison/leetcode/tree/master/algorithm/src/main/java/com/alison/StackDemo.java)


## 其他
详细的知识点：  [栈](https://blog.csdn.net/qq_16399991/article/details/84261849)
https://blog.csdn.net/qq_16399991/article/details/84261849


