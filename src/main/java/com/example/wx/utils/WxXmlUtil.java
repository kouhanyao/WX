package com.example.wx.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yao on 2017/6/7.
 */
public class WxXmlUtil {
    /**
     * @param openId       接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime   消息创建时间 （整型）
     * @param content      回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     * @return
     * @throws Exception
     */
    public static String creatTextXml(String openId, String fromUserName, Long createTime, String content) throws Exception {
        StringBuffer xml = new StringBuffer("");
        xml.append("<xml><ToUserName><![CDATA[").append(openId).append("]]></ToUserName>\n<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>" +
                "<CreateTime>").append(createTime).append("</CreateTime>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<Content><![CDATA[").append(content).append("]]></Content>" +
                "</xml>");
        return xml.toString();
    }

    /**
     * 创建图文消息
     *
     * @param openId       接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime   消息创建时间 （整型）
     * @return
     * @throws Exception
     */
    public static String creatinmgTextXml(String openId, String fromUserName, Long createTime, List<Map<String, Object>> news) throws Exception {
        StringBuffer xml = new StringBuffer("");
        xml.append("<xml>\n").append(
                "<ToUserName><![CDATA[").append(openId).append("]]></ToUserName>\n").append(
                "<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>\n").append(
                "<CreateTime>").append(createTime).append("</CreateTime>\n").append(
                "<MsgType><![CDATA[news]]></MsgType>\n").append(
                "<ArticleCount>").append(news.size()).append("</ArticleCount>\n").append(
                "<Articles>\n");
        for (Map<String, Object> item : news) {
            xml.append("<item>\n").append(
                    "<Title><![CDATA[").append(item.get("title")).append("]]></Title> \n").append(
                    "<Description><![CDATA[").append(item.get("description")).append("]]></Description>\n").append(
                    "<PicUrl><![CDATA[").append(item.get("picurl")).append("]]></PicUrl>\n").append(
                    "<Url><![CDATA[").append(item.get("url")).append("]]></Url>\n").append(
                    "</item>\n");
        }

        xml.append(
                "</Articles>\n").append(
                "</xml>");
        return xml.toString();
    }


    /**
     * @return
     * @throws Exception
     */
    public static String payxml(String return_code, String return_msg) throws Exception {
        StringBuffer xml = new StringBuffer("");
        xml.append("<xml><return_code><![CDATA[").append(return_code).append("]]></return_code>\n<return_msg><![CDATA[").append(return_msg).append("]]></return_msg></xml>");
        return xml.toString();
    }

    /**
     * 根据Map组装xml消息体，值对象仅支持基本数据类型、String、BigInteger、BigDecimal，以及包含元素为上述支持数据类型的Map
     *
     * @param vo
     * @param rootElement
     * @return
     * @author
     */
    public static String map2xmlBody(Map<String, Object> vo, String rootElement) {
        org.dom4j.Document doc = org.dom4j.DocumentHelper.createDocument();
        Element body = org.dom4j.DocumentHelper.createElement(rootElement);
        doc.add(body);
        __buildMap2xmlBody(body, vo);
        return doc.asXML();
    }

    public static void __buildMap2xmlBody(Element body, Map<String, Object> vo) {
        if (vo != null) {
            Iterator<String> it = vo.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (StringUtils.isNotEmpty(key)) {
                    Object obj = vo.get(key);
                    Element element = DocumentHelper.createElement(key);
                    if (obj != null) {
                        if (obj instanceof String) {
                            element.setText((String) obj);
                        } else {
                            if (obj instanceof Character || obj instanceof Boolean || obj instanceof Number
                                    || obj instanceof java.math.BigInteger || obj instanceof java.math.BigDecimal) {
                                org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", obj.getClass().getCanonicalName());
                                element.add(attr);
                                element.setText(String.valueOf(obj));
                            } else if (obj instanceof Map) {
                                org.dom4j.Attribute attr = DocumentHelper.createAttribute(element, "type", Map.class.getCanonicalName());
                                element.add(attr);
                                __buildMap2xmlBody(element, (Map<String, Object>) obj);
                            } else {
                            }
                        }
                    }
                    body.add(element);
                }
            }
        }
    }

    /**
     * 根据xml消息体转化为Map
     *
     * @param xml
     * @param rootElement
     * @return
     * @throws DocumentException
     * @author
     */
    public static Map xmlBody2map(String xml, String rootElement) throws DocumentException {
        org.dom4j.Document doc = DocumentHelper.parseText(xml);
        Element body = (Element) doc.selectSingleNode("/" + rootElement);
        Map vo = __buildXmlBody2map(body);
        return vo;
    }

    public static Map __buildXmlBody2map(Element body) {
        Map vo = new HashMap<>();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if (StringUtils.isNotEmpty(key)) {
                    String type = element.attributeValue("type", "java.lang.String");
                    String text = element.getText().trim();
                    Object value = null;
                    if (String.class.getCanonicalName().equals(type)) {
                        value = text;
                    } else if (Character.class.getCanonicalName().equals(type)) {
                        value = new Character(text.charAt(0));
                    } else if (Boolean.class.getCanonicalName().equals(type)) {
                        value = new Boolean(text);
                    } else if (Short.class.getCanonicalName().equals(type)) {
                        value = Short.parseShort(text);
                    } else if (Integer.class.getCanonicalName().equals(type)) {
                        value = Integer.parseInt(text);
                    } else if (Long.class.getCanonicalName().equals(type)) {
                        value = Long.parseLong(text);
                    } else if (Float.class.getCanonicalName().equals(type)) {
                        value = Float.parseFloat(text);
                    } else if (Double.class.getCanonicalName().equals(type)) {
                        value = Double.parseDouble(text);
                    } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigInteger(text);
                    } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigDecimal(text);
                    } else if (Map.class.getCanonicalName().equals(type)) {
                        value = __buildXmlBody2map(element);
                    } else {
                    }
                    vo.put(key, value);
                }
            }
        }
        return vo;
    }
}
