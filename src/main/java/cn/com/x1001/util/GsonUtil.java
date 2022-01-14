package cn.com.x1001.util;

import com.google.gson.*;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.TRANSIENT;


/**
 * @author keven1z
 * @date 2021/12/22
 */
public class GsonUtil {
    private static Gson gson = new GsonBuilder().excludeFieldsWithModifiers(TRANSIENT).create();


    private GsonUtil() {
    }


    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将json String转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T toBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
    /**
     * 将json文件转成泛型bean
     *
     * @param reader
     * @param cls
     * @return
     */
    public static <T> List<T> toBean(Reader reader, Class<T> cls) {
        Gson gson = new Gson();
        JsonArray Jarray = JsonParser.parseReader(reader).getAsJsonArray();

        ArrayList<T> lcs = new ArrayList<T>();

        for(JsonElement obj : Jarray ){
            T cse = gson.fromJson(obj , cls);
            lcs.add(cse);
        }
        return lcs;
    }

}
