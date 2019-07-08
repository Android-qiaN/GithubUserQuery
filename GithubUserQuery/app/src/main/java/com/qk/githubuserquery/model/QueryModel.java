package com.qk.githubuserquery.model;

import com.qk.githubuserquery.bean.UserBean;

import java.util.List;

/**
 * @描述: Model层接口
 * @包名: com.qk.githubuserquery.model
 * @类名: QueryModel
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public interface QueryModel {
    //获取用户信息（设计一个回调接口，避免ANR）
    void queryUserData(String inputStr,UserDataOnLoadListener userDataOnLoadListener);

    //回调接口
    interface UserDataOnLoadListener {
        void onComplete(List<UserBean> users);
    }
}
