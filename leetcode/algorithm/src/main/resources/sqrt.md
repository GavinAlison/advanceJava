## 描述

实现 int sqrt(int x) 函数。

计算并返回 x 的平方根，其中 x 是非负整数。

由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。

## 解答

-   二分查找法应用于搜索平方根的思想很简单

-   牛顿迭代法
``
SortDemo##mySqrt2


牛顿法的思想：

在迭代过程中，以直线代替曲线，用一阶泰勒展式（即在当前点的切线）代替原曲线，求直线与 xx 轴的交点，
重复这个过程直到收敛。


说明：
1、以上图片来自[《牛顿法与拟牛顿法》](https://blog.csdn.net/batuwuhanpei/article/details/51979831)；

2、@LOAFER 的题解[《牛顿迭代法》](https://leetcode-cn.com/problems/sqrtx/solution/niu-dun-die-dai-fa-by-loafer/) 
的图和文字说明更好，而知乎问答[《如何通俗易懂地讲解牛顿迭代法求开方？数值分析？》](https://www.zhihu.com/question/20690553)
里面干货就更多了，建议大家出门左转观看，我这篇题解只是展示一下迭代公式如何计算。

https://leetcode-cn.com/problems/sqrtx/solution/er-fen-cha-zhao-niu-dun-fa-python-dai-ma-by-liweiw/

注意：牛顿法得到的是平方根的浮点型精确值（可能会有一定误差），根据题目中的要求，
把最后得到的这个数转换为 int 型，即去掉小数部分即可。

牛顿法的应用：一个是求方程的根，另一个是求解最优化问题








