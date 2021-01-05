package com.lsl.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  /*  //定义用户信息服务(查询用户信息)
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("123").authorities("p2").build());
        return manager;
    }*/
    //密码编码器
    @Bean
    /*
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
           http.csrf().disable()//屏蔽CSRF跨站请求伪造的发生
                .authorizeRequests()
    /*          .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")*/
                .antMatchers("/r/r3").access("hasAuthority('p1') and hasAuthority('p2')")
                .antMatchers("/r/**").authenticated() // 所有/r/**的请求必须认证通过
                .anyRequest().permitAll()      //除了/r/**所有的请求都可以通过
                .and()
                .formLogin() //允许表单登录
                .loginPage("/login-view") //登录页面
                .loginProcessingUrl("/login") //登录页面提交到的地址
                .successForwardUrl("/login-success")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view?logout");
    }
}
