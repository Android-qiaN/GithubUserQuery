package com.qk.githubuserquery.model;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qk.githubuserquery.bean.UserBean;
import com.qk.githubuserquery.utils.Constant;
import com.qk.githubuserquery.utils.QueryUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

;

/**
 * @描述: Model层实现
 * @包名: com.qk.githubuserquery.model
 * @类名: QueryModelImpl
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public class QueryModelImpl implements QueryModel {

    private static String TAG = "=====QueryModelImpl=====";
    //统计用户个数
    private int mCount;

    @Override
    public void queryUserData(final String inputStr, final UserDataOnLoadListener userDataOnLoadListener) {
        //用于存放查询到的所有用户主要信息
        final List<UserBean> users = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(Constant.QUERY_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(Constant.QUERY_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                    /**
                     * 获取查询关键词后的用户信息
                     */
                    Request queryUsersReq = new Request.Builder().url(Constant.QUERY_USER_DATA_URL + inputStr).build();
                    Response queryUsersResp = okHttpClient.newCall(queryUsersReq).execute();
                    if (queryUsersResp.isSuccessful()) {
                        JSONObject resultObject = JSONObject.parseObject(queryUsersResp.body().string());
                        final JSONArray items = (JSONArray) resultObject.get("items");
                        if (items == null) {
                            userDataOnLoadListener.onComplete(null);
                        } else if (items.size() == 0) {
                            userDataOnLoadListener.onComplete(users);
                        }else {
                            for (int i = 0; i < items.size(); i++) {
                                JSONObject usersObject = items.getJSONObject(i);
                                //获取用户名
                                String name = usersObject.getString("login");
                                //用户名匹配
                                if (name.toLowerCase().contains(inputStr.toLowerCase())) {
                                    String iconUrl = usersObject.getString("avatar_url");
                                    UserBean userData = new UserBean(name, iconUrl, null);
                                    users.add(userData);
                                }
                            }
                        }
                    }

                    /**
                     * 获取用户使用频率最高的语言
                     */
                    mCount = 0;
                    for (final UserBean user : users) {
                        //用于存放用户编程语言使用频率
                        final Map<String, Integer> languageMap = new HashMap<>();
                        //获取用户repos
                        Request queryRepoReq = new Request.Builder().url(Constant.QUERY_USER_REPOS_URL + user.getName() + Constant.REPOS + Constant.OAUTH).build();
                        Call call = okHttpClient.newCall(queryRepoReq);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i(TAG, "okhttp异步回调失败");
                                userDataOnLoadListener.onComplete(null);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    String language = null;
                                    String resultStr = response.body().string();
                                    JSONArray resultArray = JSONArray.parseArray(resultStr);
                                    if (resultArray.size() == 0) {
                                        language = Constant.QUERY_REPOS_NULL;
                                        user.setLanguage(language);
                                    }else {
                                        for (int i = 0; i < resultArray.size(); i++) {
                                            //获取用户每个项目中主体编程语言
                                            language = resultArray.getJSONObject(i).getString("language");
                                            if (language == null) {
                                                continue;
                                            } else {
                                                //统计用户项目主体编程语言使用次数
                                                if (languageMap != null) {
                                                    if (!languageMap.containsKey(language)) {
                                                        languageMap.put(language, Constant.LANGUAGE_COUNT_BEGIN);
                                                    } else {
                                                        int countTemp = languageMap.get(language);
                                                        languageMap.put(language, countTemp + Constant.LANGUAGE_COUNT_ADD);
                                                    }
                                                }
                                            }
                                        }
                                        //设置用户编程语言偏好
                                        user.setLanguage(QueryUtils.getLanguageMax(languageMap));
                                    }

                                    mCount++;
                                    //所有用户编程语言偏好获取完成后回调
                                    if (mCount == users.size()) {
                                        userDataOnLoadListener.onComplete(users);
                                    }
                                }
                            }
                        });

                    }


                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {//判断超时异常
                        Log.i(TAG, "连接超时");
                        userDataOnLoadListener.onComplete(null);
                    }
                    if (e instanceof ConnectException) {//判断连接异常
                        Log.i(TAG, "连接异常");
                        userDataOnLoadListener.onComplete(null);
                    }
                }


            }
        });

        thread.start();
    }
}
