package cn.com.x1001.hook;

import org.objectweb.asm.ClassReader;

import javax.xml.bind.DatatypeConverter;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author keven1z
 * @date 2021/11/24
 */
public class BpCrack implements ClassFileTransformer {
    public static String toStringHex2(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte)(0xFF & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (toStringHex2(DatatypeConverter.printHexBinary(classfileBuffer)).contains("751a8be34c1a9ed9633d04be3ba075a7")) {
            ClassReader classReader = new ClassReader(classfileBuffer);
            System.out.println("crack className:"+classReader.getClassName());
            byte[] newBytes = new byte[classfileBuffer.length];
            System.arraycopy(classfileBuffer, 0, newBytes, 0, newBytes.length);
            String key_all = Arrays.toString(newBytes);
            Pattern p = Pattern.compile("90, .\\w+, -89, 0, 4, -65, 25, 5, 21, 6, 5, 100, 51, 28, -102, 0, 18, -89, 0, 4, -65, 16, 61, -89, 0, 4, -65, -96, 90, .\\w+, 21, 9, -68, 8, 58, 10, 3, 54, 11, 21, 11, 21, 9, -94, 0, 31, 25, 10, 28, -102, 0, 29, 21, 11, 25, 5, 21, 8, 21, 11, 96, 51, 84, -124, 11, 1, 28, -103, -1, -28, -89, 0, 4, -65, 21, 8, -68, 8, 58, 7, 3, 54, 11, 21, 11, 21, 8, -94, 0, 28, 25, 7, 21, 11, 25, 5, 21, 11, 51, 84, -124, 11, 1, 28, -102, 0, 14, 28, -103, -1, -25, -89, 0, 4, -65");
            Matcher m = p.matcher(key_all);
            boolean b = m.find();
            String[] key_sp = key_all.substring(0, m.start()).split(", ");
            Pattern p2 = Pattern.compile(", 21, 9, -68, 8, 58, 10, 3, 54, 11, 21, 11, 21, 9, -94, 0, 31, 25, 10, 28, -102, 0, 29, 21, 11, 25, 5, 21, 8, 21, 11, 96, 51, 84, -124, 11, 1, 28, -103, -1, -28, -89, 0, 4, -65, 21, 8, -68, 8, 58, 7, 3, 54, 11, 21, 11, 21, 8, -94, 0, 28, 25, 7, 21, 11, 25, 5, 21, 11, 51, 84, -124, 11, 1, 28, -102, 0, 14, 28, -103, -1, -25, -89, 0, 4, -65");
            Matcher m2 = p2.matcher(key_all);
            boolean b2 = m2.find();
            String[] key_sp2 = key_all.substring(0, m2.start()).split(", ");
            int[] patch = {
                    key_sp.length, 0, key_sp.length + 1, 3, key_sp.length + 28, 0, key_sp.length + 29, 3, key_sp.length + 115, 167,
                    key_sp.length + 116, 90, key_sp.length + 117, 39 + newBytes[key_sp2.length - 1] - 117 };
            for (int i = 0; i < patch.length; i += 2)
                newBytes[patch[i]] = (byte)patch[i + 1];
            return newBytes;
        }
        return null;
    }
}
