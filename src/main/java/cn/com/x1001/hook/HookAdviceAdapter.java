package cn.com.x1001.hook;

import cn.com.x1001.classmap.ClassInfo;
import cn.com.x1001.util.StringUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.util.Locale;

import static cn.com.x1001.hook.HookConsts.*;

public class HookAdviceAdapter extends AdviceAdapter {
    private ClassInfo classInfo;
    private String methodName;
    private String desc;
    private String returnValue;

    /**
     * Creates a new {@link AdviceAdapter}.
     *
     * @param api    the ASM API version implemented by this visitor. Must be one
     *               of {@link Opcodes#ASM4} or {@link Opcodes#ASM5}.
     * @param mv     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link Type Type}).
     */
    public HookAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc, ClassInfo classInfo) {
        super(api, mv, access, name, desc);
        this.classInfo = classInfo;
        this.methodName = name;
        this.desc = desc;
        this.returnValue = classInfo.getReturnValue(this.methodName, this.desc);
    }

    @Override
    public void visitCode() {
        /*
         * 取值-1~5采用iconst指令
         * 取值-128~127采用bipush指令
         * 取值-32768~32767采用sipush指令
         *　取值-2147483648~2147483647采用 ldc 指令。
         */
        int returnCode = parseReturnValue(this.returnValue);
//        System.out.println("hook 类型:" + returnCode + ",值:" + returnValue);
        switch (returnCode) {
            case RETURN_INT:
                super.visitLdcInsn(Integer.parseInt(returnValue));
                super.visitInsn(IRETURN);
                break;
            case RETURN_BOOLEAN:
                if (returnValue.toLowerCase(Locale.ROOT).equals("true")) super.visitInsn(ICONST_1);
                else super.visitInsn(ICONST_0);
                super.visitInsn(IRETURN);
                break;
            case RETURN_STRING:
                super.visitLdcInsn(returnValue);
                super.visitInsn(ARETURN);
            default:
                super.visitCode();
        }
    }

    @Override
    protected void onMethodEnter() {
        Type type = Type.getType(HookHandler.class);
        Method method = new Method("doHook", "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        loadArgArray();
        this.visitLdcInsn(classInfo.getClassName());
        this.visitLdcInsn(methodName);
        this.visitLdcInsn(desc);
        invokeStatic(type, method);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
    }

    private int parseReturnValue(String returnValue) {
        if (returnValue == null) return RETURN_NONE;
        else if (StringUtil.isInteger(returnValue)) return RETURN_INT;
        else if (StringUtil.isBoolean(returnValue)) return RETURN_BOOLEAN;
        else return RETURN_STRING;
    }
}
