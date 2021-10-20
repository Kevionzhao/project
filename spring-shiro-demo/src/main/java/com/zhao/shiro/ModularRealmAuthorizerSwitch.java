package com.zhao.shiro;

import com.zhao.util.JWTUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Admin
 * 多realm鉴权时，通过继承ModularRealmAuthorizer类，重写isPermitted来实现realm的对应匹配校验
 *
 */
public class ModularRealmAuthorizerSwitch extends ModularRealmAuthorizer {
    /**
     *  重写该方法实现分类校验
     * @param principals
     * @param permission
     */
        @Override
        public boolean isPermitted(PrincipalCollection principals, String permission) {
            assertRealmsConfigured();
            Collection<Realm> realms = getRealms();
            HashMap<String, Realm> realmHashMap = new HashMap<>(realms.size());
            String type = JWTUtil.getType((String) principals.getPrimaryPrincipal());
            for (Realm realm : realms) {
                if (realm.getName().contains(type)) {
                    realmHashMap.put(realm.getName(), realm);
                } else if (realm.getName().contains(type)) {
                    realmHashMap.put(realm.getName(), realm);
                }
            }

            Set<String> realmNames = principals.getRealmNames();
            if (!CollectionUtils.isEmpty(realmNames)) {
                String realmName = null;
                Iterator it = realmNames.iterator();
                while (it.hasNext()) {
                    realmName = ConvertUtils.convert(it.next());
                    if (realmName.contains(type)) {
                        AuthorizingRealm realm = (AuthorizingRealm) realmHashMap.get(realmName);
                        return realm.isPermitted(principals, permission);
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
    }