package com.example.wx.vo;

/**
 * Created by renyouchao on 15/12/16.
 */
public class WxText {

    public String toUserName;	//开发者微信号

    public String fromUserName;	//发送方帐号（一个OpenID）

    public Long createTime;	//消息创建时间 （整型）

    public String msgType;	//消息类型，event

    public String content;

    public String msgId;

    @Override
    public String toString() {
        return "Text{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
