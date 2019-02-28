package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.mapper.PermissionMapper;
import cn.xjk.shiro.result.DataResult;
import cn.xjk.shiro.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/20 -  21:19
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public DataResult<List<Permission>> selectAllPermissions() {
        return DataResult.success(permissionMapper.selectAllPermissions());
    }

    @Override
    public MessageResult createPermission(Permission permission) {
        //如果前端传递的permission数据中包含role的树节点,则需要关联role
        if (permissionMapper.createPermission(permission) != 0) {
            if (permission.getRid() != null) {
                Long id = permissionMapper.selectPermissionByDescription(permission.getDescription());
                correlationRoleAndPermission(permission.getRid(),id);
            }
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    @Override
    public MessageResult deletePermission(Long id) {
        if (permissionMapper.deletePermission(id) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    @Override
    public MessageResult updatePermission(Permission permission) {
        if (permissionMapper.updatePermission(permission) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    @Override
    public void correlationRoleAndPermission(Long roleId, Long permissionId) {
        permissionMapper.correlationRoleAndPermission(roleId,permissionId);
    }


    /**
     * 编辑 权限-角色 处理方法
     * 思路:先删除所有与该权限关联的角色
     *      再将前端传递的roleId和permissionId进行绑定
     * @param dataMap
     * @return
     */
    @Override
    public MessageResult correlationRoleAndPermissionMethod(Map<String, Object> dataMap) {
        try {
            Long permissionId = Long.valueOf((String) dataMap.get("id"));//获取权限的id
            List ids = (List)dataMap.get("ids");  //获取所有的所需绑定的角色id
            List parents = (List)dataMap.get("parents"); //获取所有角色树节点的是否是父节点的信息

            permissionMapper.deleteRoleByPermissionId(permissionId);

            for (int i = 0; i < ids.size(); i++) {
                if (!(boolean)parents.get(i)) {
                    //不是父节点才进行关联，因为父节点是角色，子节点才是权限
                    permissionMapper.correlationRoleAndPermission(Long.valueOf(String.valueOf(ids.get(i))),permissionId);
                }
            }

            return MessageResult.success();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return MessageResult.error();
        }
    }

    @Override
    public List<Role> selectRolesByPermissionId(Long permissionId) {
        return permissionMapper.selectRolesByPermissionId(permissionId);
    }

}
