package cn.xjk.shiro.realm;

import cn.xjk.shiro.entity.Permission;
import cn.xjk.shiro.entity.Role;
import cn.xjk.shiro.entity.User;
import cn.xjk.shiro.service.UserService;
import cn.xjk.shiro.utils.ByteSourceUtils;
import cn.xjk.shiro.utils.PasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xjk
 * @date 2019/2/12 -  15:19
 **/
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     *授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获得用户标识
        String userName = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        List<Role> roleList = userService.selectRoleByUserName(userName);
        for (Role role : roleList) {
            roles.add(role.getRole());
        }
        //此处的setRole()和setStringPermissions()都需要Set<String>作为参数
        authorizationInfo.setRoles(roles);

        Set<String> permissions = new HashSet<>();
        List<Permission> permissionList = userService.selectPermissionsByUserName(userName);
        for(Permission permission : permissionList) {
            permissions.add(permission.getPermission());
        }

        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     *认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            String userName = (String)authenticationToken.getPrincipal();
            User user = userService.selectUserByName(userName);
            if (user == null) {
                throw new UnknownAccountException();
            }
            if (Boolean.TRUE.equals(user.isLocked())) {
                throw new LockedAccountException();
            }
            Object principal = user.getUserName();
            Object credentials = user.getPassword();
            ByteSource credentialsSalt = ByteSourceUtils.bytes(user.getCrenditalSalt());
            String realmName = getName();
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
            return info;
        } catch (UnknownAccountException e) {
            e.printStackTrace();
        } catch (LockedAccountException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
