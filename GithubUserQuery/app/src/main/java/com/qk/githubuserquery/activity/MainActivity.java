package com.qk.githubuserquery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qk.githubuserquery.R;
import com.qk.githubuserquery.adapter.QueryAdapter;
import com.qk.githubuserquery.bean.UserBean;
import com.qk.githubuserquery.presenter.QueryPresenter;
import com.qk.githubuserquery.utils.Constant;
import com.qk.githubuserquery.utils.QueryUtils;
import com.qk.githubuserquery.view.QueryView;

import java.util.List;

public class MainActivity extends Activity implements QueryView, View.OnClickListener {
    //查询框
    private EditText mInputText;
    //查询按钮
    private Button mQueryBtn;
    //查询结果列表
    private ListView mUsersListView;
    //查询等待进度条
    private ProgressBar mProgressBar;
    //Presenter层引用
    private QueryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化View
        initView();
        //获取presenter层
        mPresenter = new QueryPresenter(this);
    }

    /**
     * view初始化
     */
    private void initView() {
        mInputText = findViewById(R.id.et_input);
        mQueryBtn = findViewById(R.id.bt_query);
        mQueryBtn.setOnClickListener(this);
        mUsersListView = findViewById(R.id.lv_users);
        mProgressBar = findViewById(R.id.progressbar);
    }

    @Override
    public void showUserData(final List<UserBean> users) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersListView.setAdapter(new QueryAdapter(MainActivity.this, users));
                showToast(Constant.QUERY_SUCCESS);
            }
        });

    }

    @Override
    public void showUserDataNull(List<UserBean> users) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersListView.setAdapter(null);
                showToast(Constant.QUERY_NO_DATA);

            }
        });
    }

    @Override
    public void showUserDataError(List<UserBean> users) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersListView.setAdapter(null);
                showToast(Constant.QUERY_FAILURE);
            }
        });
    }

    @Override
    public void showProgress() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void hideProgress() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showNetError() {
        showToast(Constant.QUERY_NET_ERROR);
    }

    @Override
    public void showKeywordNullError() {
        showToast(Constant.QUERY_KEYWORD_NULL_ERROR);
    }

    @Override
    public void showListView() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersListView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideListView() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUsersListView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_query:
                //隐藏软键盘
                QueryUtils.hideSoftKeyboard(this);
                //执行UI逻辑
                mPresenter.query(this, mInputText.getText().toString());
                break;
            default:
                break;
        }

    }

    /**
     * 显示toast
     *
     * @param toastStr
     */
    private void showToast(String toastStr) {
        Toast.makeText(MainActivity.this, toastStr, Toast.LENGTH_SHORT).show();
    }
}
