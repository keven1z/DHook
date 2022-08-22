# 编写你的第一个插件
## 准备
1. 创建一个新的java project，并命名为**dHook**.
2. 下载[dHook.zip](./dHook.zip)
3. 解压dHook.zip，放入你的插件项目中.
4. 导入asm依赖
```xml
<dependency>
     <groupId>org.ow2.asm</groupId>
     <artifactId>asm-commons</artifactId>
     <version>7.0</version>
</dependency>
```
5. 如下编写名为`DHookExtender`的类实现IDHookExtender接口,实现对应的方法.
```java
package dHook;
public class DHookExtender implements IDHookExtender {

    public void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks) {
        // your extension code here
    }
}
```

## APIS

### Interface IDHookExtenderCallbacks

| Modifier and Type | Method and Description                                       |
| :---------------- | :----------------------------------------------------------- |
| `void`            | `setExtensionName(String name)` 设置扩展名称                 |
| `void`            | `setExtensionDesc(String desc)` 设置扩展描述                 |
| `void`            | `setExtensionHooks(List<String> hooks)` 设置hook点           |
| `String`          | `getHookClassName` 获取hook过程中的className                 |
| `String`          | `getHookMethod()` 获取hook过程中的method                     |
| `String`          | `getHookDesc()` 获取hook过程中的desc                         |
| `Set<Source>`     | `getSource()` 获取污点跟踪中的所有源对象                     |
| `void`            | `invokeStatic(AdviceAdapter adviceAdapter, Type type, Method method)`  hook时调用静态方法 |
| `void`            | `invokeMethod(AdviceAdapter adviceAdapter, final int opcode, final String owner,final String name,final String descriptor)` hook时调用方法 |
| `void`            | `invokeReturn(AdviceAdapter adviceAdapter)` hook方法时，提前调用return，适用于void方法 |
| `void`            | `invokeReturn(AdviceAdapter adviceAdapter, String returnValue)` hook方法时，提前调用return，适用于返回值为String方法 |
| `void`            | `invokeReturn(AdviceAdapter adviceAdapter, int returnValue)`  hook方法时，提前调用return，适用于返回值为int方法 |
| `void`            | `invokeReturn(AdviceAdapter adviceAdapter, String[] returnValue)` hook方法时，提前调用return，适用于返回值为String[]方法 |
| `void`            | `invokeReturn(AdviceAdapter adviceAdapter, int[] returnValue)` hook方法时，提前调用return，适用于返回值为int[]方法 |
| `void`            | `pushReturnValue(AdviceAdapter adviceAdapter, int opcode, String desc)` 将hook的方法的返回值押入栈中 |
| `void`            | `pushThis(AdviceAdapter adviceAdapter)` 将hook的类押入栈中   |
| `void`            | `pushParameter(AdviceAdapter adviceAdapter)` 将hook的方法的参数押入栈中 |
| `void`            | `pushObject(AdviceAdapter adviceAdapter, Object parameter)` 将指定的对象押入栈中 |
| `void`            | `pushInt(AdviceAdapter adviceAdapter, int parameter)` 将int押入栈中 |

### Interface IDHookExtenderCallbacks

| Modifier and Type | Method and Description                                       |
| :---------------- | :----------------------------------------------------------- |
| `void`            | `registerExtenderCallbacks(IDHookExtenderCallbacks callbacks)` 该方法将会在插件加载时调用,注册一个`IDHookExtenderCallbacks `接口，默认调用其接口的多个方法，来为开发者提供想要的操作。 |
| `void`            | `onMethodExit(AdviceAdapter adviceAdapter, int opcode)`  在方法退出（return前）时修改代码 |
| `void`            | `onMethodEnter(AdviceAdapter adviceAdapter)` 在方法进入时修改代码 |
| `void`            | `onVisitCode(AdviceAdapter adviceAdapter)` 在方法运行过程中修改代码 |

## 案例

以下为打印应用中部分用户输入的参数：
```java
package dHook;


import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;
import java.util.ArrayList;


public class DHookExtender implements IDHookExtender, ISource {
    private IDHookExtenderCallbacks callbacks; //将IDHookExtenderCallbacks对象设置为属性，供修改方法时使用

    public void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks) {
        ArrayList<String> list = new ArrayList<>();
        list.add("org/apache/catalina/connector/Request.getParameter(Ljava/lang/String;)Ljava/lang/String;");
        callbacks.setExtensionHooks(list); //设置hook点
        callbacks.setExtensionName("request");//设置插件名
        callbacks.setExtensionDesc("打印请求参数");//设置插件描述
        this.callbacks = callbacks; //绑定属性
    }

    public void onMethodExit(AdviceAdapter adviceAdapter, int opcode) {

        callbacks.pushReturnValue(adviceAdapter, opcode, callbacks.getHookDesc()); //将getParameter方法返回值押入栈中
        Type type = Type.getType(HookHandler.class);
        Method method = new Method("doHook", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        callbacks.pushParameter(adviceAdapter); //将getParameter方法参数押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookClassName()); //将当前hook的className押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookMethod());//将当前hook的method押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookDesc());//将当前hook的desc押入栈中
        callbacks.invokeStatic(adviceAdapter, type, method); //调用doHook静态方法，传入的参数为押入栈中的对象
    }

    public void onMethodEnter(AdviceAdapter adviceAdapter) {
    }

    @Override
    public void onVisitCode(AdviceAdapter adviceAdapter) {

    }
}
```
> jdk9+ 编写插件时,tomcat应用需要修改catalina.sh以下选项:
> 
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=java.base/jdk.internal.loader=ALL-UNNAMED"
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=jdk.zipfs/jdk.nio.zipfs=ALL-UNNAMED"

**多个插件中自定义类名不要相同，目前会出现错误**