package cn.com.x1001.util;

import cn.com.x1001.bean.HookTmp;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

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
    public static Set<HookTmp> fileParse(File file) throws IOException {
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
        HashSet<HookTmp> hookTmps = new HashSet<>();
        for (CSVRecord record : records) {
            buildHookTmp(record, hookTmps);
        }
        return hookTmps;
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
        if (className == null || method == null) return;
        HookTmp hookTmp = new HookTmp(className, method, desc);
        hookTmp.setReturnValue(returnValue);
        hookTmps.add(hookTmp);
    }

    public static void recordHookResult(){

    }
}
