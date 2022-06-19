package dHook;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/04/22
 */
public interface IDHookExtenderCallbacks {
    /**
     * 设置扩展名称
     *
     * @param name 扩展名称
     */
    void setExtensionName(String name);

    /**
     * 设置扩展描述
     *
     * @param desc 扩展描述
     */
    void setExtensionDesc(String desc);

    /**
     * @param hooks 设置hook点
     */
    void setExtensionHooks(List<String> hooks);

    String getExtensionName();

    String getExtensionDesc();

    List<String> getExtensionHooks();

}
