package dHook;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

public interface IDHookExpression {
    /**
     * 调用静态方法
     *
     * @param type   静态方法类
     * @param method 静态方法
     */
    void invokeStatic(Class clazz, String methodName,String desc);

    /**
     * 调用方法
     *
     * @param owner      类名包含包路径
     * @param name       方法名
     * @param descriptor 方法描述
     */
    void invokeMethod( final int opcode, final String owner,
                      final String name,
                      final String descriptor);

    /**
     * 调用return方法，void方法
     */
    void invokeReturn();

    /**
     * 调用return方法，return String
     */
    void invokeReturn( String returnValue);

    /**
     * 调用return方法，return int
     */
    void invokeReturn( int returnValue);

    /**
     * 调用return方法，return String[]
     */
    void invokeReturn(String[] returnValue);

    /**
     * 调用return方法，return int[]
     */
    void invokeReturn(int[] returnValue);

    /**
     * 将返回值压入栈
     * @param opcode 操作符
     * @param desc hook方法描述
     */
    void pushReturnValue(int opcode, String desc);

    /**
     * 将当前hook对象压入栈中
     */
    void pushThis();

    /**
     * 将参数压入栈中
     */
    void pushParameter();

    /**
     * 将Object压入栈中
     */
    void pushObject(Object parameter);

    /**
     * 将int压入栈中
     */
    void pushInt(int parameter);


}
