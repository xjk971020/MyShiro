package cn.xjk.shiro.mapper;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/2/17 -  11:57
 **/
public interface RoleMapper {
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
    Role selectRoleByRoleId(@Param(value = "roleId") Long id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int createRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteRole(@Param(value = "id") long id);

    /**
     * 编辑角色
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 根据角色id获取该角色拥有的权限
     * @param id
     * @return
     */
    List<Permission> selectPermissionsByRoleId(@Param(value = "roleId") Long id);
}
