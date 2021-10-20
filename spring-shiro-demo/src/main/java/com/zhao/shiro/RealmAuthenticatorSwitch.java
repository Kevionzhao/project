package com.zhao.shiro;

import com.zhao.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Admin
 * 多realm登录验证时，通过继承ModularRealmAuthenticator类，重写isPermitted来实现realm的对应匹配校验
 */
public class RealmAuthenticatorSwitch extends ModularRealmAuthenticator {

    /**
     * Realm 校验分配器
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        super.assertRealmsConfigured();
        // 登录类型对应的所有Realm
        Collection<Realm> realms = getRealms();
        String type = JWTUtil.getType((String)authenticationToken.getCredentials());
        Collection<Realm> realmList = new ArrayList<>();
        for(Realm realm : realms){
            if (realm.getName().contains(type)){
                realmList.add(realm);
            }
        }
        if (realms.size() == 1) {
            return doSingleRealmAuthentication(realms.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realmList, authenticationToken);
        }
    }
}
