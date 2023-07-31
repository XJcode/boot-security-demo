package com.xj.boot.security.demo.filter;

import com.xj.boot.security.demo.config.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取Token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.length() > 0) {
            String username = JWTUtil.decode(token);
            UserDetails user = new User(username, "123", new ArrayList<>());

            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
