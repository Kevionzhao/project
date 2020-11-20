package com.zdxf.config;


import com.zdxf.common.utils.CommonUtils;
import com.zdxf.filter.JwtAuthenticationEntryPoint;
import com.zdxf.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author Admin
 * @EnableWebSecurity注解继承WebSecurityConfigurerAdapter的类
 * 这样就构成了Spring Security的配置。
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Value("${jwt.antMatchers}")
    private String antMatchers;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                //用来解决匿名用户访问无权限资源时的异常,
                //jwtAuthenticationEntryPoint为异常处理类；
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //禁用session 无状态
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // don’t authenticate this particular request 白名单
                .and()
                .authorizeRequests()
                //配置白明单，白名单之外的都需要token验证才可以访问
                .antMatchers(antMatchers.split(",")).permitAll()
                //白名单之外的所有请求都需要认证才能访问
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // 禁用页面缓存
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();
        // 登出处理
        httpSecurity.logout()
                    .logoutUrl("/login/logout")
                    //指定是否在注销时让HttpSession无效
                    .invalidateHttpSession(true);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //SpringSecurity 推荐加密方法
        //return new BCryptPasswordEncoder();
        //能够匹配数据库保存的各种加密方式，但是密码保存应该使用这样的模板
        //{加密算法}+加密后的密码
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //自定义加密方式
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return CommonUtils.password((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(CommonUtils.password((String)rawPassword));
            }
        };
    }
}
