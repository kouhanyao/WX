package com.example.wx.vo;


/**
 * Created by renyouchao on 15/12/9.
 */

public class WxEvent {

    public String toUserName;	//开发者微信号

    public String fromUserName;	//发送方帐号（一个OpenID）

    public Long createTime;	//消息创建时间 （整型）

    public String msgType;	//消息类型，event

    public String event;	//事件类型，subscribe(订阅)、unsubscribe(取消订阅)

    public String eventKey;	//事件KEY值，qrscene_为前缀，后面为二维码的参数值

    public String ticket;	//二维码的ticket，可用来换取二维码图片



    @Override
    public String toString() {
        return "EventMsg{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                ", eventKey='" + eventKey + '\'' +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}
