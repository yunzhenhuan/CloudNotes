package com.nucyzh.login_third;

/**
 * Author:XiYang on 2016/2/21.
 * Email:765849854@qq.com
 */
public class Constants {

    /**
     * 此为Bmob的APP_ID
     */
    public static String BMOB_APPID = "c238263866c0f587531c8c406cc47251";//--外网


    /**
     * 此为腾讯官方提供给开发者的APP_ID
     */

    public static final String QQ_APP_ID = "1105090435";

    /**
     * 微信平台的APPID,请自行前往微信开放平台注册申请应用
     */
    public static final String WEIXIN_APP_ID = "wx5ed4d9e4b23c86d5";

    /**
     * 微信平台的AppSecret
     */
    public static final String WEIXIN_APP_SECRET = "8f6ddb4f4b63877a6826b549b3b0bfcc ";
    /**
     * 微信平台的grant type，固定值：authorization_code
     */
    public static final String WEIXIN_GRANT_TYPE = "authorization_code";


    /**
     * 当前Demo对应的weibo平台的APP_KEY ，开发者需使用自己的 APP_KEY替换该 APP_KEY
     */
    public static final String WEIBO_APP_KEY = "248229023";

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     */
    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。
     */
    public static final String WEIBO_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
