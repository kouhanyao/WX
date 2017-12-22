package com.example.wx.wx;

import com.alibaba.fastjson.JSON;
import com.example.wx.utils.*;
import com.example.wx.vo.WxEvent;
import com.example.wx.vo.WxText;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 */
@Controller
@RequestMapping("/wx")
public class WxController {

    private Logger logger = LoggerFactory.getLogger(WxController.class);

    @ResponseBody
    @RequestMapping(value = "/callback", method = RequestMethod.POST, produces = "application/xml;charset=UTF-8")
    public String callback(@RequestBody String xml) throws IOException {
        logger.debug("xml={}",xml);
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            String msgType = root.element("MsgType").getText();
            // 事件消息
            if (msgType.equals(Constant.MSG_TYPE_EVENT)) {
                WxEvent wxEvent = new WxEvent();
                wxEvent.toUserName = root.element("ToUserName").getText();
                wxEvent.fromUserName = root.element("FromUserName").getText();
                wxEvent.createTime = new Long(root.element("CreateTime").getText());
                wxEvent.msgType = msgType;
                wxEvent.event = root.element("Event").getText();
                if(root.element("EventKey") != null){
                    wxEvent.eventKey = root.element("EventKey").getText();
                }
                if (root.element("Ticket") != null) {
                    wxEvent.ticket = root.element("Ticket").getText();
                }
                if (wxEvent.event.equals(Constant.SUBSCRIBE_EVENT) || wxEvent.event.equals(Constant.SCAN_EVENT)){ // 关注公众号
                    logger.debug("关注公众号");
                    //未关注公众号扫二维码
                    if(wxEvent.eventKey != null && !"".equals(wxEvent.eventKey)){
                        return WxXmlUtil.creatTextXml(wxEvent.fromUserName, wxEvent.toUserName, new Date().getTime(), "EventKey"+wxEvent.eventKey);
                    }
                    //普通关注
                    return WxXmlUtil.creatTextXml(wxEvent.fromUserName, wxEvent.toUserName, new Date().getTime(), "非扫码关注");
                } else if (wxEvent.event.equals(Constant.UNSUBSCRIBE_EVENT)){//取消关注
                    return WxXmlUtil.creatTextXml(wxEvent.fromUserName, wxEvent.toUserName, new Date().getTime(), "你已经取消关注");
                } else if (wxEvent.event.equals(Constant.CLICK_EVENT)){ //自定义菜单事件  - 点击菜单拉取消息时的事件推送
                    if (wxEvent.eventKey.equals(Constant.CLICK_CONTACT_EVENT_KEY)){
                        //发送图文消息
                        List<Map<String, Object>> list = new ArrayList<>();
                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("title", "man上门服务");
                        map1.put("description", "专门为你提供各种服务，电话17072873994");
                        map1.put("picurl", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510544934191&di=ba7e72aa7fb1adff9390080c69dd43c7&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123921838.jpg");
                        map1.put("url", "http://www.baidu.com/");

                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("title", "woman上门服务");
                        map2.put("description", "专门为你提供各种服务，电话17072873994");
                        map2.put("picurl", "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=dce088c1a3ec8a1300175fa39f6afbfa/622762d0f703918f2b2091e45b3d269759eec42f.jpg");
                        map2.put("url", "http://www.soso.com/");
                        list.add(map1);
                        list.add(map2);
                        return WxXmlUtil.creatinmgTextXml(wxEvent.fromUserName, wxEvent.toUserName, new Date().getTime(), list);
                    }else if(Constant.CLICK_SHANG_MEN.equals(wxEvent.eventKey)){
                        //发送模板
                        createAndSendTemplate();
                    }
                }else if(wxEvent.event.equals(Constant.VIEW_EVENT)){ // 点击菜单

                }/*else if(wxEvent.event.equals(Constant.SCAN_EVENT)) { // 扫描带参数的二维码
                    return WXXmlMsgUtil.subscribepackage(wxEvent);
                }*/
            } else if (msgType.equals(Constant.MSG_TYPE_TEXT)){ // 文本消息
                WxText wxText = new WxText();
                wxText.toUserName = root.element("ToUserName").getText();
                wxText.fromUserName = root.element("FromUserName").getText();
                wxText.createTime = new Long(root.element("CreateTime").getText());
                wxText.msgType = msgType;
                wxText.content = root.element("Content").getText();
                wxText.msgId = root.element("MsgId").getText();
                if("寇含尧".equals(wxText.content))
                    return WxXmlUtil.creatTextXml(wxText.fromUserName, wxText.toUserName, new Date().getTime(), "<a href='http://www.leley.com/pt.html'>下载乐乐医</a>");
            }else {
                WxText wxText = new WxText();
                wxText.toUserName = root.element("ToUserName").getText();
                wxText.fromUserName = root.element("FromUserName").getText();
                wxText.createTime = new Long(root.element("CreateTime").getText());
//                return WxXmlUtil.creatTextXml(wxText.fromUserName, wxText.toUserName, new Date().getTime(), "<a href='http://www.leley.com/pt.html'>下载乐乐医</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/callback", method = RequestMethod.GET, produces = "application/text;charset=UTF-8")
    public String valid(@RequestParam String signature,
                        @RequestParam String timestamp,
                        @RequestParam String nonce,
                        @RequestParam String echostr) {
        logger.debug("signature={},timestamp={},nonce={},echostr={}", signature, timestamp, nonce, echostr);
        logger.debug(WeiXinUtil.token);
        String[] str = {WeiXinUtil.token, timestamp, nonce};
        Arrays.sort(str); // 字典序排序
        String sortStr = str[0] + str[1] + str[2];
        String otherSignature = null;
        try {
            otherSignature = StringUtil.sha1(sortStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (signature.equalsIgnoreCase(otherSignature)) {
            return echostr;
        } else {
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("/createMenu")
    public String createMenu() throws Exception {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> buttons = new ArrayList<>();
        Map<String,Object> button1 = new HashMap<>();
        button1.put("name","搜索");
        button1.put("type","view");
        button1.put("url","http://www.baidu.com/");

        Map<String,Object> button2 = new HashMap<>();
        button2.put("name","功能");
        List<Map<String,Object>> mapList1 = new ArrayList<>();
        Map<String,Object> subButton21 = new HashMap<>();
        subButton21.put("name","模板信息");
        subButton21.put("type","click");
        subButton21.put("key",Constant.CLICK_SHANG_MEN);
        mapList1.add(subButton21);
        Map<String,Object> subButton22 = new HashMap<>();
        subButton22.put("name","系统拍照发图");
        subButton22.put("type","pic_sysphoto");
        subButton22.put("key","rselfmenu_1_0");
        mapList1.add(subButton22);
        Map<String,Object> subButton23 = new HashMap<>();
        subButton23.put("name","扫码推事件");
        subButton23.put("type","scancode_push");
        subButton23.put("key","rselfmenu_0_1");
        mapList1.add(subButton23);
        Map<String,Object> subButton24 = new HashMap<>();
        subButton24.put("name","拍照或相册发图");
        subButton24.put("type","pic_photo_or_album");
        subButton24.put("key","rselfmenu_1_1");
        mapList1.add(subButton24);
        Map<String,Object> subButton25 = new HashMap<>();
        subButton25.put("name","微信相册发图");
        subButton25.put("type","pic_weixin");
        subButton25.put("key","rselfmenu_1_2");
        mapList1.add(subButton25);
        button2.put("sub_button",mapList1);

        Map<String,Object> button3 = new HashMap<>();
        button3.put("name","更多服务");
        List<Map<String,Object>> mapList2 = new ArrayList<>();
        Map<String,Object> subButton31 = new HashMap<>();
        subButton31.put("name","联系我们");
        subButton31.put("type","click");
        subButton31.put("key", Constant.CLICK_CONTACT_EVENT_KEY);
        mapList2.add(subButton31);
        Map<String,Object> subButton32 = new HashMap<>();
        subButton32.put("name","我的订单");
        subButton32.put("type","view");
        subButton32.put("url","http://www.baidu.com/");
        mapList2.add(subButton32);
        button3.put("sub_button",mapList2);


        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        map.put("button",buttons);
        System.out.println(JSON.toJSONString(map));
        try {
            boolean menu = WeiXinUtil.createMenu(map);
            if (menu){
                return "success";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping("/wxmeannew")
    public String valid(@RequestBody String json) throws Exception {

        Map<String, Object> map = JsonUtil.JsonStr2Map(json);
        try {
            boolean menu = WeiXinUtil.createMenu(map);
            if (menu){
                return "success";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    //创建并发送模板
    private void createAndSendTemplate() throws Exception{
        Map<String,Object> map = new HashMap<String, Object>();

        map.put("first", getMsgMap("订购成工","#173177"));
        map.put("keynote1", getMsgMap("郭美美","#173177"));
        map.put("keynote2", getMsgMap("258412","#173177"));
        map.put("keynote3", getMsgMap("高级","#173177"));
        map.put("remark", getMsgMap("欢迎再次订购","#173177"));
        WeiXinUtil.sendTemplate("om2Xl0a4B3DiXQFcvpzeiL6SP5gs", "y_6d0kXAk5TfK0MT2DWTr6fosDb49hCU0L387XNusoI", "http://www.baidu.com/", map);
    }

    public static Map<String,Object> getMsgMap(String value,String color){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("value",value);
        map.put("color",color);
        return map;
    }
}
