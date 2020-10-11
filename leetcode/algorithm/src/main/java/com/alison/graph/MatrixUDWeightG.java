package com.alison.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.Scanner;

/**
 * Java: 邻接矩阵表示的"无向有权图(List Undirected Graph)"
 * <p>
 * 链接--》 [无向有权图讲解](https://www.cnblogs.com/skywang12345/p/3707604.html)
 * 2. [迪杰斯特拉算法介绍](https://www.cnblogs.com/he-px/p/6677063.html)
 * 3. [prim算法和kruskal算法](https://www.cnblogs.com/haimishasha/p/5363390.html)
 */
public class MatrixUDWeightG {

    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵
    private static final int MAX = Integer.MAX_VALUE;   // 最大值
    private int mEdgNum;       // 统计边

    /*
     * 创建图(自己输入数据)
     */
    public MatrixUDWeightG() {
        // 输入"顶点数"和"边数"
        System.out.printf("input vertex number: ");
        int vlen = readInt();
        System.out.printf("input edge number: ");
        int elen = readInt();
        if (vlen < 1 || elen < 1 || (elen > (vlen * (vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return;
        }
        // 初始化"顶点"
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = readChar();
        }
        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char rowChar = readChar();
            char colChar = readChar();
            int weight = readInt();
            int pIndex1 = getPosition(rowChar);
            int pIndex2 = getPosition(colChar);

            if (pIndex1 == -1 || pIndex2 == -1) {
                System.out.printf("input error: invalid edge!\n");
                return;
            }
            mMatrix[pIndex1][pIndex2] = weight;
            mMatrix[pIndex2][pIndex1] = weight;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixUDWeightG(char[] vexs, char[][] edges) {
        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;
        // 初始化"顶点"
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++)
            mVexs[i] = vexs[i];
        // 初始化"边"
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            int pIndex1 = getPosition(edges[i][0]);
            int pIndex2 = getPosition(edges[i][1]);
            mMatrix[pIndex1][pIndex2] = edges[i][2];
            mMatrix[pIndex2][pIndex1] = edges[i][2];
        }
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++)
            if (mVexs[i] == ch)
                return i;
        return -1;
    }

