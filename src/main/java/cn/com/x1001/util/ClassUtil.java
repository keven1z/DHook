package cn.com.x1001.util;

import cn.com.x1001.classmap.HookClass;
import org.apache.commons.csv.CSVRecord;
import org.objectweb.asm.Opcodes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;

import static cn.com.x1001.hook.HookConsts.*;
/**
 * 类的工具类
 * @author keven1z
 * @date 2021/11/22
 */
public class ClassUtil {
    /**
     * @param access
     * @return true 表明该类为接口类
     */
    public static boolean isInterface(int access) {
        return (access & Opcodes.ACC_INTERFACE) != 0;
    }

    /**
     * @param file hook文件路径类
     * @return 解析好的hook类的临时文件
     * @throws IOException
     */
    public static List<HookClass> fileParse(File file) throws IOException {
        Reader in = new FileReader(file);
        List<HookClass> hookClasses = GsonUtil.toBean(in, HookClass.class);

        return hookClasses;
    }

    /**
     * 构建hook类的临时类
     * @param record csv 记录类
     * @param hookTmps hook类的临时文件
     */
    private static void buildHookTmp(CSVRecord record, HashSet<HookTmp> hookTmps) {
        String className = record.get(CSV_CLASS_NAME);
        String method = record.get(CSV_METHOD);
        String desc = record.get(CSV_DESC);
        String returnValue = record.get(CSV_RETURN);
        String parameter = record.get(CSV_PARAMETER);
        if (className == null || method == null) return;
        HookTmp hookTmp = new HookTmp(className, method, desc,parameter);
        hookTmp.setReturnValue(returnValue);
        hookTmps.add(hookTmp);
    }

    public static void writeFiles(String fileName, byte[] data) throws Exception {
        File directory = new File(".");
        String canonicalPath = directory.getCanonicalPath();
        System.out.println(fileName+"导出文件路径："+canonicalPath);
        FileOutputStream fso = new FileOutputStream(canonicalPath + File.separator + fileName);
        fso.write(data);
        fso.close();
    }
    public static void exportClass(byte[] classfileBuffer,String className){
        String[] split = className.split("/");
        String realClassName = split[split.length-1];
        byte[] classfile = classfileBuffer;
        try {
            classfile = DecompilerUtil.getClassString(classfileBuffer, className).getBytes(StandardCharsets.UTF_8);
            realClassName = realClassName + ".java";
        } catch (Throwable e) {
            realClassName = realClassName + ".class";
        }

        try {
            writeFiles(realClassName,classfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
