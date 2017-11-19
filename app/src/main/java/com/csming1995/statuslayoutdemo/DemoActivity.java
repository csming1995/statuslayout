package com.csming1995.statuslayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.csm.Component.StatusLayout;

import java.util.ArrayList;


/**
 * Created by csm on 2017/7/4.
 */

public class DemoActivity extends AppCompatActivity {


    /**
     * 开关
     */
    private Switch mSwitchIsNomal;
    private Switch mSwitchNetNotWorked;
    private Switch mSwitchEmpty;

    /**
     * StatusLayout，状态切换布局
     */
    private StatusLayout mStatusLayoutDemo;
    private View mCustMessageView;

    /**
     * mRcvDemo 内容列表
     * mDemoAdapter 适配器
     * mDataDemo 数据
     */
    private RecyclerView mRvDemo;
    private DemoAdapter mDemoAdapter;
    private ArrayList<String> mDataDemo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        initListData();
        mRvDemo = (RecyclerView)findViewById(R.id.rv_demo);
        mRvDemo.setLayoutManager(new LinearLayoutManager(DemoActivity.this));
        mDemoAdapter = new DemoAdapter(DemoActivity.this, mDataDemo);
        mRvDemo.setAdapter(mDemoAdapter);
        init();
    }

    /**
     * 初始化布局
     * 初始设置布局为空数据状态
     */
    void init(){
        mStatusLayoutDemo = (StatusLayout)findViewById(R.id.statuslayout_demo);

        //test
        //mCustMessageView = (View) getLayoutInflater().inflate(R.layout.test_layout, null);
        //try {
        //    mStatusLayoutDemo.addStatus(4, mCustMessageView);
        //}catch (IllegalNumException e){
        //    e.printStackTrace();
        //}

        mSwitchIsNomal = (Switch)findViewById(R.id.sw_nomal);
        mSwitchNetNotWorked = (Switch)findViewById(R.id.sw_is_net_worked);
        mSwitchEmpty = (Switch)findViewById(R.id.sw_has_data);

        mSwitchIsNomal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSwitchNetNotWorked.setChecked(false);
                    mSwitchEmpty.setChecked(false);
                    //mStatusLayoutDemo.showStatusView(4); //test
                    mStatusLayoutDemo.showNormalView();
                }else {

                }
            }
        });

        mSwitchNetNotWorked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSwitchIsNomal.setChecked(false);
                    mSwitchEmpty.setChecked(false);
                    mStatusLayoutDemo.showNetErrorView();
                }else {

                }
            }
        });

        mSwitchEmpty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mSwitchIsNomal.setChecked(false);
                    mSwitchNetNotWorked.setChecked(false);
                    mStatusLayoutDemo.showEmptyMessageView();
                }else {
                }
            }
        });
    }

    /**
     * 初始化列表内容
     */
    void initListData(){
        for (int i = 0; i < 30; i++){
            mDataDemo.add("Data NO." + i);
        }
    }


}
