package cn.xjk.shiro.controller;

import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.TreeEntity;
import cn.xjk.shiro.entity.User;
import cn.xjk.shiro.result.DataResult;
import cn.xjk.shiro.result.MessageResult;
import cn.xjk.shiro.service.RoleService;
import cn.xjk.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/16 -  12:30
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/all")
    public DataResult<List<User>> selectAllUser() {
        return DataResult.success(userService.selectAllUser());
    }

    /**
     * 根据用户名id查询该用户拥有的角色
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public List<Role> selectRoleByUserId(@PathVariable long id) {
        return userService.selectRoleByUserId(id);
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public MessageResult updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MessageResult createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public MessageResult deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    /**
     * 构建一棵用户角色的ZTree树，以JSON格式返回给页面
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/roletree", method = RequestMethod.GET)
    public List<TreeEntity> getRoleTree() {
        List<TreeEntity> treeList = new ArrayList<>();
        List<Role> roleList = roleService.selectAllRoles();
        for (Role role : roleList) {
            if (role.getPid() != null) {
                if (role.getPid() == 0) {
                    treeList.add(new TreeEntity(role.getId(), role.getDescription(), true, (long) 0));
                } else {
                    treeList.add(new TreeEntity(role.getId(), role.getDescription(), false, role.getPid()));
                }
            }
        }
        return treeList;
    }

    /**
     * 关联user和role
     * @param dataMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/correlation")
    public MessageResult correlationUserAndRolesMethod(@RequestBody Map<String, Object> dataMap) {
        return userService.correlationUserAndRolesMethod(dataMap);
    }
}
