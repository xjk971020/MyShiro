package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.result.DataResult;
import cn.xjk.shiro.result.MessageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/20 -  21:19
 **/
public interface PermissionService {
    /**
     * 获取所有权限数据
     * @return
     */
    DataResult<List<Permission>> selectAllPermissions();

    /**
     * 创建权限
     * @param permission
     * @return
     */
    MessageResult createPermission(Permission permission);

    /**
     * 删除权限
     * @param id
     * @return
     */
    MessageResult deletePermission(Long id);

    /**
     * 更新权限
     * @param permission
     * @return
     */
    MessageResult updatePermission(Permission permission);

    /**
     * 关联角色和权限
     * @param roleId
     * @param permissionId
     * @return
     */
    void correlationRoleAndPermission(Long roleId,Long permissionId);

    /**
     * 关联角色和权限
     * @param dataMap
     * @return
     */
    MessageResult correlationRoleAndPermissionMethod(Map<String,Object> dataMap);

    /**
     * 根据权限id查找权限拥有的角色
     * @param permissionId
     * @return
     */
    List<Role> selectRolesByPermissionId(@Param(value = "permissionId")Long permissionId);
}
