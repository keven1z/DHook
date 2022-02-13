package com.keven1z.utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;
/**
 * @author keven1z
 * @date 2022/01/17
 */
public class AsmVerify implements Opcodes {

    public static void check(byte[] code) {
        ClassReader cr = new ClassReader(code);
        check(cr);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void check(ClassReader cr) {
        ClassNode cn1 = new ClassNode();
        cr.accept(new CheckClassAdapter(cn1, false), ClassReader.SKIP_DEBUG | ClassReader.EXPAND_FRAMES);

        List<?> methods = cn1.methods;
        for (int i = 0; i < methods.size(); ++i) {
            MethodNode method = (MethodNode) methods.get(i);
            BasicVerifier verifier = new BasicVerifier();
            Analyzer a = new Analyzer(verifier);
            try {
                a.analyze(cn1.name, method);
            } catch (Exception ex) {
                if (true) {
                    ex.printStackTrace(System.err);
                    printAnalyzerResult(method, a, new PrintWriter(System.err));
                }
                throw new RuntimeException("Error verify method " + cr.getClassName() + "." + method.name + " " + method.desc, ex);
            }
        }
    }

    private static String getShortName(final String name) {
        int n = name.lastIndexOf('/');
        return n == -1 ? name : "o";
    }

    static class MethodPrinter extends Textifier {
        protected MethodPrinter(int api) {
            super(api);
        }

        public StringBuffer getBuf() {
            return buf;
        }
    }

    @SuppressWarnings("rawtypes")
    static void printAnalyzerResult(MethodNode method, Analyzer a, final PrintWriter pw) throws IllegalArgumentException {
        Frame[] frames = a.getFrames();
        MethodPrinter mp = new MethodPrinter(ASM5);
        String format = "%05d %-" + (method.maxStack + method.maxLocals + 6) + "s|%s";
        MethodVisitor mv = new TraceMethodVisitor(mp);
        for (int j = 0; j < method.instructions.size(); ++j) {
            method.instructions.get(j).accept(mv);

            StringBuffer s = new StringBuffer();
            Frame f = frames[j];
            if (f == null) {
                s.append('?');
            } else {
                for (int k = 0; k < f.getLocals(); ++k) {
                    s.append(getShortName(f.getLocal(k).toString()));
                }
                s.append(" : ");
                for (int k = 0; k < f.getStackSize(); ++k) {
                    s.append(getShortName(f.getStack(k).toString()));
                }
            }
            pw.printf(format, j, s, mp.getBuf()); // mv.text.get(j));
        }
        for (int j = 0; j < method.tryCatchBlocks.size(); ++j) {
            ((TryCatchBlockNode) method.tryCatchBlocks.get(j)).accept(mv);
            pw.print(" " + mp.getBuf());
        }
        pw.println();
        pw.flush();
    }
}