package dHook;

import org.objectweb.asm.commons.AdviceAdapter;

/**
 * DHook插件扩展类
 * @author keven1z
 * @date 2022/4/21
 */
public interface IDHookExtender {
    public void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks);

    /**
     * 方法退出前操作
     * @param adviceAdapter 操作方法类
     */
    public void onMethodExit(AdviceAdapter adviceAdapter);
    /**
     * 方法进入前操作
     * @param adviceAdapter 操作方法类
     */
    public void onMethodEnter(AdviceAdapter adviceAdapter);
}
