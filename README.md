# DHook ![1.0 (shields.io)](https://img.shields.io/badge/1.0-brightgreen.svg)
DHook是一个自定义动态hook的工具。通过`javaagent`+`ASM`技术对运行时的java应用进行字节码修改，并可以配置文件的方式来增加hook点，修改执行方法的返回值等。

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
className,method,desc,return
com/keven1z/Test,test1,(Ljava/lang/String;)Ljava/lang/String;,keven1z
com/keven1z/Test,test2,(I)I,111
com/keven1z/Test,test3,(Z)Z,true
```

该配置文件由className,method,desc,return组成，分别为类名，方法名，方法的描述符，以及返回值组成。

运行`java -javaagent:agent -jar test.jar`.运行结果如下：

![image-20211123135028361](https://typora-1253484559.cos.ap-shanghai.myqcloud.com/img/image-20211123135028361.png)
