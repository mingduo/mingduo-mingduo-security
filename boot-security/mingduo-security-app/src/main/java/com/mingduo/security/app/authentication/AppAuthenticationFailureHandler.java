package com.mingduo.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  APP环境下认证失败处理器
 * @author : weizc
 * @description:
 * @since 2019/10/22
 */
@Component
@Slf4j
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

            ResponseEntity responseEntity = new ResponseEntity<>( exception.getMessage(),HttpStatus.UNAUTHORIZED);
            response.getWriter().println(objectMapper.writeValueAsString(responseEntity));
            response.getWriter().flush();

    }
}
