package com.xj.boot.security.demo.config;

import com.xj.boot.security.demo.filter.LoginFilter;
import com.xj.boot.security.demo.handle.SecurityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    LoginFilter loginFilter;

    @Autowired
    SecurityExceptionHandler securityExceptionHandler;

    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();
        users.createUser(User.withUsername("user").password("{noop}123").roles("admin").build());
        return users;
    }

    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        ProviderManager pm = new ProviderManager(daoAuthenticationProvider);
        return pm;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                // 关闭CSRF
                .csrf().disable()
                // 关闭SESSION
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 设置异常处理器
                .exceptionHandling().authenticationEntryPoint(securityExceptionHandler).and()

//                .sessionManagement().disable()
                .authorizeHttpRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated().and()

                .authenticationManager(authenticationManager())

                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)

                // 添加JWT过滤器，需要放在用户名密码认证过滤器之前
//                .addFilterBefore("", UsernamePasswordAuthenticationFilter.class)
                // 认证用户是用户信息加载配置
//                .userDetailsService()

                ;
        return http.build();
    }
}
