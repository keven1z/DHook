package cn.com.x1001;


import cn.com.x1001.hook.BpCrack;
import cn.com.x1001.hook.HookTransformer;
import cn.com.x1001.watch.ClassFileWatch;
import cn.com.x1001.watch.HookWatch;

import java.io.*;
import java.lang.instrument.Instrumentation;


public class Agent {
    //打印输出
    public static PrintStream out = System.out;
    public static InstrumentationContext context = new InstrumentationContext();
    public static long spendTime = 0;

    public static void premain(String args, Instrumentation inst){
        start(inst);
    }

    private static void start(Instrumentation inst) {
        out.println("********************************************************************");
        out.println("*                               Run Success                        *");
        out.println("********************************************************************");
        inst.addTransformer(new BpCrack());
        inst.addTransformer(new HookTransformer(), true);
        try {
            new ClassFileWatch().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new HookWatch(inst).start();
    }
}
