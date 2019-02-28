package cn.xjk.shiro.mapper;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/2/13 -  11:29
 **/
public interface UserMapper {
    /**
     * 查找所有用户信息
     * @return
     */
    List<User> selectAllUser();

    /**
     * 根据名称查询用户信息
     * @param userName user name
     * @return User
     */
    User selectUserByName(@Param(value = "userName") String userName);

    /**
     * 根据用户id查询该用户拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(long userId);

    /**
     * 根据用户名查询该用户拥有的权限
     * @param userName
     * @return
     */
    List<Permission> selectPermissionsByUserName(@Param(value = "userName")String userName);

    /**
     * 根据用户名查询该用户拥有的角色
     * @param userName
     * @return
     */
    List<Role> selectRoleByUserName(@Param(value = "userName")String userName);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int createUser(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(@Param(value = "id") long id);

    /**
     * 关联user和role
     * @param userId
     * @param roleId
     * @return
     */
    int correlationUserAndRoles(@Param(value = "userId") Long userId, @Param(value = "roleId") Long roleId);

    /**
     * 删除user对应的所有role
     * @param userId
     * @return
     */
    int deleteAllUserRolesByUserId(@Param(value = "userId") Long userId);
}
