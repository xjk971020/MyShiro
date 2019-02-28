package cn.xjk.shiro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xjk
 * @date 2019/2/14 -  10:16
 * 用户
 **/
@Getter
@Setter
@ToString
public class User {

    /**
     *用户ID
     */
    private Long id;
    
    /**
     *用户姓名
     */
    private String userName ;

    /**
     *用户密码
     */
    private String password ;

    /**
     *盐值
     */
    private String salt ;

    /**
     *用户角色
     */
    private String roleId ;

    /**
     *是否锁定
     */
    private boolean locked = Boolean.FALSE;

    public String getCrenditalSalt() {
        return userName + salt;
    }

}
