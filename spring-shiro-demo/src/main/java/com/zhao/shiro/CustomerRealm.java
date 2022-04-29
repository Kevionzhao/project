package com.zhao.shiro;

import com.zhao.model.User;
import com.zhao.service.UserService;
import com.zhao.util.JWTUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Admin
 */
@Component
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————用户身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        //获取用户对象
        User user = userService.getUser(username);
        /* 以下数据库查询可根据实际情况，可以不必再次查询，这里我两次查询会很耗资源
         * 我这里增加两次查询是因为考虑到数据库管理员可能自行更改数据库中的用户信息
         */
        String password = userService.getPassword(username);
        if (password == null) {
            throw new AuthenticationException("该用户不存在！");
        }
        int ban = userService.checkUserBanStatus(username);
        if (ban == 1) {
            throw new AuthenticationException("该用户已被封号！");
        }
        //校验的时候传入字段不同获取用户明信息的方式不同
//        return new SimpleAuthenticationInfo(token, token, "MyRealm");

        // 行自动校验密码
        //盐可以通过ByteSource.Util.bytes(Object);方法获得
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token, user.getPassWord(),
                ByteSource.Util.bytes(user.getSalt()), getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，
     * 例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————用户权限认证————");
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String permissions = userService.getPermissionList(username);
        String roles = userService.getRolesList(username);
        List<String> permissionList = Arrays.asList(permissions.split(","));
        List<String> roleList = Arrays.asList(roles.split(","));
//        //获得该用户角色
//        String role = userService.getRole(username);
//        //每个角色拥有默认的权限
//        String rolePermission = userService.getRolePermission(username);
//        //每个用户可以设置新的权限
//        String permission = userService.getPermission(username);
//        Set<String> roleSet = new HashSet<>();
//        Set<String> permissionSet = new HashSet<>();
//        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
//        roleSet.add(role);
//        permissionSet.add(rolePermission);
//        permissionSet.add(permission);
//        //设置该用户拥有的角色和权限
//        info.setRoles(roleSet);
//        info.setStringPermissions(permissionSet);
        Set<String> roleSet = new HashSet<>();
        roleSet.addAll(roleList);
        info.setRoles(roleSet);
        info.addStringPermissions(permissionList);
        return info;
    }
//    @Bean(name = "hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
//        // 设置加密次数
//        hashedCredentialsMatcher.setHashIterations(1);
//        return hashedCredentialsMatcher;
//    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //自定义认证加密方式
        CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher();
        // 设置自定义认证加密方式
        super.setCredentialsMatcher(customCredentialsMatcher);
    }



}
