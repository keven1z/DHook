package dHook;

import java.util.List;

/**
 * @author keven1z
 * @date 2022/04/22
 */
public class DefaultDHookExtenderCallbacks implements dHook.IDHookExtenderCallbacks {
    private String extensionName;
    private String desc;
    private List<String> hooks;
    @Override
    public void setExtensionName(String name) {
        this.extensionName = name;
    }

    @Override
    public void setExtensionDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setExtensionHooks(List<String> hooks) {
        this.hooks = hooks;
    }

    public String getExtensionName() {
        return extensionName;
    }

    @Override
    public String getExtensionDesc() {
        return desc;
    }

    @Override
    public List<String> getExtensionHooks() {
        return hooks;
    }
}
