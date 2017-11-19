package com.csming1995.statuslayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csm on 2017/7/4.
 */

/**
 * @author csm
 * RecyclerView的Adapter
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolderDemo>{

    private List<String> mListDemo;
    private Context mContext;

    public DemoAdapter(Context context, ArrayList<String> mDataDemo){
        mContext = context;
        mListDemo = mDataDemo;
    }


    @Override
    public ViewHolderDemo onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new DemoAdapter.ViewHolderDemo(view);
    }

    @Override
    public void onBindViewHolder(DemoAdapter.ViewHolderDemo holder, int position){
        holder.tvDemo.setText(mListDemo.get(position));
    }

    @Override
    public int getItemCount() {
        return mListDemo.size();
    }

    class ViewHolderDemo extends RecyclerView.ViewHolder {
        TextView tvDemo;

        public ViewHolderDemo(View itemView) {
            super(itemView);
            tvDemo = (TextView)itemView;
        }
    }
}