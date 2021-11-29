# DHook ![1.0 (shields.io)](https://img.shields.io/badge/1.1-brightgreen.svg)
DHook是一个自定义动态hook的工具。通过`javaagent`+`ASM`技术对运行时的java应用进行字节码修改，并可以配置文件的方式来增加hook点，修改执行方法的返回值以及参数等。

## 兼容性
* java 8-11

## 快速开始
以test.jar 为例，其源码为：

```java
package com.keven1z;

/**
 * @author keven1z
 * @date 2021/11/23
 */
public class Test {
    public static String test1(String str){
        return str;
    }
    public static int test2(int i){
        return 1232132132;
    }
    public static boolean test3(boolean b){
        return b;
    }

    public static void main(String[] args) {
        String str = "origin";
        System.out.println("执行test1,原始值为"+str);
        System.out.println("现在的值为"+test1(str));
        int i = 999;
        System.out.println("执行test2,原始值为"+i);
        System.out.println("现在的值为"+test2(i));
        boolean b = false;
        System.out.println("执行test3,原始值为"+b);
        System.out.println("现在的值为"+test3(b));
    }
}

```
该类存在三个分别是string，boolean，int为参数和返回值的方法，通过配置`hookClass.csv`文件，更改对应的返回值。此文件内容如下：

```csv
className,method,desc,return,parameter
com/keven1z/Test,test1,(Ljava/lang/String;)Ljava/lang/String;,keven1z,
com/keven1z/Test,test2,(I)I,111,
com/keven1z/Test,test3,(Z)Z,true,
```
文件参数如下：

|   name    |                         description                          |
| :-------: | :----------------------------------------------------------: |
| className |      类的全路径，以`/`分割，示例：`com/keven1z/Test`，       |
|  method   |                    方法名，示例：`test1`                     |
|   desc    |   方法描述，示例：`(Ljava/lang/String;)Ljava/lang/String;`   |
|  return   |                        待修改的返回值                        |
| parameter | 待修改的参数，以`;`分割多个参数,以`-`分割位置和值，前者为位置，后者为值，静态方法位置以0为起始值，非静态方法以1为起始值，示例：0-aa;1-bb |



运行`java -javaagent:dHook.jar -jar test.jar`.运行结果如下：

![image-20211129204730815](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20211129204730815.png)

### 辅助参数

#### 反编译源代码

需在`className`前添加`+`,会在运行目录下生成一个文件，若该类可以被反编译，则生成java文件，否则生成class文件。

#### 打印类的所有方法

可以通过将method设置为`*`,则`Dhook`会打印该类的所有方法。

## 更新
### 1.0版本
* 支持hook接口，当填写的类为接口时，默认会hook所有实现的子类
* 支持更改hook类的返回类型为string，int，boolean的返回值
* 支持打印hook方法的所有参数值

### 1.1版本
* 增加通过`*`打印该类的所有方法
* 支持打印返回值
* 支持反编译代码
* 支持修改参数