package com.mingduo.security.core.social.weixin.connect;

import com.mingduo.security.core.social.weixin.api.Weixin;
import com.mingduo.security.core.social.weixin.api.WeixinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * @since 2019/10/31
 * @author : weizc 
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {


    /**
     * 微信获取授权码的url
     * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
     */
    private static  final String URL_AUTHORIZE="https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     * https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Authorized_Interface_Calling_UnionID.html
     */
    private static  final String URL_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token";


    /**
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     * @param appId
     * @param appSecret
     */
    public WeixinServiceProvider(String appId,String appSecret) {

        super(new WeixinOauth2Template(appId, appSecret, URL_AUTHORIZE, URL_TOKEN));

    }

    @Override
    public Weixin getApi(String accessToken) {
        return new WeixinImpl(accessToken);
    }
}
