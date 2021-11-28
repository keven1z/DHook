package cn.com.x1001.hook;


import cn.com.x1001.Agent;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.classmap.ClassInfo;
import cn.com.x1001.classmap.HookGraph;
import cn.com.x1001.util.ClassUtil;
import cn.com.x1001.util.DecompilerUtil;
import com.strobel.decompiler.Decompiler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class HookTransformer implements ClassFileTransformer {
    InstrumentationContext context = Agent.context;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (null == className) {
            return classfileBuffer;
        }
//        System.out.println(className);
        ClassReader classReader = new ClassReader(classfileBuffer);
        if (!context.isHookClass(className)) {
            buildClassMap(classReader, className, null);
            ClassInfo hookClasses = context.getSuperHookClasses(className);
            if (hookClasses != null) {
                context.addHooKClass(className, hookClasses);
            }

            return classfileBuffer;
        }
        buildClassMap(classReader, className, context.getHookClass(className));
        context.setHooked(className);
        /* 如果为接口,添加所有实现该接口的第一个类为hook点*/
        if (ClassUtil.isInterface(classReader.getAccess())) {
            Set<ClassInfo> childHookClasses = context.getChildHookClasses(className);
            if (!childHookClasses.isEmpty()) {
                context.addHooKClass(childHookClasses, context.getHookClass(className));
            }
            return classfileBuffer;
        }

        HookGraph classMap = context.getClassMap();
        ClassInfo classInfo = classMap.getVertex(className);
        if (classInfo.getActions().contains(HookConsts.ACTION_GET_DECOMPILER)){
            ClassUtil.exportClass(classfileBuffer,className);
        }

        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES| ClassWriter.COMPUTE_MAXS);
        CodeClassVisitor codeClassVisitor = new CodeClassVisitor(classWriter, classMap.getVertex(className));
        classReader.accept(codeClassVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private void buildClassMap(ClassReader classReader, String className, ClassInfo hookClass) {
//        long start = System.currentTimeMillis();
        HookGraph classMap = context.getClassMap();
        String[] interfaces = classReader.getInterfaces();
        String superName = classReader.getSuperName();
        HashSet<String> ancestors = buildAncestors(superName, interfaces);
        if (hookClass == null) {
            hookClass = classMap.addNode(className, classReader.getAccess());
        } else {
            hookClass.setAccess(classReader.getAccess());
            classMap.addNode(hookClass);
        }
        for (String ancestorsClassName : ancestors) {
            ClassInfo ancestorsClassInfo = classMap.addNode(ancestorsClassName, -1);
            /*將頂點關係加入節點*/
            classMap.addEdge(hookClass, ancestorsClassInfo);
        }
//        long l = System.currentTimeMillis() - start;
//        if (l > 0) {
//            Agent.spendTime+=l;
//            System.out.println("spend time:"+Agent.spendTime);
//        }

    }

    /**
     * @param superName  当前类的父类
     * @param interfaces 当前类的接口类
     */
    private HashSet<String> buildAncestors(String superName, String[] interfaces) {

        HashSet<String> ancestors = new HashSet<>();
        if (interfaces.length > 0) {
            ancestors.addAll(Arrays.asList(interfaces));
        }
        if (superName != null && !superName.equalsIgnoreCase("java/lang/Object")) {
            ancestors.add(superName);
        }
        return ancestors;
    }

}
