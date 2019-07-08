package com.qk.githubuserquery.utils;

public class Constant {
    // 查询用户基础信息url
    public static final String QUERY_USER_DATA_URL = "https://api.github.com/search/users?q=";
    // 查询用户repos信息url
    public static final String QUERY_USER_REPOS_URL = "https://api.github.com/users/";
    // 查询用户repos信息url追加部分
    public static final String REPOS = "/repos";
    // OAuth鉴权
    public static final String OAUTH = "?client_id=f5b9434bc3439b2ad595&client_secret=53c78c7e0f2b4fb1098915d53a53117b6a854c54";
    // 查询关键字不能为空提示
    public static final String QUERY_KEYWORD_NULL_ERROR = "查询关键字不能为空";
    // 网络无连接提示
    public static final String QUERY_NET_ERROR = "请检查网络连接";
    // 查询成功提示
    public static final String QUERY_SUCCESS = "查询成功";
    // 查询失败提示
    public static final String QUERY_FAILURE = "查询失败";
    // 查询无数据提示
    public static final String QUERY_NO_DATA = "未查到关键字相关的用户信息";
    // 用户编程语言统计起始数值
    public static final int LANGUAGE_COUNT_BEGIN = 1;
    // 用户编程语言统计增量
    public static final int LANGUAGE_COUNT_ADD = 1;
    // 连接超时时间（秒）
    public static final int QUERY_TIMEOUT = 10;
    // repos接口数据为空时显示的编程语言偏好
    public static final String QUERY_REPOS_NULL = "暂无";
}
