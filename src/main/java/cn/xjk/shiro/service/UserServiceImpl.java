package cn.xjk.shiro.service;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.User;
import cn.xjk.shiro.mapper.UserMapper;
import cn.xjk.shiro.result.MessageResult;
import cn.xjk.shiro.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/2/13 -  11:57
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    /**
     * 查询所有的用户信息
     * @return
     */

    @Override
    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    /**
     * 根据名称查询用户信息
     */
    @Override
    public User selectUserByName(String userName) {
            return userMapper.selectUserByName(userName);
    }

    /**
     * 根据用户名id查询该用户拥有的角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> selectRoleByUserId(long userId) {
        return userMapper.selectRoleByUserId(userId);
    }

    @Override
    public List<Role> selectRoleByUserName(String userName) {
        return userMapper.selectRoleByUserName(userName);
    }

    @Override
    public List<Permission> selectPermissionsByUserName(String userName) {
        return userMapper.selectPermissionsByUserName(userName);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public MessageResult updateUser(User user) {
        if (user.getPassword() != null) {
            PasswordHelper.encryptPassword(user);
        }
        if (userMapper.updateUser(user) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public MessageResult createUser(User user) {
        PasswordHelper.encryptPassword(user);
        if (userMapper.createUser(user) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Override
    public MessageResult deleteUser(long id) {
        if (userMapper.deleteUser(id) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    public MessageResult correlationUserAndRoles(Long userId, Long roleId) {
        if (userMapper.correlationUserAndRoles(userId,roleId) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    @Override
    public MessageResult deleteAllUserRolesByUserId(Long userId) {
        if (userMapper.deleteAllUserRolesByUserId(userId) != 0) {
            return MessageResult.success();
        } else {
            return MessageResult.error();
        }
    }

    /**
     * 关联user和role
     * 思路:先删去所有与user关联的role,再通过从前端获得的roleId进行绑定
     *      绑定的role不能包括父节点，注意判断过滤
     *      如果是只有一个父节点的role,则只能绑定一个
     * @param dataMap
     * @return
     */
    public MessageResult correlationUserAndRolesMethod(Map<String, Object> dataMap) {
        Long userId = Long.valueOf(String.valueOf(dataMap.get("id"))); //当前用户的id
        List ids = (ArrayList) dataMap.get("ids"); //获得所要绑定的角色的id
        List parents = (ArrayList) dataMap.get("parents");//当前用户是否是父节点的判断的集合
        List names = (ArrayList) dataMap.get("names");//获得角色的命名

        deleteAllUserRolesByUserId(userId);//删除该用户的所有角色

        String role_id = ""; //初始化user表中的role_id列

        if (ids.size() == 1) {
            correlationUserAndRoles(userId, Long.valueOf(String.valueOf(ids.get(0))));
            role_id += "[" + names.get(0)  + "]";
        } else {
            for (int i = 0; i < ids.size(); i++) {
                //只关联角色树中的子节点
                if (!(boolean) parents.get(i)) {
                    correlationUserAndRoles(userId, Long.valueOf(String.valueOf(ids.get(i))));
                    role_id += "[" + names.get(i)  + "]";
                }
            }
        }

        User user = new User();
        user.setId(userId);
        user.setRoleId(role_id);

        updateUser(user);

        return MessageResult.success();
    }
}
