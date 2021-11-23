package cn.com.x1001.classmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 完整的节点信息：
 *
 * （1）顶点
 * （2）边
 *
 * 二者的集合。
 *
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class GraphNode {

    /**
     * 顶点信息
     * @since 0.0.2
     */
    private ClassInfo classInfo;

    /**
     * 以此顶点为起点的边的集合，是一个列表，列表的每一项是一条边
     *
     * （1）使用集合，避免重复
     */
    private Set<Edge> edgeSet;

    /**
     * 初始化一個節點
     * @param vertex 頂點
     */
    public GraphNode(ClassInfo vertex) {
        this.classInfo = vertex;
        this.edgeSet = new HashSet<>();
    }

    /**
     * 新增一条边
     * @param edge 边
     */
    public void add(final Edge edge) {
        edgeSet.add(edge);
    }

    /**
     * 获取目标边
     * @param to 目标边
     * @return 边
     * @since 0.0.2
     */
    public Edge get(final ClassInfo to) {
        for(Edge edge : edgeSet) {
            ClassInfo dest = edge.getTo();

            if(dest.equals(to)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * 获取目标边
     * @param to 目标边
     * @since 0.0.2
     */
    public void remove(final ClassInfo to) {
        Iterator<Edge> edgeIterable = edgeSet.iterator();

        while (edgeIterable.hasNext()) {
            Edge next = edgeIterable.next();

            if(to.equals(next.getTo())) {
                edgeIterable.remove();
                return;
            }
        }

    }

    public ClassInfo getVertex() {
        return classInfo;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public Set<ClassInfo> getToClassInfo(){
        HashSet<ClassInfo> classInfos = new HashSet<>();
        for(Edge edge:this.getEdgeSet()){
                ClassInfo from = edge.getFrom();
                if(from.equals(classInfo)){
                    classInfos.add(edge.getTo());
                }
        }
        return classInfos;
    }

    @Override
    public String toString() {
        return this.getVertex().toString();
    }

    /**
     * 设置已经hook该类
     */
    public void hooked(){
        this.classInfo.setHooked(true);
    }


}
