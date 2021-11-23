package cn.com.x1001.hook;
import cn.com.x1001.classmap.ClassInfo;
import cn.com.x1001.hook.HookAdviceAdapter;
import org.objectweb.asm.*;

import java.util.Set;
/**
 * @author  keven1z
 * @Date  2021/6/17
 * @Description 类访问对象
*/
public class CodeClassVisitor extends ClassVisitor {

    private ClassInfo classInfo;
    Set<String> methods;
    public CodeClassVisitor(ClassVisitor classVisitor, ClassInfo classInfo) {
        super(Opcodes.ASM5, classVisitor);
        this.classInfo = classInfo;
        methods = classInfo.getMethods();
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor localMethodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (methods.contains(name) && classInfo.getDescs(name).contains(desc))
            return new HookAdviceAdapter(Opcodes.ASM5, localMethodVisitor, access, name, desc,classInfo);
        return localMethodVisitor;
    }

}
