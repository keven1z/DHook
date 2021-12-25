package cn.com.x1001.hook;

import cn.com.x1001.Agent;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.util.HookUtil;
import org.objectweb.asm.*;

import java.util.Set;

/**
 * @author keven1z
 * @Date 2021/6/17
 * @Description 类访问对象
 */
public class CodeClassVisitor extends ClassVisitor {
    private String className;
    private  Set<HookClass> hookClasses;
    Set<String> methods;

    public CodeClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM5, classVisitor);
        this.className = className;
        this.hookClasses = Agent.context.getHookClasses(className);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor localMethodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        /*
            如果method为*，则将所有方法打印出来   w
         */
        if (HookUtil.isContainMethod(this.hookClasses,HookConsts.FLAG_PRINT_ALL_METHOD_AND_DESC)) {
            Agent.out.println(this.className + "." + name + desc);
        }

        if (HookUtil.isContainMethodDesc(hookClasses,name,desc)){
            Agent.out.println("[+] Hook class:"+this.className + "." + name + desc);
            HookClass hookClass = Agent.context.getHookClass(hookClasses, name, desc);
            return new HookAdviceAdapter(Opcodes.ASM5, localMethodVisitor, access, name, desc, hookClass);
        }

        return localMethodVisitor;
    }

}
