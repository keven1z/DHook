package dHook;

/**
 * DHook插件扩展类
 *
 * @author keven1z
 * @date 2022/4/21
 */
public interface IDHookExtender {
    void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks);

    /**
     * 方法退出前操作
     *
     * @param adviceAdapter 操作方法类
     */
    void onMethodExit(int opcode);
    /**
     * 方法进入前操作
     *
     */
    void onMethodEnter();

    void onVisitCode();

}
