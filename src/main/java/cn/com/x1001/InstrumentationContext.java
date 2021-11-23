package cn.com.x1001;

import cn.com.x1001.classmap.ClassInfo;
import cn.com.x1001.classmap.Edge;
import cn.com.x1001.classmap.GraphNode;
import cn.com.x1001.classmap.HookGraph;
import cn.com.x1001.util.ClassUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author  keven1z
 * @Date  2021/6/17
 * @Description hook 的上下文信息
*/
public class InstrumentationContext {

    /**
     * 类的图集合
     */
    private HookGraph classMap = new HookGraph();
    /*
    * 存储hook的接口或类
    */
    private   Set<ClassInfo> hookClasses = new CopyOnWriteArraySet();


    public Set<ClassInfo> getClassHashSet() {
        return hookClasses;
    }

    public void addHook(ClassInfo classInfo) {
        this.getClassHashSet().add(classInfo);
    }
    public HookGraph getClassMap() {
        return classMap;
    }

    /**
     * 是否为hook点
     */
    public boolean isHookClass(String className) {
        for (ClassInfo hookClassInfo : getClassHashSet()) {
            String hookClassName = hookClassInfo.getClassName();
            if (hookClassName.equals(className)) {
                return true;
            }
        }
        return false;
    }
    public void setHooked(String className){
        GraphNode node = this.getClassMap().getNode(className);
        node.hooked();
    }
    public ClassInfo getHookClass(String className) {
        for (ClassInfo hookClassInfo : getClassHashSet()) {
            String hookClassName = hookClassInfo.getClassName();
            if (hookClassName.equals(className)) {
                return hookClassInfo;
            }
        }
        return null;
    }
    public void addHooKClass(String className,ClassInfo hookClass){
        ClassInfo vertex = getClassMap().getVertex(className);
        vertex.setMethodDesc(hookClass.getMethodDesc());
        getClassHashSet().add(vertex);
    }
    public void addHooKClass(Set<ClassInfo> classInfoHashSet,ClassInfo hookClass){
        for (ClassInfo classInfo :classInfoHashSet){
            classInfo.setMethodDesc(hookClass.getMethodDesc());
        }
        getClassHashSet().addAll(classInfoHashSet);
    }
    /**
     * 判断该节点的父类是否在hook表中
     * @param className 待hook的className
     */
    public Set<ClassInfo> getChildHookClasses(String className){
        ClassInfo superClassInfo = getClassMap().getVertex(className);
        HashSet<Edge> toEdges = getClassMap().getToEdges(superClassInfo);
        HashSet<ClassInfo> classInfos = new HashSet<>();
        for(Edge edge:toEdges){
            ClassInfo from = edge.getFrom();
            if (!ClassUtil.isInterface(from.getAccess())){
                classInfos.add(from);
            }
            else classInfos.addAll(getChildHookClasses(from.getClassName()));
        }
        return classInfos;
    }
    public ClassInfo getSuperHookClasses(String className){
        ClassInfo classInfo = getClassMap().getVertex(className);
        /*如果为接口则不加入hook点*/
        if (ClassUtil.isInterface(classInfo.getAccess())){
            return null;
        }
        HashSet<Edge> fromEdges = getClassMap().getFromEdges(classInfo);
        for(Edge edge:fromEdges){
            ClassInfo to = edge.getTo();
            if (isHookClass(to.getClassName())){
                return to;
            }
            else getSuperHookClasses(to.getClassName());
        }
        return null;
    }
}
