package com.alison.graph;

import java.io.IOException;
import java.util.Scanner;

/**
 * Java: 邻接表表示的"无向有权图(List Undirected Graph)"
 * <p>
 * 先存储数组VNode[], 再存储边, 无向的边，存储需要存储两次， A-C, 存储A->C, C->A
 * <p>
 * 理解
 * <p>
 * 链接 ->https://www.cnblogs.com/skywang12345/p/3707612.html
 */
public class ListUDWeightG {
    // 邻接表中表对应的链表的顶点
    private class ENode {
        int ivex;       // 该边所指向的顶点的位置
        int weight;     // 权重
        ENode nextEdge; // 指向下一条弧的指针
    }

    // 邻接表中表的顶点
    private class VNode {
        char data;          // 顶点信息
        ENode firstEdge;    // 指向第一条依附该顶点的弧
    }

    private VNode[] mVexs;  // 顶点数组

    /*
     * 创建图(自己输入数据)
     */
    public ListUDWeightG() {
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
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = readChar();
            mVexs[i].firstEdge = null;
        }
        // 初始化"边"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int weight = readInt();
            int cIndex1 = getPosition(c1);
            int cIndex2 = getPosition(c2);
            // 初始化node1
            ENode node1 = new ENode();
            // 设置node1的ivex值为cIndex2, 这个值是数组mVexs的下标
            node1.ivex = cIndex2;
            node1.weight = weight;
            // 将node1链接到"cIndex1所在链表的末尾"
            if (mVexs[cIndex1].firstEdge == null)
                mVexs[cIndex1].firstEdge = node1;
            else
                linkLast(mVexs[cIndex1].firstEdge, node1);
            // 初始化node2
            ENode node2 = new ENode();
            node2.ivex = cIndex1;
            node2.weight = weight;
            // 将node2链接到"cIndex2所在链表的末尾"
            if (mVexs[cIndex2].firstEdge == null)
                mVexs[cIndex2].firstEdge = node2;
            else
                linkLast(mVexs[cIndex2].firstEdge, node2);
        }
    }

    /*
     * 将node节点链接到list的最后
     */
    private void linkLast(ENode list, ENode node) {
        ENode p = list;
        while (p.nextEdge != null)
            p = p.nextEdge;
        p.nextEdge = node;
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++)
            if (mVexs[i].data == ch)
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
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public ListUDWeightG(char[] vexs, char[][] edges) {
        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;
        // 初始化"顶点"
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = new VNode();
            mVexs[i].data = vexs[i];
            mVexs[i].firstEdge = null;
        }
        // 初始化"边"
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            char c1 = edges[i][0];
            char c2 = edges[i][1];
            // 读取边的起始顶点和结束顶点
            int pIndex1 = getPosition(edges[i][0]);
            int pIndex2 = getPosition(edges[i][1]);
            // 初始化node1
            ENode node1 = new ENode();
            node1.ivex = pIndex2;
            node1.weight = edges[i][2];
            // 将node1链接到"pIndex1所在链表的末尾"
            if (mVexs[pIndex1].firstEdge == null)
                mVexs[pIndex1].firstEdge = node1;
            else
                linkLast(mVexs[pIndex1].firstEdge, node1);
            // 初始化node2
            ENode node2 = new ENode();
            node2.ivex = pIndex1;
            // 将node2链接到"p2所在链表的末尾"
            if (mVexs[pIndex2].firstEdge == null)
                mVexs[pIndex2].firstEdge = node2;
            else
                linkLast(mVexs[pIndex2].firstEdge, node2);
        }
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d--%d(%c) ", node.weight, node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
            }
            System.out.printf("\n");
        }
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
        /*
        A -- F -- G -- E
        |  \
        C -- D
        |
        B
         */
        ListUDWeightG pG;
        // 自定义"图"(输入矩阵队列)
//        pG = new ListUDWeightG();
        // 采用已有的"图"
        pG = new ListUDWeightG(vexs, edges);
        pG.print();   // 打印图
    }
}