    /*
     * 读取一个输入字符
     */
    private char readChar() {
        char ch = '0';
        do {
            try {
                ch = (char) System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')));

        return ch;
    }

    /*
     * 读取一个输入字符
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("Martix Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++) {
                System.out.printf("(%c,%c)--%d ", mVexs[i], mVexs[j], mMatrix[i][j]);
//                System.out.printf("%d ", mMatrix[i][j]);
            }
            System.out.printf("\n");
        }
    }

    /*
     * Dijkstra最短路径。
     * 即，统计图中"顶点vs"到其它各个顶点的最短路径。
     *
     * 参数说明：
     *       vs -- 起始顶点(start vertex)。即计算"顶点vs"到其它顶点的最短路径。
     *     prev -- 前驱顶点数组。即，prev[i]的值是"顶点vs"到"顶点i"的最短路径所经历的全部顶点中，位于"顶点i"之前的那个顶点。
     *     dist -- 长度数组。即，dist[i]是"顶点vs"到"顶点i"的最短路径的长度。
     */
    public void dijkstra(int vs, int[] prev, int[] dist) {
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取
        boolean[] flag = new boolean[mVexs.length];
        // 初始化
        for (int i = 0; i < mVexs.length; i++) {
            flag[i] = false;          // 顶点i的最短路径还没获取到。
            prev[i] = 0;              // 顶点i的前驱顶点为0。
            dist[i] = mMatrix[vs][i];  // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        }
        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        dist[vs] = 0;
        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。k为最短路径的下标
        int k = 0;
        for (int i = 1; i < mVexs.length; i++) {
            // 寻找当前最小的路径；
            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
            int min = MAX;
            for (int j = 0; j < mVexs.length; j++) {
                if (flag[j] == false && dist[j] < min) {
                    min = dist[j];
                    k = j;
                }
            }
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;
            // 修正当前最短路径和前驱顶点
            // 即，当已经"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
            for (int j = 0; j < mVexs.length; j++) {
                int tmp = (mMatrix[k][j] == MAX ? MAX : (min + mMatrix[k][j]));
                if (flag[j] == false && (tmp < dist[j])) {
                    dist[j] = tmp;
                    prev[j] = k;
                }
            }
        }
        // 打印dijkstra最短路径的结果
        System.out.printf("dijkstra(%c): \n", mVexs[vs]);
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("  shortest(%c, %c)=%d\n", mVexs[vs], mVexs[i], dist[i]);
        }
    }

    private void setMmatrix(int[][] matrix) {
        // 更改矩阵 边
        for (int i = 0; i < this.mVexs.length; i++) {
            for (int j = 0; j < this.mVexs.length; j++) {
                mMatrix[i][j] = matrix[i][j];
            }
        }
        // 统计"边"
        mEdgNum = 0;
        for (int i = 0; i < this.mVexs.length; i++) {
            for (int j = i + 1; j < this.mVexs.length; j++) {
                if (mMatrix[i][j] != MAX)
                    mEdgNum++;
            }
        }
    }

    /*
     * prim最小生成树
     * 通过加入最小邻接边的方法来建立最小生成树算法。
     * 参数说明：
     *   start -- 从图中的第start个元素开始，生成最小树
     */
    public void prim(int start) {
        int num = mVexs.length;             // 顶点个数
        int index = 0;                      // prim最小树的索引，即prims数组的索引
        char[] prims = new char[num];       // prim最小树的结果数组
        int[] weights = new int[num];       // 顶点间边的权值

        // prim最小生成树中第一个数是"图中第start个顶点"，因为是从start开始的。
        prims[index++] = mVexs[start];

        // 初始化"顶点的权值数组"，
        // 将每个顶点的权值初始化为"第start个顶点"到"该顶点"的权值。
        for (int i = 0; i < num; i++) {
            weights[i] = mMatrix[start][i];
        }
        // 将第start个顶点的权值初始化为0。
        // 可以理解为"第start个顶点到它自身的距离为0"。
        weights[start] = 0;

        for (int i = 0; i < num; i++) {
            // 由于从start开始的，因此不需要再对第start个顶点进行处理。
            if (start == i)
                continue;
            int j = 0;
            int k = 0;
            int min = MAX;
            // 在未被加入到最小生成树的顶点中，找出权值最小的顶点。
            while (j < num) {
                // 若weights[j]=0，意味着"第j个节点已经被排序过"(或者说已经加入了最小生成树中)。
                if (weights[j] != 0 && weights[j] < min) {
                    min = weights[j];
                    k = j;
                }
                j++;
            }
            // 经过上面的处理后，在未被加入到最小生成树的顶点中，权值最小的顶点是第k个顶点。
            // 将第k个顶点加入到最小生成树的结果数组中
            prims[index++] = mVexs[k];
            // 将"第k个顶点的权值"标记为0，意味着第k个顶点已经排序过了(或者说已经加入了最小树结果中)。
            weights[k] = 0;
            // 当第k个顶点被加入到最小生成树的结果数组中之后，更新其它顶点的权值。
            for (j = 0; j < num; j++) {
                // 当第j个节点没有被处理，并且需要更新时才被更新。
                if (weights[j] != 0 && mMatrix[k][j] < weights[j])
                    weights[j] = mMatrix[k][j];
            }
        }
        // 计算最小生成树的权值
        int sum = 0;
        for (int i = 1; i < index; i++) {
            int min = MAX;
            // 获取prims[i]在mMatrix中的位置
            int n = getPosition(prims[i]);
            // 在vexs[0...i]中，找出到j的权值最小的顶点
            for (int j = 0; j < i; j++) {
                int m = getPosition(prims[j]);
                if (mMatrix[m][n] < min)
                    min = mMatrix[m][n];
            }
            sum += min;
        }
        // 打印最小生成树
        System.out.printf("PRIM(%c)=%d: ", mVexs[start], sum);
        for (int i = 0; i < index; i++) {
            System.out.printf("%c ", prims[i]);
        }
        System.out.printf("\n");
    }


    /*
     * 克鲁斯卡尔（Kruskal)最小生成树
     * 根据边的权值以递增的方式逐渐建立最小生成树
     * 具体操作是:将赋权图每个顶点都看做森林，然后将图中每条邻接边的权值按照升序的方式进行排列，
     * 接着从排列好的邻接边表中抽取权值最小的边，写入该边的起始顶点和结束顶点，连接顶点将森林构成树，
     * 然后读取起始结束顶点的邻接边，优先抽取权值小的邻接边，继续连接顶点将森林构成树。
     * 添加邻接边的要求是加入到图中的邻接边不构成回路。如此反复进行，直到已经添加n-1条边为止。
     *
     * @link  [图解克鲁斯卡尔（Kruskal）算法](https://blog.csdn.net/wonder233/article/details/79101336)
     */
    public void kruskal() {
        int index = 0;                      // rets数组的索引
        int[] vends = new int[mEdgNum];     // 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
        EData[] rets = new EData[mEdgNum];  // 结果数组，保存kruskal最小生成树的边
        EData[] edges;                      // 图对应的所有边
        // 获取"图中所有的边"
        edges = getEdges();
        // 将边按照"权"的大小进行排序(从小到大)
        sortEdges(edges, mEdgNum);
        for (int i = 0; i < mEdgNum; i++) {
            int p1 = getPosition(edges[i].start);      // 获取第i条边的"起点"的序号
            int p2 = getPosition(edges[i].end);        // 获取第i条边的"终点"的序号

            int m = getEnd(vends, p1);                 // 获取p1在"已有的最小生成树"中的终点
            int n = getEnd(vends, p2);                 // 获取p2在"已有的最小生成树"中的终点
            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            if (m != n) {
                vends[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets[index++] = edges[i];           // 保存结果
            }
        }
        // 统计并打印"kruskal最小生成树"的信息
        int length = 0;
        for (int i = 0; i < index; i++) {
            length += rets[i].weight;
        }
        System.out.printf("Kruskal=%d: ", length);
        for (int i = 0; i < index; i++)
            System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
        System.out.printf("\n");
    }

    /*
     * 获取图中的边
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges;
        edges = new EData[mEdgNum];
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = i + 1; j < mVexs.length; j++) {
                if (mMatrix[i][j] != MAX) {
                    edges[index++] = new EData(mVexs[i], mVexs[j], mMatrix[i][j]);
                }
            }
        }

        return edges;
    }

    /*
     * 对边按照权值大小进行排序(由小到大)
     */
    private void sortEdges(EData[] edges, int elen) {
        for (int i = 0; i < elen; i++) {
            for (int j = i + 1; j < elen; j++) {
                if (edges[i].weight > edges[j].weight) {
                    // 交换"边i"和"边j"
                    EData tmp = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tmp;
                }
            }
        }
    }

    /*
     * 获取i的终点
     */
    private int getEnd(int[] vends, int i) {
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }

    // 边的结构体
    @Data
    @AllArgsConstructor
    private static class EData {
        char start; // 边的起点
        char end;   // 边的终点
        int weight; // 边的权重
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'C', 2},
                {'A', 'D', 1},
                {'A', 'F', 2},
                {'B', 'C', 1},
                {'C', 'D', 2},
                {'E', 'G', 1},
                {'F', 'G', 2}};
        MatrixUDWeightG pG;
        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new MatrixUDWeightG(vexs, edges);
        pG.print();   // 打印图
        int matrix[][] = {
                /*A*//*B*/     /*C*/   /*D*/   /*E*/   /*F*/   /*G*/
                /*A*/ {0, 12, MAX, MAX, MAX, 16, 14},
                /*B*/ {12, 0, 10, MAX, MAX, 7, MAX},
                /*C*/ {MAX, 10, 0, 3, 5, 6, MAX},
                /*D*/ {MAX, MAX, 3, 0, 4, MAX, MAX},
                /*E*/ {MAX, MAX, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, MAX, 2, 0, 9},
                /*G*/ {14, MAX, MAX, MAX, 8, 9, 0}};
        pG.setMmatrix(matrix);

        int[] prev = new int[pG.mVexs.length];
        int[] dist = new int[pG.mVexs.length];
        // dijkstra算法获取"第4个顶点"到其它各个顶点的最短距离
        pG.dijkstra(3, prev, dist);

        pG.prim(0);   // prim算法生成最小生成树

        pG.kruskal(); // Kruskal算法生成最小生成树
    }


}
