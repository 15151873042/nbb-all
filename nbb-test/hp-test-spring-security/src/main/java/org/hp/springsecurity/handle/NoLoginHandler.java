package org.hp.springsecurity.handle;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 未登录失败处理类
 */
@Component
public class NoLoginHandler implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("{\"code\": \"401\", \"msg\": \"未登录\"}");
    }
}
