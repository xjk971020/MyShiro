package cn.xjk.shiro.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author xjk
 * @date 2019/2/24 -  10:02
 * 异常工具类
 **/
public class ExceptionUtil {

    public static String getMessage(Exception e){
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(printWriter);
            stringWriter.flush();
            printWriter.flush();
        } finally {
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return stringWriter.toString();
    }

}
