package cn.xjk.shiro.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xjk
 * @date 2019/2/18 -  11:57
 * 后端返回数的结构
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeEntity {
    /**
     *节点的id值
     */
    private Long id;

    /**
     *节点的名称
     */
    private String name ;

    /**
     *是否是父节点
     */
    private boolean isParent ;

    /**
     *父节点id
     */
    private Long pid ;

}
