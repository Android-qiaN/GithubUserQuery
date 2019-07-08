package com.qk.githubuserquery.view;

import com.qk.githubuserquery.bean.UserBean;

import java.util.List;

/**
 * @描述: View层接口
 * @包名: com.qk.githubuserquery.view
 * @类名: QueryView
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public interface QueryView {

    /**
     * 显示获取到的用户信息
     */
    void showUserData(List<UserBean> users);

    /**
     * 显示获取到用户信息为空时的状态
     */
    void showUserDataNull(List<UserBean> users);

    /**
     * 显示获取用户信息失败时的状态
     */
    void showUserDataError(List<UserBean> users);

    /**
     * 显示旋转进度条
     */
    void showProgress();

    /**
     * 隐藏旋转进度条
     */
    void hideProgress();

    /**
     * 提示网络无连接
     */
    void showNetError();

    /**
     * 提示查询关键字不能为空
     */
    void showKeywordNullError();

    /**
     * 显示listview
     */
    void showListView();

    /**
     * 隐藏listview
     */
    void hideListView();

}
