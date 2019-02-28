package cn.xjk.shiro.utils;

import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;

/**
 * @author xjk
 * @date 2019/2/25 -  23:31
 **/
@SuppressWarnings("unchecked")
public class ByteSourceUtils {

    public static ByteSource bytes(byte[] bytes) {
        return new MySimpleByteSource(bytes);
    }

    public static ByteSource bytes(String string) {
        return new MySimpleByteSource(string);
    }

    public static ByteSource bytes(char[] chars) {
        return new MySimpleByteSource(chars);
    }

    public static ByteSource bytes(InputStream inputStream) {
        return new MySimpleByteSource(inputStream);
    }

    public static ByteSource bytes(File file) {
        return new MySimpleByteSource(file);
    }

    public static ByteSource bytes(ByteSource byteSource) {
        return new MySimpleByteSource(byteSource);
    }

}

