package com.alison.graph;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author alison
 * @Date 2019/9/1  22:13
 * @Version 1.0
 * @Description toplogicalSort的深度优先遍历
 * @link https://blog.csdn.net/kimylrong/article/details/17220455
 */
public class GraphDFS {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Vertex {
        private String name;
        private boolean visited = false;
        private Vertex parent;
        private int level = 0;// 层级

        public Vertex(String name) {
            this.name = name;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Graph {
        private Set<Vertex> vertextSet = new HashSet<>();   // 顶点
        private Map<String, List<Vertex>> edgeMap = new LinkedHashMap<String, List<Vertex>>(); // 边
    }

    public static Vertex[] topLogicalSort(Graph graph) {
        Set<Vertex> vertexSet = graph.getVertextSet();
        if (vertexSet.size() < 2) {
            return vertexSet.toArray(new Vertex[1]);
        }
        LinkedList<Vertex> sortList = new LinkedList<>();
        for (String vertexName : graph.getEdgeMap().keySet()) {
            Vertex vertex = get(vertexName, graph);
            if (vertex == null) {
                return null;
            }
            if (!vertex.visited) {
                if (graph.getEdgeMap() == null)
                    return null;
                List<Vertex> vertexList = graph.getEdgeMap().get(vertex.getName());
                if (Objects.isNull(vertexList))
                    continue;
                vertex.visited = true;
                vertex.level += 1;
                sortList.add(vertex);
                depthFirstSearch(vertex, graph, vertexList, sortList);
            }
        }
        return sortList.toArray(new Vertex[sortList.size()]);
    }

    private static Vertex get(String name, Graph graph) {
        for (Vertex vertex : graph.getVertextSet()) {
            if (vertex.getName().equalsIgnoreCase(name)) {
                return vertex;
            }
        }
        return null;
    }

    private static void depthFirstSearch(Vertex parent, Graph graph, List<Vertex> vertexList, LinkedList<Vertex> sortList) {
        for (Vertex vert : vertexList) {
            if (!vert.visited) {
                vert.visited = true;
                vert.level = vert.level + parent.level;
                sortList.add(vert);
                if (graph.getEdgeMap().get(vert.getName()) != null) {
                    vertexList = graph.getEdgeMap().get(vert.getName());
                    depthFirstSearch(vert, graph, vertexList, sortList);
                }
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        Set<Vertex> vertexSet = graph.getVertextSet();
        Map<String, List<Vertex>> edgeMap = graph.getEdgeMap();

        Vertex twoVertex = new Vertex("2");
        Vertex threeVertex = new Vertex("3");
        Vertex fiveVertex = new Vertex("5");
        Vertex sevenVertex = new Vertex("7");
        Vertex eightVertex = new Vertex("8");
        Vertex nineVertex = new Vertex("9");
        Vertex tenVertex = new Vertex("10");
        Vertex elevenVertex = new Vertex("11");

        vertexSet.add(twoVertex);
        vertexSet.add(threeVertex);
        vertexSet.add(fiveVertex);
        vertexSet.add(sevenVertex);
        vertexSet.add(eightVertex);
        vertexSet.add(nineVertex);
        vertexSet.add(tenVertex);
        vertexSet.add(elevenVertex);

        edgeMap.put(twoVertex.getName(), Lists.newArrayList(elevenVertex));
        edgeMap.put(nineVertex.getName(), Lists.newArrayList(elevenVertex, eightVertex));
        edgeMap.put(tenVertex.getName(), Lists.newArrayList(elevenVertex, threeVertex));
        edgeMap.put(elevenVertex.getName(), Lists.newArrayList(sevenVertex, fiveVertex));
        edgeMap.put(eightVertex.getName(), Lists.newArrayList(sevenVertex, threeVertex));
        Vertex[] sortedVertexs = topLogicalSort(graph);
        System.out.println(Arrays.stream(sortedVertexs).map(Vertex::getName).collect(Collectors.toList()));
    }
}
