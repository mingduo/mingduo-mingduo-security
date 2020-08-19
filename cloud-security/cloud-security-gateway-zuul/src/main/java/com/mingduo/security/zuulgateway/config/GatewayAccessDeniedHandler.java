package com.mingduo.security.zuulgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mingduo.security.zuulgateway.filter.AuditLogOncePerFilter.LOG_USER;

/**
 * 
 * @apiNode:
 * @since 2020/4/2
 * @author : weizc 
 */
@Slf4j
@Component
public class GatewayAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        log.error("handle 403 ");

        request.setAttribute(LOG_USER,true);
        super.handle(request, response, authException);
    }
}