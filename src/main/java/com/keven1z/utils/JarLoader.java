package com.keven1z.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * jar加载器
 *
 * @author noverguo
 */
public class JarLoader implements Opcodes {

    public static List<ClassNode> loadJar(File jarFile) throws IOException {
        JarFile jf = new JarFile(jarFile);
        List<ClassNode> nodes = new ArrayList<ClassNode>();
        Enumeration<? extends ZipEntry> entries = jf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().endsWith("class")) {
                ClassReader cr = new ClassReader(jf.getInputStream(entry));
                ClassNode cn = new ClassNode();
                cr.accept(cn, ClassReader.EXPAND_FRAMES);
//					cn.accept(new TraceClassVisitor(new PrintWriter(System.out)));
                nodes.add(cn);
            }
        }
        jf.close();
        return nodes;
    }

    public static byte[] saveToJar(File jarFile, Collection<ClassNode> changeNodes) throws IOException {
        if (changeNodes == null) {
            return new byte[0];
        }
        String tempFolder = System.getProperty("java.io.tmpdir");
//        File tmpJarFile = new File(tempFolder + ".tmp.jar");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//            CommonUtils.copy(jarFile, tmpJarFile);
        JarFile jf = new JarFile(jarFile);
        JarOutputStream jos = new JarOutputStream(byteArrayOutputStream);
        Enumeration<JarEntry> entries = jf.entries();
        Map<String, ClassNode> changeNames = new HashMap<>();
        for (ClassNode cn : changeNodes) {
            changeNames.put(cn.name + ".class", cn);
        }
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            InputStream in = null;
            String entryName = jarEntry.getName();

            if (changeNames.containsKey(entryName)) {
                jarEntry = new JarEntry(entryName);
                ClassNode cn = changeNames.get(entryName);
                changeNames.remove(entryName);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);
//						cn.accept(new TraceClassVisitor(new PrintWriter(Log.sOut)));
                byte[] code = cw.toByteArray();
                in = new ByteArrayInputStream(code);

                AsmVerify.check(code);
            } else {
                in = jf.getInputStream(jarEntry);
                if (entryName.endsWith("class")) {
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    CommonUtils.inputStreamToOutputStream(in, output);
                    output.flush();
                    byte[] code = output.toByteArray();
                    in = new ByteArrayInputStream(code);
                    AsmVerify.check(code);
                }
            }
            jos.putNextEntry(jarEntry);
            CommonUtils.inputStreamToOutputStream(in, jos);
            jos.closeEntry();
            in.close();
        }

        for (Entry<String, ClassNode> entry : changeNames.entrySet()) {
            JarEntry jarEntry = new JarEntry(entry.getKey());
            jos.putNextEntry(jarEntry);
            ClassNode cn = entry.getValue();
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(new TraceClassVisitor(new PrintWriter(System.out)));
            cn.accept(cw);
            byte[] code = cw.toByteArray();
            InputStream in = new ByteArrayInputStream(code);

            CommonUtils.inputStreamToOutputStream(in, jos);
            in.close();
            jos.closeEntry();
        }
        byteArrayOutputStream.toByteArray();
        jos.close();
        jf.close();
        return byteArrayOutputStream.toByteArray();
    }
}