package com.mingduo.security.core.validate.code.controller;

import com.mingduo.security.core.constants.SecurityConstants;
import com.mingduo.security.core.constants.ValidateCodeType;
import com.mingduo.security.core.validate.code.ValidateCodeProcessor;
import com.mingduo.security.core.validate.code.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成校验码的请求处理器
 *
 * @author : weizc
 * @description:
 * @since 2019/10/23
 */
@RestController
public class ValidateCodeController {


    @Autowired
    ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) throws Exception {

        validateCodeProcessorHolder.findValidateProcessor(ValidateCodeType.valueOf(type.toUpperCase()))
                .create(new ServletWebRequest(request,response));


    }
}
