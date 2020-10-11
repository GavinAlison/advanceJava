package com.alison.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @Author alison
 * @Date 2019/8/28  23:48
 * @Version 1.0
 * @Description
 * @link [深入理解拓扑排序（Topological sort)](https://www.jianshu.com/p/3347f54a3187)
 * 时间复杂度： O(n + e)，其中n为图中的结点数目，e为图中的边的数目
 * 空间复杂度：O(n)
 */
public class TopologicalSort {
    /**
     * Get topological ordering of the input directed graph
     *
     * @param n             number of nodes in the graph
     * @param adjacencyList adjacency list representation of the input directed graph
     * @return topological ordering of the graph stored in an List<Integer>.
     */
    public List<Integer> topologicalSort(int n, int[][] adjacencyList) {
        List<Integer> topoRes = new ArrayList<>();
        int[] inDegree = new int[n];
        for (int[] parent : adjacencyList) {
            for (int child : parent) {
                inDegree[child]++;
            }
        }
        Deque<Integer> deque = new ArrayDeque<>();
        // start from nodes whose indegree are 0
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0)
                deque.offer(i);
        }
        while (!deque.isEmpty()) {
            int curr = deque.poll();
            topoRes.add(curr);
            for (int child : adjacencyList[curr]) {
                inDegree[child]--;
                if (inDegree[child] == 0)
                    deque.offer(child);
            }
        }
        return topoRes.size() == n ? topoRes : new ArrayList<>();
    }
}
