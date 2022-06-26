# DHook ![2.3 (shields.io)](https://img.shields.io/badge/2.3-brightgreen.svg)
DHook是一个交互式自定义动态hook的工具。通过`javaagent`+`ASM`技术对运行时的java应用进行字节码修改，并可以配置文件的方式来增加hook点，修改执行方法的返回值以及参数等。

## 环境
* jdk 8-11

## 快速开始

### 启动DHookServer
1. `git clone https://github.com/keven1z/DHook.git`，或者下载release包
2. 运行`mvn clean package`
3. 运行`java -jar DHookServer-[release-version].jar`

启动界面如下：
```
,------.  ,--.  ,--. ,-----.  ,-----. ,--. ,--.
|  .-.  \ |  '--'  |'  .-.  ''  .-.  '|  .'   /
|  |  \  :|  .--.  ||  | |  ||  | |  ||  .   '
|  '--'  /|  |  |  |'  '-'  ''  '-'  '|  |\   \
`-------' `--'  `--' `-----'  `-----' `--' '--'
 :: DHook ::2.3        springboot: (v2.6.2)

2022-02-13 15:17:03.856  INFO 12496 --- [           main] com.keven1z.DHookServerApplication       : Starting DHookServerApplication v2.0 using Java 1.8.0_171 on zii with PID 12496 
2022-02-13 15:17:03.856  INFO 12496 --- [           main] com.keven1z.DHookServerApplication       : No active profile set, falling back to default profiles: default
2022-02-13 15:17:04.949  INFO 12496 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8081 (http)
2022-02-13 15:17:04.965  INFO 12496 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-02-13 15:17:04.965  INFO 12496 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-02-13 15:17:05.012  INFO 12496 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-02-13 15:17:05.012  INFO 12496 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1109 ms
2022-02-13 15:17:05.168  INFO 12496 --- [           main] c.a.d.s.b.a.DruidDataSourceAutoConfigure : Init DruidDataSource
2022-02-13 15:17:05.356 ERROR 12496 --- [           main] com.alibaba.druid.pool.DruidDataSource   : testWhileIdle is true, validationQuery not set
2022-02-13 15:17:05.356  INFO 12496 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
2022-02-13 15:17:05.669  INFO 12496 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
2022-02-13 15:17:05.856  INFO 12496 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http) with context path ''
2022-02-13 15:17:05.856  INFO 12496 --- [           main] com.keven1z.DHookServerApplication       : Started DHookServerApplication in 2.37 seconds (JVM running for 2.748)
2022-02-13 15:17:06.044  INFO 12496 --- [           main] com.keven1z.NettyServer                  : Netty started on port(s): 7070
```
> server 访问端口：8081
> server心跳监听端口: 7070

### 创建应用
访问localhost:8081,界面如下：

![image-20220213152610239](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20220213152612268.png)

点击新增，新增应用（此应用与即将hook的服务器绑定），填写应用名称即可。

### 以agent的形式启动应用

点击**获取agent**，自动下载对应应用的agent，将`-javaagent:[path]/dHook.jar`加入待hook的java应用中启动。如以下Tomcat应用，在`catalina.bat`中加入`set CATALINA_OPTS="-javaagent:../dHook.jar"`，启动Tomcat。

![image-20220213202742214](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20220213202742214.png)

agent启动成功会看到如上标志，并且可以在页面看到状态变为1.

### 添加Hook点

点击编辑Hook。进入以下页面

![image-20220213204922229](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20220213204922229.png)

点击新增，新增Hook点，界面如下：

![image-20220213205025411](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20220213205025411.png)

参数解释如下：

|   选项值   |                             解释                             |
| :--------: | :----------------------------------------------------------: |
| className  |      类的全路径，以`/`分割，示例：`com/keven1z/Test`，       |
|   method   |                    方法名，示例：`test1`                     |
|    desc    |   方法描述，示例：`(Ljava/lang/String;)Ljava/lang/String;`   |
| 修改返回值 |                        待修改的返回值                        |
| 方法执行前 | 定义所Hook的方法执行前操作，主要为两个操作：1. 静态方法调用   2. 修改当前类中属性值 |
| 方法执行后 | 定义所Hook的方法执行后操作，主要为两个操作：1. 静态方法调用   2. 修改当前类中属性值 |



### classMap

agent所捕获的Hook的所有类名

### 功能
#### 导出配置
将会导出hook点的信息的json文件

#### 导出agent
将会导出包含hook点的agent，该agent不与服务端绑定，去除了多余的api调用，仅作用hook点的修改，体积很小。

### 插件
> 插件编写见[plugin](./plugin.md)

插件与agent id绑定，进入对应的agent的导入插件，插件放在plugins文件夹中。

### 案例

以破解cs4.4为例，我们已知破解cs 需要修改`common/Authorization`中的以下参数：

* validto 有效期
* valid 有效性

以及调用`common/SleevedResource.Setup`传入密钥即可。

我们在方法执行前加入对应的参数和对应破解的值，并执行`common/SleevedResource.Setup`静态方法，最后加入`return`，使`<init>`方法执行返回。

<!--静态方法中classname填写为return，默认将该方法返回，若返回不为空，则将返回值填入参数即可正常返回-->

![image-20220223105550651](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20220223105550651.png)



## 更新
### 2.3版本 2022/6/26

* 增加插件功能

### 2.2版本

* 增加导出仅包含hook点信息的agent

### 2.1版本

* 可以增加方法执行前后静态方法执行参数
* 增加方法执行前后，直接return

### 2.0版本

* 增加交互式的hook操作
* 增加方法执行前后的修改

### 1.1版本

* 增加通过`*`打印该类的所有方法
* 支持打印返回值
* 支持反编译代码
* 支持修改参数

### 1.0版本
* 支持hook接口，当填写的类为接口时，默认会hook所有实现的子类
* 支持更改hook类的返回类型为string，int，boolean的返回值
* 支持打印hook方法的所有参数值







