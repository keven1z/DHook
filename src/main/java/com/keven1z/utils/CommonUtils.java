package com.keven1z.utils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * @author keven1z
 * @date 2022/01/17
 */
public class CommonUtils {
    public static void inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        inputStreamToOutputStream(in, out, false);
    }
    public static void inputStreamToOutputStream(InputStream in, OutputStream out, boolean close) throws IOException {
        try {
            byte[] buf = new byte[4096];
            int len = -1;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            if(close) {
                in.close();
                out.close();
            }
        }
    }

    public static void copy(File srcFile, File destFile) throws FileNotFoundException, IOException {
        inputStreamToOutputStream(new FileInputStream(srcFile), new FileOutputStream(destFile), true);
    }
    public static void copy(InputStream srcFile, File destFile) throws FileNotFoundException, IOException {
        inputStreamToOutputStream(srcFile, new FileOutputStream(destFile), true);
    }
}