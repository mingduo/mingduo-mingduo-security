package com.mingduo.security.core.social;

import com.mingduo.security.core.properties.QQProperties;
import com.mingduo.security.core.properties.SecurityProperites;
import com.mingduo.security.core.properties.WeixinProperties;
import com.mingduo.security.core.social.qq.api.QQ;
import com.mingduo.security.core.social.qq.connect.QQConnectionFactory;
import com.mingduo.security.core.social.support.CustomSocialConfigurer;
import com.mingduo.security.core.social.weixin.api.Weixin;
import com.mingduo.security.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/30
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperites securityProperites;

    @Autowired
    private ObjectProvider<ConnectionFactory<QQ>> qqConnectionFactory;

    @Autowired
    private ObjectProvider<ConnectionFactory<Weixin>> weixinConnectionFactory;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        connectionRepository.setTablePrefix("tbl_");
        return connectionRepository;
    }


    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        qqConnectionFactory.ifAvailable(conectFactory -> connectionFactoryConfigurer.addConnectionFactory(conectFactory));
        weixinConnectionFactory.ifAvailable(conectFactory -> connectionFactoryConfigurer.addConnectionFactory(conectFactory));

    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @ConditionalOnProperty(prefix = "my.security.social.qq", name = "app-id")
    @Bean
    public ConnectionFactory<QQ> qqConnectionFactory() {
        QQProperties qqProperties = securityProperites.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(), qqProperties.getAppId(), qqProperties.getAppSecret());
    }


    @ConditionalOnProperty(prefix = "my.security.social.weixin", name = "app-id")
    @Bean
    public ConnectionFactory<Weixin> weixinConnectionFactory() {
        WeixinProperties weixinProperties = securityProperites.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(), weixinProperties.getAppSecret());
    }


    @Bean
    public SpringSocialConfigurer socialConfigurer(){

        SpringSocialConfigurer socialConfigurer = new CustomSocialConfigurer(securityProperites.getSocial().getFilterProcessesUrl());
        return socialConfigurer;
    }
}
