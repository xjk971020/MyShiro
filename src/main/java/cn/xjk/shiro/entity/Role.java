package cn.xjk.shiro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xjk
 * @date 2019/2/16 -  15:01
 * 角色
 **/
@Getter
@Setter
@ToString
public class Role {
    /**
     *角色id
     */
    private Long id ;
    
    /**
     *role
     */
    private String role ;

    /**
     *角色描述
     */
    private String description ;
    
    /**
     *父id
     */
    private Long pid;
    
    /**
     *是否可用
     */
    private boolean available ;
    
}
