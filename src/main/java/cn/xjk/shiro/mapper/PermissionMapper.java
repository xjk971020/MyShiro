package cn.xjk.shiro.mapper;


import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/2/20 -  21:06
 **/
public interface PermissionMapper {
    /**
     * 获取所有权限数据
     * @return
     */
    List<Permission> selectAllPermissions();

    /**
     * 根据 权限描述 查找 权限id
     * @return
     */
    Long selectPermissionByDescription(@Param(value = "description")String description);

    /**
     * 根据权限id查找权限拥有的角色
     * @param permissionId
     * @return
     */
    List<Role> selectRolesByPermissionId(@Param(value = "permissionId")Long permissionId);

    /**
     * 创建权限
     * @param permission
     * @return
     */
    int createPermission(Permission permission);

    /**
     * 删除权限
     * @param id
     * @return
     */
    int deletePermission(@Param(value = "id")Long id);

    /**
     * 更新权限
     * @param permission
     * @return
     */
    int updatePermission(Permission permission);

    /**
     * 关联角色和权限
     * @param roleId
     * @param permissionId
     * @return
     */
    int correlationRoleAndPermission(@Param(value = "roleId") Long roleId,@Param(value = "permissionId") Long permissionId);

    /**
     * 根据权限id删除与该权限关联的角色
     * @param id
     */
    void deleteRoleByPermissionId(@Param(value = "permissionId") Long id);
}
