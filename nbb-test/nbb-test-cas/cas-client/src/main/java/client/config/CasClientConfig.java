package client.config;


import client.properties.CasProperties;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CasProperties.class})
public class CasClientConfig {

    @Autowired
    private CasProperties casProperties;



    /**
     * 此监听器的作用是为了防止内存泄漏，当session销毁时，销毁SingleSignOutHttpSessionListener内部维护的session
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }


    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new SingleSignOutFilter());
        registrationBean.setName("SingleSignOutFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }


    /**
     * AuthenticationFilter：校验是否登录拦截器，校验当前请求是否是否携带ticket参数或者当前用户已登录，如果是则交给下一个过滤器处理，如果不是则跳转到cas-server登陆页
     */
    @Bean
    public FilterRegistrationBean casFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new AuthenticationFilter());
        registrationBean.setName("AuthenticationFilter");
        registrationBean.addInitParameter("casServerLoginUrl",casProperties.getCasServerLoginUrl());
        registrationBean.addInitParameter("serverName",casProperties.getServerName());
        registrationBean.addUrlPatterns("/*"); // 需要校验登录的接口
        registrationBean.setOrder(3);
        return registrationBean;
    }


    /**
     * AbstractTicketValidationFilter：校验ticket拦截器
     *
     * 如果参数中含有不含ticket参数，则交给下一个过滤器处理，
     * 如果参数中有ticket则调用sso-server校验ticket，ticket校验成功则创建session，最终重定向到访问页面
     * 校验失败则抛出异常
     */
    @Bean
    public FilterRegistrationBean casValidationFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new Cas20ProxyReceivingTicketValidationFilter());
        registrationBean.setName("TicketValidationFilter");
        registrationBean.addInitParameter("casServerUrlPrefix",casProperties.getCasServerUrlPrefix());
        registrationBean.addInitParameter("serverName",casProperties.getServerName());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(4);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean casHttpWrapperFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new HttpServletRequestWrapperFilter());
        registrationBean.setName("CAS HttpServletRequest Wrapper Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(5);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean casAssertionLocalFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new AssertionThreadLocalFilter());
        registrationBean.setName("CAS Assertion Thread Local Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(6);
        return registrationBean;
    }

}
