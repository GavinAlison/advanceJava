package com.alison.graph;

import java.io.IOException;
import java.util.Scanner;

/**
 * Java: 邻接矩阵图   有向有权图
 * <p>
 * 链接--》https://www.cnblogs.com/skywang12345/p/3707618.html
 */
public class MatrixDirectionWeightGraph {

    private char[] mVexs;       // 顶点集合
    private int[][] mMatrix;    // 邻接矩阵

    /*
     * 创建图(自己输入数据)
     */
    public MatrixDirectionWeightGraph() {
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
            char c1 = readChar();
            char c2 = readChar();
            int weight = readInt();
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);
            if (p1 == -1 || p2 == -1) {
                System.out.printf("input error: invalid edge!\n");
                return;
            }
            mMatrix[p1][p2] = weight;
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixDirectionWeightGraph(char[] vexs, char[][] edges) {

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
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);
            // 这里不需要设置mMatrix[p2][p1] 的值
            // 设置权重
            mMatrix[p1][p2] = edges[i][2];
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
//                System.out.printf("(%c,%c)-(%d) ", mVexs[i], mVexs[j], mMatrix[i][j]);
                System.out.printf("(%d) ", mMatrix[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'B', 2},
                {'B', 'C', 1},
                {'B', 'E', 2},
                {'B', 'F', 1},
                {'C', 'E', 2},
                {'D', 'C', 1},
                {'E', 'B', 2},
                {'E', 'D', 1},
                {'F', 'G', 2}};
        MatrixDirectionWeightGraph pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixDG();
        // 采用已有的"图"
        pG = new MatrixDirectionWeightGraph(vexs, edges);

        pG.print();   // 打印图
    }
}
