package com.javatiaocao.myblog.config;

import com.javatiaocao.myblog.service.impl.UserDetailServiceImpl;
import com.javatiaocao.myblog.utils.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/aboutme", "/archives", "/categories", "/friendlylink", "/tags", "/update","/swagger-ui.html**","/v2/api-docs","/swagger-resources/**","/webjars/**")
                .permitAll()
                .antMatchers("/editor", "/user").hasAnyRole("USER")
                .antMatchers("/ali", "/mylove").hasAnyRole("ADMIN")
                .antMatchers("/superadmin", "/myheart", "/today", "/yesterday")
                .hasAnyRole("SUPERADMIN")
                .and()
                //实现跳转自定义登陆页面
                .formLogin().loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/")
                .and()
                //添加iframe/frame组件
                .headers().frameOptions().sameOrigin()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
        //关闭跨站请求伪造
        http.csrf().disable();
    }

    @Bean
    UserDetailsService userDetailsServiceImpl() {
        return new UserDetailServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(new PasswordEncoder() {
            MD5Util mD5Util = new MD5Util();

            @Override
            public String encode(CharSequence rawPassword) {
                return mD5Util.encode((String) rawPassword);

            }

            /**
             * @param rawPassword 用户输入的密码
             * @param encodedPassword 数据库中的加了密的密码
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(mD5Util.encode((String) rawPassword));
            }
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
