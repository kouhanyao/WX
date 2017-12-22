package com.example.wx.utils;

import com.alibaba.fastjson.JSON;
import com.example.wx.utils.WeiXinEncryptDecrypt.AesException;
import com.example.wx.utils.WeiXinEncryptDecrypt.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yao on 2017/7/3.
 */
public class WeiXinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);
    /**
     *  与开发模式接口配置信息中的Token保持一致
     */
    public static String token = "sldfjewfdf4511fd5115f";
    /**
     * 微信生成的 ASEKey
     */
    private static String encodingAesKey ="myKey";
    /**
     * 公众号第三方平台的appid
     */
    private static String appId="wx90cfdc98ad38f651";
    public static String appSecret="e327c5672b6558657d71dfbee66cc79b";

    /**
     * 解密微信发过来的密文
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timeStamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param encrypt_msg 密文，对应POST请求的数据
     * @return  解密后的明文
     */
    public static String decryptMsg(String msgSignature,String timeStamp,String nonce,String encrypt_msg) {
        WXBizMsgCrypt pc;
        String result ="";
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            result = pc.decryptMsg(msgSignature, timeStamp, nonce, encrypt_msg);
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("解密微信失败,result={}",result,e);
        }
        return result;
    }

    /**
     * 加密给微信的消息内容
     * @param replayMsg 需要回复给微信端的消息，xml格式的字符串
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
     * @return
     */
    public static String ecryptMsg(String replayMsg,String timeStamp, String nonce) {
        WXBizMsgCrypt pc;
        String result ="";
        try {
            pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            result = pc.encryptMsg(replayMsg, timeStamp, nonce);
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("解密加密失败,result={}",result,e);
        }
        return result;
    }

    public static final String[] accesstokenerrcode = {"40001","41001","40014","42001"};

    /**
     * 是否是 access_token 错误
     * @param errCode
     * @return
     */
    public static boolean isaccesstokenerr(String errCode){
        List<String> list= Arrays.asList(accesstokenerrcode);
        return list.contains(errCode);
    }

    /**
     * 判断接口是否调用成功
     * @param errCode
     * @return
     */
    public static boolean isSuccess(Object errCode){
        if(errCode == null || "0".equals(errCode.toString())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 创建自定义菜单
     * @return
     * @throws Exception
     */
    public static boolean createMenu(Map<String, Object> menu) throws Exception {
        String json = WxHttpClient.postJson(WxURLMakerUtil.getCreateMenuUrl(WxURLMakerUtil.getAccesstoken()), JSON.toJSONString(menu));
        Map<String, Object> jsonmap = JsonUtil.JsonStr2Map(json);
        if(!isSuccess(jsonmap.get("errcode"))) {
            if (isaccesstokenerr(jsonmap.get("errcode").toString())) {
                logger.error("accesstoken 失效，获取重试");
                String response = WxHttpClient.get(WxURLMakerUtil.getAccessTokenUrl(appId,appSecret));
                Map<String, Object> accesstokenmap = JsonUtil.JsonStr2Map(response);
                if(!isSuccess(accesstokenmap.get("errcode"))) {
                    logger.error("createMenu该方法中获取accesstoken失败，result={}", JSON.toJSONString(accesstokenmap));
                    return false;
                }
                //Redisutil.set("accesstoken" + appId, accesstokenmap.get("access_token").toString());
                WxURLMakerUtil.setAccesstoken(accesstokenmap.get("access_token").toString());
                json = WxHttpClient.postJson(WxURLMakerUtil.getCreateMenuUrl(WxURLMakerUtil.getAccesstoken()), JSON.toJSONString(menu));
                jsonmap = JsonUtil.JsonStr2Map(json);
                if(isSuccess(jsonmap.get("errcode"))) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }

    /**
     * 发送模板消息
     * @param openId  接收人openId
     * @param template  模板id
     * @param map  消息关键字
     * @return
     * @throws Exception
     */
    public static Boolean sendTemplate(String openId, String template,String url, Map<String, Object> map) throws Exception{
        Map<String,Object> objectMap = new HashMap<String, Object>();
        objectMap.put("touser",openId);
        objectMap.put("template_id",template);
        objectMap.put("url", url);
        objectMap.put("data",map);
        String json = WxHttpClient.postJson(WxURLMakerUtil.getSendTemplateUrl(WxURLMakerUtil.getAccesstoken()), JSON.toJSONString(objectMap));
        Map<String, Object> jsonmap = JsonUtil.JsonStr2Map(json);
        if(!isSuccess(jsonmap.get("errcode"))) {
            if (isaccesstokenerr(jsonmap.get("errcode").toString())) {
                logger.debug("accesstoken 失效，获取重试");
                String response = WxHttpClient.get(WxURLMakerUtil.getAccessTokenUrl(appId,appSecret));
                Map<String, Object> map1 = JsonUtil.JsonStr2Map(response);
                //Redisutil.set("accesstoken" + appId, map1.get("access_token").toString());
                WxURLMakerUtil.setAccesstoken(map1.get("access_token").toString());
                json = WxHttpClient.postJson(WxURLMakerUtil.getSendTemplateUrl(WxURLMakerUtil.getAccesstoken()), JSON.toJSONString(objectMap));
                jsonmap = JsonUtil.JsonStr2Map(json);
                if(isSuccess(jsonmap.get("errcode"))) {
                    return true;
                }
            }
        }else {
            return true;
        }
        return false;
    }
}
