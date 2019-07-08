package com.qk.githubuserquery.bean;

/**
 * @描述: Github用户信息
 * @包名: com.qk.githubuserquery.bean
 * @类名: UserBean
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public class UserBean {
    //用户名
    private String name;
    //用户头像url
    private String iconUrl;
    //用户编程语言偏好
    private String language;

    public UserBean(String name, String iconUrl, String language) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
