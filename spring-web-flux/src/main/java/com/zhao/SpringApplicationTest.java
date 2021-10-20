package com.zhao;

import com.zhao.interfaces.ProxyCreator;
import com.zhao.proxy.JDKProxyCreator;
import com.zhao.serverinfo.ApiServer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Admin
 */
@SpringBootApplication
public class SpringApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationTest.class,args);
    }

    @Bean
    ProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }

    @Bean
    FactoryBean<ApiServer> creatorBean(ProxyCreator proxyCreator){
       return new FactoryBean<ApiServer>() {
           @Override
           public ApiServer getObject() throws Exception {
               return (ApiServer) proxyCreator.createProxy(this.getObjectType());
           }

           @Override
           public Class<?> getObjectType() {
               return ApiServer.class;
           }
       };
    }
}
