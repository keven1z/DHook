package cn.com.x1001.util;

import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isContainString(String str, HashSet<String> hashSet){
        if(str == null ||str.length() == 0) return false;
        if (hashSet == null || hashSet.size() == 0) return false;
        for(String element:hashSet){
            if(element.equals(str)) return true;
        }
        return false;
    }

    /**
     * @param str
     * @return 判断是否是数字
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    /**
     * @param str
     * @return 判断是否是布尔
     */
    public static boolean isBoolean(String str) {
        str = str.toLowerCase(Locale.ROOT);
        return str.equals("true") || str.equals("false");
    }

}
