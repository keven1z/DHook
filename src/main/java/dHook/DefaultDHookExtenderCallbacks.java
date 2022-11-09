package dHook;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/04/22
 */
public class DefaultDHookExtenderCallbacks implements IDHookExtenderCallbacks {
    private String extensionName;
    private String extensionDesc;
    private List<String> hooks;
    private String className;
    private String method;
    private String desc;
    private AdviceAdapter adviceAdapter;


    @Override
    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    @Override
    public void setExtensionDesc(String extensionDesc) {
        this.extensionDesc = extensionDesc;
    }

    @Override
    public void setExtensionHooks(List<String> hooks) {
        this.hooks = hooks;
    }

    public String getExtensionName() {
        return extensionName;
    }

    @Override
    public String getExtensionDesc() {
        return extensionDesc;
    }

    @Override
    public List<String> getExtensionHooks() {
        return hooks;
    }

    @Override
    public String getHookClassName() {
        return this.className;
    }

    @Override
    public String getHookMethod() {
        return this.method;
    }

    @Override
    public String getHookDesc() {
        return this.desc;
    }

    /**
     * @param className hook的className
     * @param method    hook中的method
     * @param desc      hook中方法描述符
     *                  设置hook信息
     */
    public void setHookInfo(String className, String method, String desc) {
        this.className = className;
        this.method = method;
        this.desc = desc;
    }

    @Override
    public void setAdviceAdapter(Object adviceAdapter) {
        this.adviceAdapter = (AdviceAdapter) adviceAdapter;
    }


    public void invokeStatic(Class clazz, String methodName, String desc) {
        Type type = Type.getType(clazz);
        Method method = new Method(methodName, desc);
        adviceAdapter.invokeStatic(type, method);
    }

    public void invokeMethod(final int opcode, String owner, String name, String descriptor) {
        adviceAdapter.visitMethodInsn(opcode, owner, name, descriptor, false);
    }

    public void invokeReturn() {
        adviceAdapter.visitInsn(adviceAdapter.RETURN);
    }

    public void invokeReturn(String returnValue) {
        adviceAdapter.visitLdcInsn(returnValue);
        adviceAdapter.visitInsn(adviceAdapter.ARETURN);
    }


    public void invokeReturn(int returnValue) {
        adviceAdapter.visitIntInsn(adviceAdapter.BIPUSH, returnValue);
        adviceAdapter.visitInsn(adviceAdapter.IRETURN);
    }

    public void invokeReturn(String[] returnValue) {
        adviceAdapter.visitTypeInsn(adviceAdapter.ANEWARRAY, "java/lang/String");
        for (int i = 0; i < returnValue.length; i++) {
            adviceAdapter.visitInsn(adviceAdapter.DUP);
            adviceAdapter.visitIntInsn(adviceAdapter.BIPUSH, i);
            adviceAdapter.visitLdcInsn(returnValue[i]);
            adviceAdapter.visitInsn(adviceAdapter.AASTORE);
        }
        adviceAdapter.visitInsn(adviceAdapter.ARETURN);
    }

    public void invokeReturn(int[] returnValue) {
        adviceAdapter.visitInsn(returnValue.length);//声明数组长度
        adviceAdapter.visitIntInsn(adviceAdapter.NEWARRAY, adviceAdapter.T_CHAR);
        for (int i = 0; i < returnValue.length; i++) {
            adviceAdapter.visitInsn(adviceAdapter.DUP);
            adviceAdapter.visitIntInsn(adviceAdapter.BIPUSH, i);
            adviceAdapter.visitLdcInsn(returnValue[i]);
            adviceAdapter.visitInsn(adviceAdapter.IASTORE);
        }
        adviceAdapter.visitInsn(adviceAdapter.IRETURN);
    }

    public void pushReturnValue(int opcode, String desc) {
        if (opcode == adviceAdapter.RETURN) {
            adviceAdapter.visitInsn(adviceAdapter.ACONST_NULL);
        } else if (opcode == adviceAdapter.ARETURN || opcode == adviceAdapter.ATHROW) {
            adviceAdapter.dup();
        } else {
            if (opcode == adviceAdapter.LRETURN || opcode == adviceAdapter.DRETURN) {
                adviceAdapter.dup2();
            } else {
                adviceAdapter.dup();
            }
            adviceAdapter.box(Type.getReturnType(desc));
        }
    }


    public void pushThis() {
        adviceAdapter.loadThis();
    }


    public void pushParameter() {
        adviceAdapter.loadArgArray();
    }


    public void pushObject(Object object) {
        adviceAdapter.visitLdcInsn(object);
    }


    public void pushInt(int parameter) {
        adviceAdapter.visitInsn(parameter);
    }
}
