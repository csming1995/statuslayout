# StatusLayout

A custom Layout to switch status；

# Screenshot

![screenshot](https://raw.githubusercontent.com/csming1995/statuslayout/master/staatuslayout.gif)

## Add dependency
### **Step 1.** Add the JitPack repository to your build file
### Add it in your root build.gradle at the end of repositories:   

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

### **Step 2.** Add the dependency
```gradle
dependencies {
	compile 'com.github.csming1995:statuslayout:1.0-1beta'
}
```

## How to use
### Activity
```java
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
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:tools="http://schemas.android.com/tools"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              xmlns:app="http://schemas.android.com/apk/res-auto"
              android:orientation="vertical">
  ...

    <com.csm.Component.StatusLayout
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
    </com.csm.Component.StatusLayout>
</LinearLayout>

```