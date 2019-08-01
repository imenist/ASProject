package com.example.lenovo.translate;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2019/7/7.
 */

public class ListviewAdapter extends BaseAdapter {
    private List<String> mStringList;
    private Context mContext;

    public ListviewAdapter(Context mContext, List<String> mStringList) {
        this.mContext = mContext;
        this.mStringList = mStringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder mMyViewHolder = null;
        if(view == null){
            mMyViewHolder = new MyViewHolder();
            view = View.inflate(mContext,R.layout.listview_item,null);
            mMyViewHolder.item_tv = (TextView) view.findViewById(R.id.item_tv);
            view.setTag(mMyViewHolder);
        }else {
            mMyViewHolder = (MyViewHolder)view.getTag();
        }
        mMyViewHolder.item_tv.setText(mStringList.get(i));
        return view;
    }

    private final class MyViewHolder{
        TextView item_tv;
    }
}

