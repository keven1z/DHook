package dHook;

import org.objectweb.asm.commons.AdviceAdapter;

/**
 * DHook插件扩展类
 * @author keven1z
 * @date 2022/4/21
 */
public abstract class IDHookExtender extends DataFillingDHookExtender{
    public abstract void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks);

    /**
     * 方法退出前操作
     * @param adviceAdapter 操作方法类
     */
    public abstract void onMethodExit(AdviceAdapter adviceAdapter,int opcode);
    /**
     * 方法进入前操作
     * @param adviceAdapter 操作方法类
     */
    public abstract void onMethodEnter(AdviceAdapter adviceAdapter);
    public abstract void onVisitCode(AdviceAdapter adviceAdapter);

}
