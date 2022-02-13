package com.keven1z.utils;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/17
 */
public class JarUtil {
    /**
     * 修改agent中的@className的@fieldName
     * @param className 待更新field的类名
     * @param fieldName 待更新field的name
     * @param fieldValue 待更新field的value
     * @return 修改后agent的路径
     */
    public static byte[] updateField(String className, String fieldName, String fieldValue) throws Exception{
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("agent/dHook.jar");
        Resource resource = resources[0];
//        File file = resource.getFile();
        InputStream inputStream = resource.getInputStream();
        String tmpAgent = System.getProperty("java.io.tmpdir") + File.separator + "dHook.jar";
        File agent = new File(tmpAgent);
        CommonUtils.copy(inputStream,agent);

        List<ClassNode> classNodes = JarLoader.loadJar(agent);

        List<ClassNode> changeNodes = new ArrayList<>();
        for (ClassNode classNode : classNodes) {
            if (!className.equals(classNode.name)) {
                changeNodes.add(classNode);
                continue;
            }
            for (FieldNode field : classNode.fields) {
                if (fieldName.equals(field.name)) {
                    field.value = fieldValue;
                }
            }
            changeNodes.add(classNode);
        }
//        String inJarPath = file.getAbsolutePath();
//        String outJarPath = System.getProperty("java.io.tmpdir") + File.separator +"dHook.jar";
        return JarLoader.saveToJar(agent, changeNodes);
    }

    public static void main(String[] args) throws Exception {
        byte[] bytes = JarUtil.updateField("cn/com/x1001/hook/HookConsts", "REGISTER_ID", "id");
        System.out.println(bytes.length);
    }
}
