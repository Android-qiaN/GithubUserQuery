package com.qk.githubuserquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qk.githubuserquery.R;
import com.qk.githubuserquery.bean.UserBean;

import java.util.List;

/**
 * @描述: 查询结果列表适配器
 * @包名: com.qk.githubuserquery.adapter
 * @类名: QueryAdapter
 * @日期: 2019/7/6
 * @作者: QianKun
 */
public class QueryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserBean> mUsers;

    public QueryAdapter(Context context, List<UserBean> users) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mUsers = users;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return mUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.listview_item, null);
        }
        TextView name = view.findViewById(R.id.item_name);
        ImageView iconView = view.findViewById(R.id.item_icon);
        TextView language = view.findViewById(R.id.item_language);

        name.setText(mUsers.get(i).getName());
        String iconUrl = mUsers.get(i).getIconUrl();
        //使用Glide图片加载框架
        Glide.with(mContext).load(iconUrl)
                .override(100, 100)
                .placeholder(R.drawable.loading)
                .into(iconView);
        language.setText(mUsers.get(i).getLanguage());

        return view;
    }

}
