package cn.com.x1001.hook;

public class HookConsts {

    /*
        csv文件配置信息
     */
    public static final String CSV_FILE_NAME  = "hookClass.csv";
    public static final String CSV_CLASS_NAME  = "className";
    public static final String CSV_METHOD  = "method";
    public static final String CSV_DESC  = "desc";
    public static final String CSV_RETURN  = "return";
    public static final String CSV_PARAMETER  = "parameter";


    public static final String FLAG_ACTION_GET_DECOMPILER = "+";
    public static final int ACTION_GET_DECOMPILER = 1;
    /*
        返回值的类型
     */
    public static final int RETURN_NONE = 0;
    public static final int RETURN_INT = 1;
    public static final int RETURN_BOOLEAN = 2;
    public static final int RETURN_STRING = 3;

    /*
        打印描述信息
     */
    public static final String FLAG_PRINT_ALL_METHOD_AND_DESC = "*";


    public static final String SERVER = "http://49.234.9.241";
    public static final String SERVER_REGISTER = SERVER+"/register";
    public static final String SERVER_HOOK = SERVER+"/hook";

}
