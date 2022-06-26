# 编写你的插件
## API
1. IDHookExtender： DHook抽象类，包含onMethodExit，onMethodEnter,onVisitCode，修改方法不同调用时机的抽象方法
2. IDHookExtenderCallbacks：DHook抽象类帮助类，主要负责设置插件名，hook点等信息

## 编写插件
1. 下载[dHook.zip](./dHook.zip)
2. 解压dHook.zip，放入你的插件项目中。
3. 编写名为`DHookExtender`的类继承IDHookExtender,实现对应的抽象方法
4. 在registerExtenderCallbacks中通过callbacks对象设置hook点等信息

## 案例
以下为打印应用执行中的sql语句的插件：
```java
public class DHookExtender extends IDHookExtender {
  public void registerExtenderCallbacks(IDHookExtenderCallbacks idHookExtenderCallbacks) {
    ArrayList<String> list = new ArrayList<>();
    list.add("java.sql.Statement.executeQuery(Ljava/lang/String;)Ljava/sql/ResultSet;");
    idHookExtenderCallbacks.setExtensionHooks(list);
    idHookExtenderCallbacks.setExtensionName("sql语句打印插件");
    idHookExtenderCallbacks.setExtensionDesc("该工具可以打印所有应用执行的语句");
  }
  
  public void onMethodExit(AdviceAdapter adviceAdapter, int opcode) {
    if (opcode == 177) {
      adviceAdapter.visitInsn(1);
    } else if (opcode == 176 || opcode == 191) {
      adviceAdapter.dup();
    } else {
      if (opcode == 173 || opcode == 175) {
        adviceAdapter.dup2();
      } else {
        adviceAdapter.dup();
      } 
      adviceAdapter.box(Type.getReturnType(getDesc()));
    } 
    Type type = Type.getType(DHookExtender.class);
    Method method = new Method("doHook", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
    adviceAdapter.loadArgArray();
    adviceAdapter.visitLdcInsn(getClassName());
    adviceAdapter.visitLdcInsn(getMethod());
    adviceAdapter.visitLdcInsn(getDesc());
    adviceAdapter.invokeStatic(type, method);
  }
  
  public void onMethodEnter(AdviceAdapter adviceAdapter) {}
  
  public void onVisitCode(AdviceAdapter adviceAdapter) {}
  
  public static void doHook(Object returnValue, Object[] args, String className, String method, String desc) {
    StringBuilder sb = new StringBuilder();
    for (Object arg : args)
      sb.append(arg).append("\t"); 
    String argStr = sb.toString();
    System.out.println(className + "." + method + desc + "\n\t"+ argStr);
    if (returnValue != null)
      System.out.println("\t"+ returnValue);
  }
}
```
> jdk9+ 编写插件时,tomcat应用需要修改catalina.sh以下选项:
> 
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=java.base/jdk.internal.loader=ALL-UNNAMED"
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=jdk.zipfs/jdk.nio.zipfs=ALL-UNNAMED"