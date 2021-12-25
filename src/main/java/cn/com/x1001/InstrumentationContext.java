package cn.com.x1001;

import cn.com.x1001.classmap.*;
import cn.com.x1001.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author keven1z
 * @Date 2021/6/17
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
    private CopyOnWriteArraySet<HookClass> hookClasses = new CopyOnWriteArraySet<>();
    private  String agentID;

    public CopyOnWriteArraySet<HookClass> getClassHashSet() {
        return hookClasses;
    }
    public void register(String agentID){
        this.agentID = agentID;
    }
    public String getAgentID(){
        return this.agentID;
    }
    public void addHook(HookClass hookClass) {
        this.getClassHashSet().add(hookClass);
    }

    public HookGraph getClassMap() {
        return classMap;
    }

    /**
     * 是否为hook点
     */
    public boolean isHookClass(String className) {
        for (HookClass hookHookClass : getClassHashSet()) {
            String hookClassName = hookHookClass.getClassName();
            if (hookClassName.equals(className)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHookClass(String className, String method, String desc) {
        for (HookClass hookHookClass : getClassHashSet()) {
            String hookClassName = hookHookClass.getClassName();
            String m = hookHookClass.getMethod();
            String d = hookHookClass.getDesc();
            if (hookClassName.equals(className) && m.equals(method) && d.equals(desc)) {
                return true;
            }
        }
        return false;
    }

    public HookClass getHookClass(Set<HookClass> hookClasses, String method, String desc) {

        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            String d = hookClass.getDesc();
            if (m.equals(method) && d.equals(desc)) {
                return hookClass;
            }
        }
        return null;
    }

    public HookClass getHookClass(String className, String method, String desc) {
        Set<HookClass> hookClasses = getHookClasses(className);
        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            String d = hookClass.getDesc();
            if (m.equals(method) && d.equals(desc)) {
                return hookClass;
            }
        }
        return null;
    }

    /**
     * 設置已hook
     */
    public void setHooked(String className) {
        Set<HookClass> hookClasses = getHookClasses(className);
        for (HookClass hookClass : hookClasses) {
            hookClass.setHooked(true);
        }
    }

    public Set<HookClass> getHookClasses(String className) {
        HashSet<HookClass> hashSet = new HashSet<>();
        for (HookClass hookHookClass : getClassHashSet()) {
            String hookClassName = hookHookClass.getClassName();
            if (hookClassName.equals(className)) {
                hashSet.add(hookHookClass);
            }
        }
        return hashSet;
    }

    public boolean containAction(String className, int action) {
        Set<HookClass> hookClasses = getHookClasses(className);
        for (HookClass hookClass : hookClasses) {
            Set<Integer> actions = hookClass.getActions();
            if (actions.contains(action)) return true;
        }
        return false;
    }

    /**
     * 通过父类增加hook点
     *
     * @param className
     * @param superClassName
     */
    public void addHooKClass(String className, String superClassName) {
        Set<HookClass> superHookClasses = getHookClasses(superClassName);
        for (HookClass hookClass : superHookClasses) {
            HookClass hc = new HookClass(className, hookClass.getMethod(), hookClass.getDesc(), hookClass.getReturnValue(), hookClass.getParameters(), hookClass.getActions());
            getClassHashSet().add(hc);
        }
    }

    public void addHooKClass(String className, Set<ClassVertex> childClasses) {
        Set<HookClass> hookClasses = getHookClasses(className);

        for (HookClass hookClass : hookClasses) {
            for (ClassVertex classVertex : childClasses) {
                HookClass hc = new HookClass(classVertex.getClassName(), hookClass.getMethod(), hookClass.getDesc(), hookClass.getReturnValue(), hookClass.getParameters(), hookClass.getActions());
                getClassHashSet().add(hc);
            }
        }
    }

    /**
     * 判断该节点的父类是否在hook表中
     *
     * @param className 待hook的className
     */
    public Set<ClassVertex> getChildClasses(String className) {
        ClassVertex vertex = classMap.getVertex(className);
        HashSet<Edge> toEdges = getClassMap().getToEdges(vertex);
        HashSet<ClassVertex> classVertices = new HashSet<>();
        for (Edge edge : toEdges) {
            ClassVertex from = edge.getFrom();
            if (!ClassUtil.isInterface(from.getAccess())) {
                classVertices.add(from);
            } else classVertices.addAll(getChildClasses(from.getClassName()));
        }
        return classVertices;
    }

    public ClassVertex getSuperClasses(String className) {
        ClassVertex classVertex = getClassMap().getVertex(className);
        /*如果为接口则不加入hook点*/
        if (ClassUtil.isInterface(classVertex.getAccess())) {
            return null;
        }
        HashSet<Edge> fromEdges = getClassMap().getFromEdges(classVertex);
        for (Edge edge : fromEdges) {
            ClassVertex to = edge.getTo();
            if (isHookClass(to.getClassName())) {
                return to;
            } else getSuperClasses(to.getClassName());
        }
        return null;
    }
}
