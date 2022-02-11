package cn.com.x1001.util;

import cn.com.x1001.bean.FieldEntity;
import cn.com.x1001.bean.MethodActionEntity;
import cn.com.x1001.bean.MethodEntity;
import cn.com.x1001.classmap.HookClass;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author keven1z
 * @date 2021/11/29
 */
public class HookUtil {
    public static boolean isContainMethod(Set<HookClass> hookClasses, String method) {
        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            if(m.equals(method)) return true;
        }
        return false;
    }

    /**
     * 判断hook点是否包含指定@method和@desc
     */
    public static boolean isContainMethodDesc(Set<HookClass> hookClasses, String method,String desc) {
        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            String d = hookClass.getDesc();
            if(m.equals(method) && d.equals(desc)) return true;
        }
        return false;
    }

    public static void executeAction(AdviceAdapter adviceAdapter, String className,MethodActionEntity methodActionEntity) throws ClassNotFoundException {
        List<MethodEntity> methods = methodActionEntity.getMethods();
        List<FieldEntity> fields = methodActionEntity.getFields();
        //按前后排序
        methods.sort(new MethodEntity());
        for (MethodEntity method:methods){
            adviceAdapter.visitMethodInsn(adviceAdapter.INVOKESTATIC, method.getClassName(), method.getMethodName(), method.getDesc(), false);
        }
        //修改属性值
        for (FieldEntity field:fields){
            String value = field.getValue();
            visitField(adviceAdapter,className,field.getName(),value);
        }

    }

    private static List<MethodEntity> sort(List<MethodEntity> methodEntity){
        List<MethodEntity> methodEntities = new ArrayList<>();
//        for ()
        return null;
    }
    public static void visitField(AdviceAdapter adviceAdapter,String className,String name,String value){
        if (StringUtil.isInteger(value)){
            adviceAdapter.visitVarInsn(adviceAdapter.ALOAD, 0);
            adviceAdapter.visitInsn(Integer.parseInt(value));
            adviceAdapter.visitFieldInsn(adviceAdapter.PUTFIELD, className, name, "I");
        }
        else if(StringUtil.isBoolean(value)){
            adviceAdapter.visitVarInsn(adviceAdapter.ALOAD, 0);
            if (value.toLowerCase(Locale.ROOT).equals("true")) adviceAdapter.visitInsn(adviceAdapter.ICONST_1);
            else adviceAdapter.visitInsn(adviceAdapter.ICONST_0);
            adviceAdapter.visitFieldInsn(adviceAdapter.PUTFIELD, className, name, "I");
        }
        else{
            adviceAdapter.visitVarInsn(adviceAdapter.ALOAD, 0);
            adviceAdapter.visitLdcInsn(value);
            adviceAdapter.visitFieldInsn(adviceAdapter.PUTFIELD, className, name, "Ljava/lang/String;");
        }
    }
}
