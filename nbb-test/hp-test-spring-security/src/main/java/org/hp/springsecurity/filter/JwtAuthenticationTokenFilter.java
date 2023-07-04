package org.hp.springsecurity.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * token过滤器，模拟从header中获取登录用户信息和用户所拥有的权限信息
 * 
 * @author ruoyi
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        // 判断用户是否已登录
        String userName = request.getHeader("userName");
        if (userName != null) {
            List<SimpleGrantedAuthority> authoritys = Collections.emptyList();
            try {
                // 判断用户拥有的权限
                String[] perms = request.getHeader("perms").split(",");

                authoritys = Arrays.stream(perms).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            } catch (Exception e) {

            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authoritys);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
