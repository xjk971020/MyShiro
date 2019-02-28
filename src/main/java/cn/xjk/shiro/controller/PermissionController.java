package cn.xjk.shiro.controller;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.result.DataResult;
import cn.xjk.shiro.result.MessageResult;
import cn.xjk.shiro.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/20 -  21:27
 **/
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/all")
    public DataResult<List<Permission>> getAllPermissions() {
        return permissionService.selectAllPermissions();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public MessageResult deletePermission(@PathVariable Long id) {
        return permissionService.deletePermission(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResult createPermission(@RequestBody Permission permission) {
        return permissionService.createPermission(permission);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public MessageResult updatePermission(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @RequestMapping(value = "/roles/{permissionId}",method = RequestMethod.GET)
    public List<Role> selectRolesByPermissionId(@PathVariable Long permissionId) {
        return permissionService.selectRolesByPermissionId(permissionId);
    }

    @RequestMapping(value = "/correlation", method = RequestMethod.POST)
    public MessageResult correlationRoleAndPermissionMethod(@RequestBody Map<String, Object> dataMap) {
        System.out.println(dataMap.get("id"));
        return permissionService.correlationRoleAndPermissionMethod(dataMap);
    }
}
