package cn.com.x1001.hook;


import cn.com.x1001.Agent;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.classmap.ClassVertex;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.classmap.HookGraph;
import cn.com.x1001.util.ClassUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.IOException;
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
        ClassReader classReader = new ClassReader(classfileBuffer);
        buildClassMap(classReader, className);

        if (!context.isHookClass(className)) {
            ClassVertex superClassVertex = context.getSuperClasses(className);
            if (superClassVertex != null) {
                context.addHooKClass(className, superClassVertex.getClassName());
            }
            return classfileBuffer;
        }

        context.setHooked(className);
        /* 如果为接口,添加所有实现该接口的第一个类为hook点*/
        if (ClassUtil.isInterface(classReader.getAccess())) {
            Set<ClassVertex> childClasses = context.getChildClasses(className);
            if (!childClasses.isEmpty()) {
                context.addHooKClass(className, childClasses);
            }
            return classfileBuffer;
        }

        if (context.containAction(className, HookConsts.ACTION_GET_DECOMPILER)) {
            ClassUtil.exportClass(classfileBuffer, className);
        }

        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        CodeClassVisitor codeClassVisitor = new CodeClassVisitor(classWriter, className);
        classReader.accept(codeClassVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    private void buildClassMap(ClassReader classReader, String className) {
//        long start = System.currentTimeMillis();
        HookGraph classMap = context.getClassMap();
        String[] interfaces = classReader.getInterfaces();
        String superName = classReader.getSuperName();
        HashSet<String> ancestors = buildAncestors(superName, interfaces);
        ClassVertex classVertex = classMap.buildVertex(className, classReader.getAccess());
        for (String ancestorsClassName : ancestors)  {
            ClassVertex ancestorsVertex = classMap.addNode(ancestorsClassName, -1);
            /*將頂點關係加入節點*/
            classMap.addEdge(classVertex, ancestorsVertex);
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
