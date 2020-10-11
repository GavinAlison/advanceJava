package com.alison.graph;

import java.io.IOException;
import java.util.Scanner;

/**
 * Java: 邻接矩阵表示的"无向无权图(List Undirected Graph)"
 * <p>
 * link-->
 * 1.https://www.cnblogs.com/skywang12345/p/3707604.html
 * 2.https://github.com/wangkuiwu/datastructs_and_algorithm/blob/master/source/graph/dijkstra/udg/java/MatrixUDG.java
 */
public class MatrixUDG {

    private int mEdgNum;        // 边的数量
    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵
    private static final int MAX = Integer.MAX_VALUE;//  最大值

    /*
     * 创建图(自己输入数据)
     */
    public MatrixUDG() {
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
            int pIndex1 = getPosition(rowChar);
            int pIndex2 = getPosition(colChar);

            if (pIndex1 == -1 || pIndex2 == -1) {
                System.out.printf("input error: invalid edge!\n");
                return;
            }
            mMatrix[pIndex1][pIndex2] = 1;
            mMatrix[pIndex2][pIndex1] = 1;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixUDG(char[] vexs, char[][] edges) {
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
            mMatrix[pIndex1][pIndex2] = 1;
            mMatrix[pIndex2][pIndex1] = 1;
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
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%10d ", mMatrix[i][j]);
            System.out.printf("\n");
        }
    }

    /**
     * 返回顶点v的第一个领接顶点的索引， 失败则返回-1
     *
     * @param v
     * @return
     */
    private int firstVertex(int v) {
        if (v < 0 || v > (mVexs.length - 1))
            return -1;
        for (int i = 0; i < mVexs.length; i++) {
            if (mMatrix[v][i] != 0 && mMatrix[v][i] != MAX)
                return i;
        }
        return -1;
    }

    /**
     * 返回顶点v相对于w的下一个邻接顶点的索引，失败返回-1
     *
     * @param v
     * @param w
     * @return
     */
    private int nextVertex(int v, int w) {
        if (v < 0 || v > mVexs.length - 1 || w < 0 || w > (mVexs.length - 1))
            return -1;
        for (int i = w + 1; i < mVexs.length; i++) {
            if (mMatrix[v][i] != 0 && mMatrix[v][i] != MAX)
                return i;
        }
        return -1;
    }


    /**
     * 深度优先搜索遍历图的递归实现
     */

    private void DFS(int i, boolean[] isVisited) {
        isVisited[i] = true;
        System.out.printf("%c ", mVexs[i]);
        // 遍历该顶点的所有邻接顶点， 没有访问过，那么就继续往下走
        for (int w = firstVertex(i); w > 0; w = nextVertex(i, w)) {
            if (!isVisited[w])
                DFS(w, isVisited);
        }
    }

    /**
     * 深度优先搜索图
     */
    public void DFS() {
        boolean[] isVisited = new boolean[mVexs.length];
        for (boolean visited : isVisited) {
            visited = false;
        }
        System.out.println("DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!isVisited[i])
                DFS(i, isVisited);
        }
        System.out.print("\n");
    }

    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS() {
        int head = 0;
        int rear = 0;
        int[] queue = new int[mVexs.length];            // 辅组队列
        boolean[] visited = new boolean[mVexs.length];  // 顶点访问标记
        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;
        System.out.printf("BFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", mVexs[i]);
                // rear 指向入队指针
                queue[rear++] = i;  // 入队列
            }
            while (head != rear) {
                // head 指向出队指针
                int j = queue[head++];  // 出队列 peek
                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) { //k是为访问的邻接顶点
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.printf("%c ", mVexs[k]);
                        queue[rear++] = k; // offer
                    }
                }
            }
        }
        System.out.printf("\n");
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'C'},
                {'A', 'D'},
                {'A', 'F'},
                {'B', 'C'},
                {'C', 'D'},
                {'E', 'G'},
                {'F', 'G'}};
        MatrixUDG pG;
        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new MatrixUDG(vexs, edges);
        pG.print();   // 打印图
    }
}
