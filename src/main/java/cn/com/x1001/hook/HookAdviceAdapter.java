package cn.com.x1001.hook;

import cn.com.x1001.bean.MethodActionEntity;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.util.HookUtil;
import cn.com.x1001.util.StringUtil;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.util.*;

import static cn.com.x1001.hook.HookConsts.*;

public class HookAdviceAdapter extends AdviceAdapter {
    private HookClass hookClass;
    private String methodName;
    private String desc;
    private String returnValue;

    /**
     * Creates a new {@link AdviceAdapter}.
     * @param api    the ASM API version implemented by this visitor. Must be one
     *               of {@link Opcodes#ASM4} or {@link Opcodes#ASM5}.
     * @param super     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link Type Type}).
     * @param hookClass
     */
    public HookAdviceAdapter(int api, MethodVisitor mv, int access, String name, String desc, HookClass hookClass) {
        super(api, mv, access, name, desc);
        this.hookClass = hookClass;
        this.methodName = name;
        this.desc = desc;
        this.returnValue = hookClass.getReturnValue();
    }



    @Override
    public void visitCode() {
//        暂未实现
//        modifyEnterParameter();
        modifyReturnValue();
    }

    @Override
    protected void onMethodEnter() {
        String className = this.hookClass.getClassName();
        List<MethodActionEntity> methodActions = hookClass.getOnMethodAction();
        for (MethodActionEntity methodActionEntity:methodActions){
            if (methodActionEntity.getType() == ACTION_ON_METHOD_ENTER){
                try {
                    HookUtil.executeAction(this,className,methodActionEntity);
                } catch (ClassNotFoundException e) {
                    //TODO 暂未处理
                    System.out.println(e.getMessage());
                }

            }
        }
        /** 破解cs
        super.visitVarInsn(ALOAD, 0);
        super.visitInsn(ICONST_0);
        super.visitFieldInsn(PUTFIELD, className, "watermark", "I");

        super.visitVarInsn(ALOAD, 0);
        super.visitLdcInsn("");
        super.visitFieldInsn(PUTFIELD, className, "validto", "Ljava/lang/String;");

        super.visitVarInsn(ALOAD, 0);
        super.visitInsn(ACONST_NULL);
        super.visitFieldInsn(PUTFIELD, className, "error", "Ljava/lang/String;");

        super.visitVarInsn(ALOAD, 0);
        super.visitInsn(ICONST_0);
        super.visitFieldInsn(PUTFIELD, className, "valid", "Z");

        super.visitVarInsn(ALOAD, 0);
        super.visitLdcInsn("forever");
        super.visitFieldInsn(PUTFIELD, className, "validto", "Ljava/lang/String;");


        super.visitVarInsn(ALOAD, 0);
        super.visitInsn(ICONST_1);
        super.visitFieldInsn(PUTFIELD, className, "valid", "Z");

        super.visitVarInsn(ALOAD, 0);
        super.visitInsn(ICONST_1);
        super.visitFieldInsn(PUTFIELD, className, "watermark", "I");

        super.visitIntInsn(BIPUSH, 16);
        super.visitIntInsn(NEWARRAY, T_BYTE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_0);
        super.visitIntInsn(BIPUSH, 94);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_1);
        super.visitIntInsn(BIPUSH, -104);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_2);
        super.visitIntInsn(BIPUSH, 25);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_3);
        super.visitIntInsn(BIPUSH, 74);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_4);
        super.visitInsn(ICONST_1);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitInsn(ICONST_5);
        super.visitIntInsn(BIPUSH, -58);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 6);
        super.visitIntInsn(BIPUSH, -76);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 7);
        super.visitIntInsn(BIPUSH, -113);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 8);
        super.visitIntInsn(BIPUSH, -91);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 9);
        super.visitIntInsn(BIPUSH, -126);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 10);
        super.visitIntInsn(BIPUSH, -90);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 11);
        super.visitIntInsn(BIPUSH, -87);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 12);
        super.visitIntInsn(BIPUSH, -4);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 13);
        super.visitIntInsn(BIPUSH, -69);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 14);
        super.visitIntInsn(BIPUSH, -110);
        super.visitInsn(BASTORE);
        super.visitInsn(DUP);
        super.visitIntInsn(BIPUSH, 15);
        super.visitIntInsn(BIPUSH, -42);
        super.visitInsn(BASTORE);
        super.visitVarInsn(ASTORE, 1);
        super.visitVarInsn(ALOAD, 1);
        super.visitMethodInsn(INVOKESTATIC, "common/SleevedResource", "Setup", "([B)V", false);
        super.visitInsn(RETURN);
         **/
    }

    @Override
    protected void onMethodExit(int opcode) {
        String className = this.hookClass.getClassName();
        List<MethodActionEntity> methodActions = hookClass.getOnMethodAction();
        for (MethodActionEntity methodActionEntity:methodActions){
            if (methodActionEntity.getType() == ACTION_ON_METHOD_EXIT){
                try {
                    HookUtil.executeAction(this,className,methodActionEntity);
                } catch (ClassNotFoundException e) {
                    //TODO 暂未处理
                }

            }
        }
        if (opcode == RETURN) {
            visitInsn(ACONST_NULL);
        } else if (opcode == ARETURN || opcode == ATHROW) {
            dup();
        } else {
            if (opcode == LRETURN || opcode == DRETURN) {
                dup2();
            } else {
                dup();
            }
            box(Type.getReturnType(this.methodDesc));
        }
        Type type = Type.getType(HookHandler.class);
        Method method = new Method("doHook", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        loadArgArray();
        this.visitLdcInsn(hookClass.getClassName());
        this.visitLdcInsn(methodName);
        this.visitLdcInsn(desc);
        invokeStatic(type, method);
    }

    private int parseReturnValue(String returnValue) {
        if (returnValue == null || returnValue.equals("")) return RETURN_NONE;
        else if (StringUtil.isInteger(returnValue)) return RETURN_INT;
        else if (StringUtil.isBoolean(returnValue)) return RETURN_BOOLEAN;
        else return RETURN_STRING;
    }


    /**
     * 修改入参
     */
    private void modifyEnterParameter(){
        HashMap<Integer, String> parameters = this.hookClass.getParameters();
        if (parameters == null) return;
        for (Map.Entry<Integer,String> entries: parameters.entrySet()){
            Integer pos = entries.getKey();
            String value = entries.getValue();
//            System.out.println(pos+" "+ value);
            if (StringUtil.isInteger(value)){
                super.visitLdcInsn(Integer.parseInt(value));
                super.visitVarInsn(ISTORE, pos);
            }
            else if(StringUtil.isBoolean(value)){
                if (returnValue.toLowerCase(Locale.ROOT).equals("true")) super.visitInsn(ICONST_1);
                else super.visitInsn(ICONST_0);
                super.visitVarInsn(ISTORE, pos);
            }
            else{
                super.visitLdcInsn(value);
                super.visitVarInsn(ASTORE, pos);
            }

        }
    }

    /**
     * 修改返回值
     *   取值-1~5采用iconst指令
     *   取值-128~127采用bipush指令
     *   取值-32768~32767采用sipush指令
     *   取值-2147483648~2147483647采用 ldc 指令。
     *
     */
    private void modifyReturnValue(){
        if (this.returnValue == null) return;
        int returnCode = parseReturnValue(this.returnValue);
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
                break;
            default:
                super.visitCode();
        }
    }


}
