package cn.com.x1001;


import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.hook.HookMessage;
import cn.com.x1001.hook.HookTransformer;
import cn.com.x1001.watch.ClassFileWatch;
import cn.com.x1001.watch.HeartBeat;
import cn.com.x1001.watch.HookWatch;
import cn.com.x1001.watch.WatchManager;

import java.io.*;
import java.lang.instrument.Instrumentation;


public class Agent {
    //打印输出
    public static PrintStream out = System.out;
    public static InstrumentationContext context =new InstrumentationContext();

    public static void premain(String args, Instrumentation inst) {
        start(inst);
    }

    private static void start(Instrumentation inst) {
        banner();
        if(register()){
            System.out.println("[+] Agent register successfully,Server:"+ HookConsts.SERVER);
            WatchManager.startWatch(new HeartBeat(context.getAgentID()));
        }
        else {
            System.out.println("[-] Agent register failed,Server:"+ HookConsts.SERVER);
        }
        init();
        inst.addTransformer(new HookTransformer(), true);
        new HookWatch(inst).start();
    }

    private static void banner() {
        String banner = ",------.  ,--.  ,--. ,-----.  ,-----. ,--. ,--. \n" +
                "|  .-.  \\ |  '--'  |'  .-.  ''  .-.  '|  .'   / \n" +
                "|  |  \\  :|  .--.  ||  | |  ||  | |  ||  .   '  \n" +
                "|  '--'  /|  |  |  |'  '-'  ''  '-'  '|  |\\   \\ \n" +
                "`-------' `--'  `--' `-----'  `-----' `--' '--' ";
        System.out.println(banner);
    }

    private static void init() {
        context = new InstrumentationContext();
    }

    private static boolean register() {
        try {
            return HookMessage.register();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}