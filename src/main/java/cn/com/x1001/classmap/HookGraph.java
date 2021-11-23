package cn.com.x1001.classmap;

import java.util.*;

/**
 * hook信息图结构
 * @author keven1z
 * @date 2021/11/18
 */
public class HookGraph {

    /**
     * 节点链表
     *
     * @since 0.0.2
     */
    private Set<GraphNode> nodeSet;

    /**
     * 初始化有向图
     *
     * @since 0.0.2
     */
    public HookGraph() {
        this.nodeSet = Collections.synchronizedSet(new LinkedHashSet<>());
    }

    public void addNode(ClassInfo classInfo) {
        GraphNode node = getNode(classInfo);
        if (node != null){
            node.getVertex().setMethodDesc(classInfo.getMethodDesc());
            node.getVertex().setAccess(classInfo.getAccess());
        }
        else {
            node = new GraphNode(classInfo);
            this.nodeSet.add(node);
        }
        // 直接加入到集合中

    }
    public ClassInfo addNode(String className,int access) {
        GraphNode node = this.getNode(className);
        if (node != null){
            return node.getVertex();
        }
        ClassInfo classInfo = new ClassInfo();
        classInfo.setClassName(className);
        classInfo.setAccess(access);
        node = new GraphNode(classInfo);
        // 直接加入到集合中
        addNode(node);
        return classInfo;
    }
    public void addNode(GraphNode node) {
        // 直接加入到集合中
        this.nodeSet.add(node);
    }
    public int getNodeSize(){
        return this.nodeSet.size();
    }
    public boolean removeVertex(ClassInfo v) {
        //1. 移除一个顶点
        //2. 所有和这个顶点关联的边也要被移除
        nodeSet.removeIf(graphNode -> v.equals(graphNode.getVertex()));

        return true;
    }

    public void addEdge(Edge edge) {
        //1. 新增一条边，直接遍历列表。
        // 如果存在这条的起始节点，则将这条边加入。
        // 如果不存在，则直接报错即可。

        for (GraphNode graphNode : nodeSet) {
            ClassInfo from = edge.getFrom();
            ClassInfo originFrom = graphNode.getVertex();

            // 起始节点在开头
            if (from.equals(originFrom)) {
                graphNode.getEdgeSet().add(edge);
            }
        }
    }

    public boolean removeEdge(Edge edge) {
        // 直接从列表中对应的节点，移除即可
        GraphNode node = getNode(edge);
        if (null != node) {
            // 移除目标为 to 的边
            node.remove(edge.getTo());
        }

        return true;
    }



    /**
     * 获取图节点
     *
     * @param edge 边
     * @return 图节点
     */
    private GraphNode getNode(final Edge edge) {
        for (GraphNode node : nodeSet) {
            final ClassInfo from = edge.getFrom();

            if (node.getVertex().equals(from)) {
                return node;
            }
        }

        return null;
    }

    /**
     * 获取对应的图节点
     *
     * @param vertex 顶点
     * @return 图节点
     * @since 0.0.2
     */
    public GraphNode getNode(ClassInfo vertex) {
        for (GraphNode node : nodeSet) {
            if (node.getVertex().equals(vertex)) {
                return node;
            }
        }
        return null;
    }
    /**
     * 获取className当前class节点
     *
     * @param className
     * @return
     */
    public GraphNode getNode(String className) {
        for (GraphNode node : nodeSet) {
            ClassInfo vertex = node.getVertex();
            if (vertex != null) {
                if (vertex.getClassName().equals(className)) return node;
            }
        }
        return null;
    }

    /**
     * 获取className当前class頂點
     *
     * @param className
     * @return
     */
    public ClassInfo getVertex(String className) {

        GraphNode nodeByClassName = getNode(className);
        if (nodeByClassName == null) return null;
        return nodeByClassName.getVertex();
    }



    public HashSet<Edge> getToEdges(ClassInfo to) {
        HashSet<Edge> edges = new HashSet<>();
        for (Edge edge : this.getEdges()) {
            ClassInfo dest = edge.getTo();
            if (dest.equals(to)) {
                edges.add(edge);
            }
        }
        return edges;
    }
    public HashSet<Edge> getFromEdges(ClassInfo from) {
        HashSet<Edge> edges = new HashSet<>();
        for (Edge edge : this.getEdges()) {
            ClassInfo dest = edge.getFrom();
            if (dest.equals(from)) {
                edges.add(edge);
            }
        }
        return edges;
    }


    public HashSet<Edge> getEdges() {
        HashSet<Edge> edges = new HashSet<>();
        for (GraphNode node : this.nodeSet) {
            Set<Edge> edgeSet = node.getEdgeSet();
            edges.addAll(edgeSet);
        }
        return edges;
    }

    /**
     * 清除图
     */
    public void clear() {
        if (this.nodeSet != null) this.nodeSet.clear();
    }

    public void addEdge(ClassInfo from, ClassInfo to) {
        if (from == null || to == null) return;
        Edge classInfoEdge = new Edge(from, to);
        this.addEdge(classInfoEdge);
    }


}
