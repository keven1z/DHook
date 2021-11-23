package cn.com.x1001.classmap;

import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author keven1z
 * @Date 2021/6/11
 * @Description hook类的信息
 */
public class ClassInfo {
    private String className;
    private int access = -1;
    private boolean isHooked = false;

    private Set<MethodDesc> methodDesc = new HashSet<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<MethodDesc> getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String method, String desc) {
        this.methodDesc.add(new MethodDesc(method, desc));
    }

    public void setMethodDesc(String method, String desc, String returnValue) {
        this.methodDesc.add(new MethodDesc(method, desc, returnValue));
    }

    public void setMethodDesc(Set<MethodDesc> methodDesc) {
        this.methodDesc.addAll(methodDesc);
    }

    public Set<String> getMethods() {
        HashSet<String> methods = new HashSet<>();
        for (MethodDesc md : methodDesc) {
            methods.add(md.getMethod());
        }
        return methods;
    }

    public Set<String> getDescs(String method) {
        HashSet<String> descs = new HashSet<>();
        for (MethodDesc md : methodDesc) {
            if (md.getMethod().equals(method)) {
                descs.add(md.getDesc());
            }
        }
        return descs;
    }
    public String getReturnValue(String method,String desc) {
        if (method == null || desc == null) return null;
        for (MethodDesc md : methodDesc) {
            if (md.getMethod().equals(method) && md.getDesc().equals(desc)) {
                return md.getReturnValue();
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(className, classInfo.className);
    }

    /**
     * @param className
     * @param method
     * @param desc
     * @return
     */
    public boolean exist(String className, String method, String desc) {
        if (className == null || method == null || desc == null) return false;
        if (!this.className.equals(className)) return false;
        for (MethodDesc md : methodDesc) {
            if (method.equals(md.getMethod()) && desc.equals(md.getDesc())) return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Objects.hash(className);
    }

    public boolean isHooked() {
        return isHooked;
    }

    public void setHooked(boolean hooked) {
        isHooked = hooked;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return className + "." + methodDesc;
    }

    public static class MethodDesc {
        private String method;
        private String desc;
        private String returnValue;

        public MethodDesc(String method, String desc) {
            this.method = method;
            this.desc = desc;
        }

        public MethodDesc(String method, String desc, String returnValue) {
            this.method = method;
            this.desc = desc;
            this.returnValue = returnValue;
        }

        public String getReturnValue() {
            return returnValue;
        }

        public void setReturnValue(String returnValue) {
            this.returnValue = returnValue;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "method='" + method + '\'' +
                    ", desc='" + desc;
        }

    }
}
