package cn.xjk.shiro.controller;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.TreeEntity;
import cn.xjk.shiro.result.DataResult;
import cn.xjk.shiro.result.MessageResult;
import cn.xjk.shiro.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xjk
 * @date 2019/2/19 -  16:24
 **/
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 查询所有角色
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("all")
    @RequiresPermissions(value = {"role:view"})
    public DataResult<List<Role>> selectAllRole() {
        return DataResult.success(roleService.selectAllRoles());
    }

    /**
     * 根据角色id删除角色
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}")
    public MessageResult deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResult createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    /**
     * 更新角色
     *
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public MessageResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 获得所有的role节点的数据，以TreeEntity类型进行封装
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/roletree")
    public List<TreeEntity> getRoleTree() {
        return roleService.getRoleTree();
    }

    /**
     * 返回 角色 树
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permissiontree/{id}", method = RequestMethod.GET)
    public List<TreeEntity> getPermssionTree(@PathVariable Long id) {
        return roleService.getPermssionTree(id);
    }

    @ResponseBody
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public List<Permission> getPermissionByRoleId(@PathVariable Long id) {
        return roleService.selectPermissionsByRoleId(id);
    }
}
