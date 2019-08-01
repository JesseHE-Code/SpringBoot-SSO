package com.example.ssoservernew.config;

import com.example.ssoservernew.handler.MySuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @Author :hezhiqiang06
 * @Date :2019-07-25 11:15
 **/

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MySuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * 授权方式提供者，判断授权有效性，用户有效性，在判断用户是否有效性，它依赖于UserDetailsService实例，开发人员可以自定义UserDetailsService的实现。
     * ProviderManager是AuthenticationManager的实现类，提供了基本认证实现逻辑和流程；
     * ProviderManager 通过 AuthenticationProvider 扩展出更多的验证提供的方式；而 AuthenticationProvider 本身也就是一个接口，
     * 从类图中我们可以看出它的实现类AbstractUserDetailsAuthenticationProvider 和AbstractUserDetailsAuthenticationProvider
     * 的子类DaoAuthenticationProvider 。DaoAuthenticationProvider 是Spring Security中一个核心的Provider,对所有的数据库提供
     * 了基本方法和入口。
     *
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        //定义一个Provider
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //设置userDetails
        authenticationProvider.setUserDetailsService(userDetailsService);
        //password Encoder
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        //是不是隐藏UserNotFound异常
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;
    }

    /**
     * HttpSecurity
     * 确保我们应用中的所有请求都需要用户被认证
     * .formLogin().permitAll() --> 允许用户进行基于表单的认证
     * 允许用户使用HTTP基本验证进行认证
     *  通过重载，配置如何拦截器保护请求
     *
     *  configure(WebSecurity)	通过重载，配置Spring Security的Filter链
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http
                .requestMatchers().antMatchers("/oauth/**","/login/**", "/logout/**","/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/success","/test").authenticated()   //需要权限的
                .antMatchers("**/**.css", "**/**.js","/register").permitAll()
                .and()
                .formLogin().successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("SESSIONID","JSESSIONID")
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and()
                .csrf()
                    .disable();

        http.addFilterAt(personalLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
    }

    /**
     * 通过重载，配置user-detail服务
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        //AuthenticationProvider 的管理
        auth.authenticationProvider(authenticationProvider());

    }

    private PersonalLoginFilter personalLoginFilter(){

        PersonalLoginFilter personalLoginFilter = new PersonalLoginFilter("/login");
        try {
            personalLoginFilter.setAuthenticationManager(authenticationManager());
        }
        catch (Exception e){
            //
        }
        return personalLoginFilter;
    }
}

