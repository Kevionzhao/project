package com.zhao.bank1.controller;

import com.zhao.bank1.entity.User;
import com.zhao.bank1.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@RestController
public class SpringCacheController {


    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/getUserById/{id}")
    @Cacheable(cacheNames = "user",key = "#root.methodName")
    public User getUserById(@PathVariable Integer id){
        User user = userServiceImpl.getUser(id);
        return user;
    }

    @GetMapping("/getUserInfo/{id}")
    @Cacheable(cacheNames = "user1",key = "#root.method.name")
    public User getUserInfo(@PathVariable Integer id){
        User user = userServiceImpl.getUser(id);
        return user;
    }

    @GetMapping("/getUserA/{id}")
    @Cacheable(cacheNames = "user3",key = "#root.targetClass")
    public User getUserA(@PathVariable Integer id){
        User user = userServiceImpl.getUser(id);
        return user;
    }
}
