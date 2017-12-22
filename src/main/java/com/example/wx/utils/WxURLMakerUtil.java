package com.example.wx.utils;

public class WxURLMakerUtil {

    private static String httpHost = "https://api.weixin.qq.com";

    /**
     * 请求微信接口用到的ACCESS_TOKEN
     */
    private static String accesstoken = "";

    public static String getAccesstoken() {
        return accesstoken;
    }

    public static void setAccesstoken(String accesstoken) {
        WxURLMakerUtil.accesstoken = accesstoken;
    }

    /**
     * 获取access_token 接口地址
     * @return
     */
    public static String getAccessTokenUrl(String appId,String appSecret){
       return new StringBuffer(httpHost).append("/cgi-bin/token?grant_type=client_credential&appid=")
               .append(appId)
               .append("&secret=")
               .append(appSecret).toString();
    }

    /**
     *
     * @param token
     * @return
     */
    public static String getCustomSendUrl(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/message/custom/send?access_token=")
                .append(token).toString();
    }

    /**
     * 获取用户基本信息接口地址
     * @param token
     * @param openId
     * @return
     */
    public static String getUserInfoUrl(String token,String openId){
        return new StringBuffer(httpHost).append("/cgi-bin/user/info?access_token=")
                .append(token).append("&openid=").append(openId).append("&lang=zh_CN").toString();
    }
    /**
     * 发生客服消息接口地址
     * @param token
     * @return
     */
    public static String kfaccount(String token){
        return new StringBuffer(httpHost).append("/customservice/kfaccount/add?access_token=")
                .append(token).toString();
    }
    /**
     * 发送模板消息
     * @param token
     * @return
     */
    public static String getSendTemplateUrl(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/message/template/send?access_token=")
                .append(token).toString();
    }
    /**
     * 创建二维码接口
     * @param token
     * @return
     */
    public static String getCreateQrcodeUrl(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/qrcode/create?access_token=")
                .append(token).toString();
    }
    /**
     * 获取二维码接口
     * @return
     */
    public static String getShowqrcodeUrl(String ticket){
        return new StringBuffer(httpHost).append("/cgi-bin/showqrcode?ticket=")
                .append(ticket).toString();
    }
    /**
     * 自定义菜单创建接口
     * @return
     */
    public static String getCreateMenuUrl(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/menu/create?access_token=")
                .append(token).toString();
}
    /**
     * 获取 js sdk getjsapi_ticket
     * @return
     */
    public static String getjsapi_ticket(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/ticket/getticket?access_token=")
                .append(token).append("&type=jsapi").toString();
    }
    /**
     * 自定义菜单创建接口
     * @return
     */
    public static String getwebaccess_tokenurl(String appId,String appSecret,String code){
        return new StringBuffer(httpHost).append("/sns/oauth2/access_token?appid=")
                .append(appId).append("&secret=").append(appSecret).append("&code=").append(code).append("&grant_type=authorization_code").toString();
    }
    /**
     * 多媒体次啊在
     * @return
     */
    public static String media(String token,String media_id){
        return new StringBuffer(httpHost).append("/cgi-bin/media/get?access_token=").append(token).append("&media_id=").append(media_id).toString();
    }
    /**
     * 获取标签下的粉丝
     * @return
     */
    public static String tag(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/user/tag/get?access_token=").append(token).toString();
    }
    /**
     * 获取所有标签
     * @return
     */
    public static String tags(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/tags/get?access_token=").append(token).toString();
    }
    /**
     * 创建标签
     * @return
     */
    public static String createtag(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/tags/create?access_token=").append(token).toString();
    }
    /**
     * 为用户打标签
     * @return
     */
    public static String batchtagging(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/tags/members/batchtagging?access_token=").append(token).toString();
    }

    /**
     * 新增永久素材
     * @return
     */
    public static String addForeverMaterial(String token, String type){
        return new StringBuffer(httpHost).append("/cgi-bin/material/add_material?access_token=").append(token).append("&type=").append(type).toString();
    }
    /**
     * 新增临时素材
     * @return
     */
    public static String addmedia(String token, String type){
        return new StringBuffer(httpHost).append("/cgi-bin/media/upload?access_token=").append(token).append("&type=").append(type).toString();
    }

    /**
     * 删除永久素材
     * @return
     */
    public static String deleteMaterial(String token){
        return new StringBuffer(httpHost).append("/cgi-bin/material/del_material?access_token=").append(token).toString();
    }
}
