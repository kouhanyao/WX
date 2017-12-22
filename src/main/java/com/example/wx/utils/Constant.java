/*
 *Copyright 2014 leleyun
 *Renyouchao
 *
*/
package com.example.wx.utils;

public class Constant {
	/**
	 * 事件类型，subscribe(订阅)
	 * 扫描带参数二维码事件  -- 未关注
	 */
	public static String SUBSCRIBE_EVENT = "subscribe";
	/**
	 * 事件类型  unsubscribe(取消订阅)
	 */
	public static String UNSUBSCRIBE_EVENT = "unsubscribe";
	/**
	 * 扫描带参数二维码事件  -- 已关注
	 */
	public static String SCAN_EVENT = "SCAN";
	/**
	 * 上报地理位置事件
	 */
	public static String LOCATION_EVENT = "LOCATION";
	/**
	 *自定义菜单事件  - 点击菜单拉取消息时的事件推送
	 */
	public static String CLICK_EVENT = "CLICK";
	/**
	 *自定义菜单事件  - 点击菜单跳转链接时的事件推送
	 */
	public static String VIEW_EVENT = "VIEW";
	/**
	 * 消息类型 - 文本消息
	 */
	public static String MSG_TYPE_TEXT = "text";
	/**
	 * 消息类型 - 事件消息
	 */
	public static String MSG_TYPE_EVENT = "event";

	public static String CLICK_CONTACT_EVENT_KEY = "V1001_CONTACT";

	//上门模板
	public static String CLICK_SHANG_MEN = "V1001_SHANG_MEN";

	public static final String RESULT_CODE_KEY = "code";

	public static final String RESULT_CODE_VALUE = "000";

	public static final String RESULT_MSG_KEY = "msg";

	public static String GRCODE_SEQ = "grcode_seq";

	public static String SCENE_ID_SEQ = "scene_id_seq";

    public final static String DOCTOR_REPORT_TYPE = "doctor";

    public final static String CHANNEL_REPORT_TYPE = "channel";

    public final static String GROUP_REPORT_TYPE = "group";

    public final static String USER_REPORT_TYPE = "user";

	public final static String DOWNLOAD_TYPE = "download";



}
