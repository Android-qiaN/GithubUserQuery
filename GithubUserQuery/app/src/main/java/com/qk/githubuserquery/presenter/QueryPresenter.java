package com.qk.githubuserquery.presenter;

import android.content.Context;

import com.qk.githubuserquery.bean.UserBean;
import com.qk.githubuserquery.model.QueryModel;
import com.qk.githubuserquery.model.QueryModelImpl;
import com.qk.githubuserquery.utils.QueryUtils;
import com.qk.githubuserquery.view.QueryView;

import java.util.List;

/**
 * @描述: Presenter层
 * @包名: com.qk.githubuserquery.presenter
 * @类名: QueryPresenter
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public class QueryPresenter {
    //View引用
    private QueryView mQueryView;
    //Model引用
    private QueryModel mQueryModel = new QueryModelImpl();

    public QueryPresenter(QueryView mQueryView) {
        this.mQueryView = mQueryView;
    }

    /**
     * 执行UI查询逻辑
     */
    public void query(Context context, String inputStr) {
        //判断输入框是否为空
        if ("".equals(inputStr)) {
            mQueryView.showKeywordNullError();
            return;
        }

        if (!QueryUtils.isNetworkConnected(context)) {
            mQueryView.showNetError();
        } else {
            if (mQueryView != null && mQueryModel != null) {
                mQueryView.hideListView();
                mQueryView.showProgress();
                mQueryModel.queryUserData(inputStr, new QueryModel.UserDataOnLoadListener() {
                    @Override
                    public void onComplete(List<UserBean> users) {
                        if (users == null) {//查询失败
                            mQueryView.showUserDataError(null);
                            mQueryView.hideProgress();
                            mQueryView.hideListView();
                        } else if (users.size() == 0) {//查询无数据
                            mQueryView.showUserDataNull(users);
                            mQueryView.hideProgress();
                            mQueryView.hideListView();
                        } else {//查询返回数据
                            mQueryView.showUserData(users);
                            mQueryView.hideProgress();
                            mQueryView.showListView();

                        }

                    }
                });
            }

        }
    }
}
