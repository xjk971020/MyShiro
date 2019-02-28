package cn.xjk.shiro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xjk
 * @date 2019/2/20 -  14:46
 * 权限
 **/
@Setter
@Getter
@ToString
public class Permission {
    /**
     *权限id
     */
    private Long id ;
    
    /**
     *权限名称
     */
    private String permission ;
    
    /**
     *权限描述
     */
    private String description ;
    
    /**
     *父节点(即role中的id)
     */
    private Long rid ;
  
    /**
     *是否可用
     */
    private boolean available = Boolean.FALSE ;
    
}

