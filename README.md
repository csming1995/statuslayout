# StatusLayout

用于状态切换的Layout控件

* **初始支持少量状态**

  ​

* **使用者可以自定义特有的状态以及对应的View**

  ​

* 步骤：

  1.Add it in your root build.gradle at the end of repositories:

  	allprojects {
  		repositories {
  			maven { url 'https://jitpack.io' }
  		}
  	}
  ​

  2.Add the dependency

  ```
  dependencies {
  	        compile 'com.github.csming1995:StatusLayoutDemoProject:0.5'
  	}
  ```

  eg：（懒得写文档）

  ```
  <com.example.meitu.test.Component.StatusLayout
      android:id="@+id/statuslayout_demo"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:attr_message="@string/str_there_has_nothing"
      app:attr_image_src="@mipmap/ic_launcher">
      <android.support.v7.widget.RecyclerView
          android:id="@+id/rv_demo"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:src="@mipmap/ic_launcher_round"/>
  </com.example.meitu.test.Component.StatusLayout>
  ```

```
package com.example.meitu.test.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.meitu.test.Adapter.DemoAdapter;
import com.example.meitu.test.Component.StatusLayout;
import com.example.meitu.test.R;

import java.util.ArrayList;


/**
 * Created by meitu on 2017/7/4.
 */

public class DemoActivity extends AppCompatActivity {


    /**
     * 开关
     */
    Switch mSwitchIsNomal;
    Switch mSwitchNetNotWorked;
    Switch mSwitchEmpty;

    /**
     * StatusLayout，状态切换布局
     */
    StatusLayout mStatusLayoutDemo;
    View mCustMessageView;

    /**
     * mRcvDemo 内容列表
     * mDemoAdapter 适配器
     * mDataDemo 数据
     */
    RecyclerView mRvDemo;
    DemoAdapter mDemoAdapter;
    ArrayList<String> mDataDemo = new ArrayList<String>();

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
```