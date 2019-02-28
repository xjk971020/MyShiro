package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.TreeEntity;
import cn.xjk.shiro.result.MessageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/2/18 -  12:10
 **/
public interface RoleService {
    /**
     * 获取所有的角色信息
     * @return
     */
    List<Role> selectAllRoles();

    /**
     * 根据角色id获得角色
     * @param id
     * @return
     */
    Role selectRoleByRoleId( Long id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    MessageResult createRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    MessageResult deleteRole(long id);

    /**
     * 编辑角色
     * @param role
     * @return
     */
    MessageResult updateRole(Role role);

    /**
     * 根据角色id获取该角色拥有的权限
     * @param id
     * @return
     */
    List<Permission> selectPermissionsByRoleId(@Param(value = "roleId") Long id);

    /**
     * 获得所有的role节点的数据，以TreeEntity类型进行封装
     * @return
     */
    List<TreeEntity> getRoleTree();

    /**
     * 返回 角色-权限 树
     * @param id
     * @return
     */
    List<TreeEntity> getPermssionTree(Long id);
}
