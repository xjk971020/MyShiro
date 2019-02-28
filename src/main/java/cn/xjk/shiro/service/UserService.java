package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.User;
import cn.xjk.shiro.result.MessageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/13 -  11:56
 **/
public interface UserService {
    /**
     * 查询所有的用户信息
     * @return
     */
    List<User> selectAllUser();

    /**
     * 根据名称查询用户信息
     * @param userName 用户姓名
     * @return User 返回用户信息
     */
    User selectUserByName(String userName);

    /**
     * 根据用户名id查询该用户拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(long userId);

    /**
     * 根据用户名查询该用户拥有的角色
     * @param userName
     * @return
     */
    List<Role> selectRoleByUserName(String userName);

    /**
     * 根据用户id查询该用户拥有的权限
     * @param userName
     * @return
     */
    List<Permission> selectPermissionsByUserName(String userName);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    MessageResult updateUser(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    MessageResult createUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    MessageResult deleteUser(long id);

    /**
     * 关联user和role
     * @param userId
     * @param roleId
     * @return
     */
    MessageResult correlationUserAndRoles(Long userId, Long roleId);

    /**
     * 删除user对应的所有role
     * @param userId
     * @return
     */
    MessageResult deleteAllUserRolesByUserId(Long userId);

    /**
     * 关联user和role
     * @param dataMap
     * @return
     */
    public MessageResult correlationUserAndRolesMethod(Map<String, Object> dataMap);
}
