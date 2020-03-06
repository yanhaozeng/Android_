package com.yhz.android_frame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhz.android_frame.R;

import java.util.List;

/**
 * @description: MainAdapter (框架首页适配器)
 * @author: Y.hz
 * @time: 2020/03/02 10:05
 */
public class MainAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public MainAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainViewHolder holder = null;
        if (convertView == null){
            holder = new MainViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main,null);
            holder.itemMainTv = convertView.findViewById(R.id.itemMain_tv);
            convertView.setTag(holder);
        }else {
            holder = (MainViewHolder) convertView.getTag();
        }
        //赋值
        holder.itemMainTv.setText("");
        return convertView;
    }

    class MainViewHolder{
        TextView itemMainTv;
    }
}
