package cn.xjk.shiro.utils;

import cn.xjk.shiro.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author xjk
 * @date 2019/2/14 -  10:12
 **/
public class PasswordHelper {
    /**
     * 实例化RandomNumberGenerator对象，用于生成一个随机数
     */
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 散列算法名称
     */
    private static  String algorithName = "MD5";

    /**
     * 散列次数
     */
    private static int hashIterations = 1024;

    public static void encryptPassword(User user) {
        if (user.getPassword() != null) {
            user.setSalt(randomNumberGenerator.nextBytes().toHex());
            System.out.println("salt： " + user.getSalt());
        }
        String password = new SimpleHash(algorithName,user.getPassword(), ByteSource.Util.bytes(user.getCrenditalSalt()),hashIterations).toHex();
        user.setPassword(password);
        System.out.println("password: " + user.getPassword());
    }
}
