package cn.com.x1001.classmap;

/**
 * @author keven1z
 * @date 2021/11/29
 */
public class ClassVertex {
    private String className;
    private int access;
    public ClassVertex(){}
    public ClassVertex(String className, int access) {
        this.className = className;
        this.access = access;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
