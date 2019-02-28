package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.TreeEntity;
import cn.xjk.shiro.mapper.RoleMapper;
import cn.xjk.shiro.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjk
 * @date 2019/2/18 -  12:11
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectAllRoles() {
        return roleMapper.selectAllRoles();
    }

    @Override
    public Role selectRoleByRoleId(Long id) {
        return roleMapper.selectRoleByRoleId(id);
    }

    @Override
    public MessageResult createRole(Role role) {
        if (roleMapper.createRole(role) != 0) {
            return MessageResult.success();
        } else{
            return MessageResult.error();
        }
    }

    @Override
    public MessageResult deleteRole(long id) {
        if (roleMapper.deleteRole(id) != 0) {
            return MessageResult.success();
        } else{
            return MessageResult.error();
        }
    }

    @Override
    public MessageResult updateRole(Role role) {
        if (roleMapper.updateRole(role) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    @Override
    public List<Permission> selectPermissionsByRoleId(Long id) {
        return roleMapper.selectPermissionsByRoleId(id);
    }

    @Override
    public List<TreeEntity> getRoleTree() {
        List<Role> roleList = selectAllRoles();
        TreeEntity tree;
        List<TreeEntity> treeList = new ArrayList<>();

        for (Role role : roleList) {
            if (role.getPid() != null) {
                //所有的节点都是父节点，方便进行下一级的添加
                tree = new TreeEntity(role.getId(),role.getDescription(),true,Long.parseLong("0"));
                treeList.add(tree);
            }
        }
        return treeList;
    }

    @Override
    public List<TreeEntity> getPermssionTree(Long id) {
        List<TreeEntity> treeEntityList = new ArrayList<>();
        List<Permission> permissionList = selectPermissionsByRoleId(id);
        TreeEntity treeEntity;

        //根节点。即关于角色的信息
        Role role = selectRoleByRoleId(id);
        treeEntity = new TreeEntity(role.getId(),role.getDescription(),true,role.getPid());
        treeEntityList.add(treeEntity);

        //子节点，遍历所有属于该角色的权限并添加到树节点中
        for (Permission permission: permissionList) {
            if (permission.getRid() != null) {
                treeEntity = new TreeEntity(permission.getId(),permission.getDescription(),false,permission.getRid());
                treeEntityList.add(treeEntity);
            }
        }
        return treeEntityList;
    }
}
