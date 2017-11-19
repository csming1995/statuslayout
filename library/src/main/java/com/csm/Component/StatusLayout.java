package com.csm.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csm.Exception.IllegalNumException;
import com.csm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csm on 2017/7/4.
 */
public class StatusLayout extends FrameLayout{
    private static final String TAG = "StatusLayout.FrameLayout";


    /**
     * DEFAULT EMPTY NET_ERROR 默认的三种状态
     * DEFAULT 为用户第一次使用该组件时指定的属性状态
     */
    private static final int DEFAULT = 1;
    private static final int EMPTY = 2;
    private static final int NET_ERROR = 3;
    //rivate static final int LOADING = 3;


    /**
     * 属性值
     */
    private String mInitMessage;
    private Drawable mInitImage;
    private String mInitStrInBtn;

    /**
     * Map 用键值对存储 状态-视图
     * List 用于存储子控件,即内容
     */
    private Map<Integer, View> mMapMessageViews;
    private List<View> mNormalViews;

    private LayoutInflater mLayoutInflater;


    /**
     * 默认页
     * 空数据页
     * 网络错误页
     */
    private LinearLayout mDefaultView;//默认页
    private LinearLayout mDefaultEmptyMessageView;
    private LinearLayout mDefaultNetErrorView;

    private Context mContext;

    public StatusLayout(Context context){
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


    /**
     * 一些初始化工作
     * 初始化DefaultView
     */

    private void init(AttributeSet attrs){
        if (null == mNormalViews) mNormalViews = new ArrayList<>();

        if (null == mMapMessageViews) mMapMessageViews = new HashMap<>();

        if (null == mLayoutInflater){
            mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        TypedArray mValueArray = mContext.obtainStyledAttributes(attrs, R.styleable.StatusLayoutValue);

        mInitMessage = mValueArray.getString(R.styleable.StatusLayoutValue_attr_message);
        mInitImage = mValueArray.getDrawable(R.styleable.StatusLayoutValue_attr_image_src);
        mInitStrInBtn = mValueArray.getString(R.styleable.StatusLayoutValue_attr_str_btn);

        setEmptyMessageView();
        setNetErrorMessageView();
        setDefaultView(mInitMessage, mInitImage, mInitStrInBtn);
        mValueArray.recycle();

    }

    /**
     * 加载完布局后 使默认视图显示
     */
    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        showDefaultView();
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过addView函数在被调用时,对child View进行初始化
     * 获取子控件信息
     * @param child
     * @param params
     */
    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        for(int i = 0; i < getChildCount(); i ++){
            mNormalViews.add(getChildAt(i));
        }
    }

    public void showDefaultView(){
        showStatusView(DEFAULT);
    }

    public void showEmptyMessageView(){
        showStatusView(EMPTY);
    }

    public void showNetErrorView(){
        showStatusView(NET_ERROR);
    }

    /**
     * 设置为有数据状态
     * 使当前View的子View显示
     * 子View为RecyclerView
     * @see #setContentView(boolean)
     */
    public void showNormalView(){
        hiddenStatusViews();
        setContentView(true);
    }

    /**
     * 设置子View的显示或隐藏状态
     * 子View存储于一个list中
     * @param isShown
     */
    private void setContentView(boolean isShown){
        if (isShown){
            for (View v : mNormalViews){
                v.setVisibility(VISIBLE);
            }
        }else {
            for (View v : mNormalViews){
                v.setVisibility(GONE);
            }
        }
    }

    /**
     * 无参调用的设置网络错误页
     * 用于内部调用
     */
    private void setNetErrorMessageView(){
        if (null == mDefaultNetErrorView){
            mDefaultNetErrorView = (LinearLayout)mLayoutInflater.inflate(R.layout.status_layout_net_error_message, null);
        }
        mMapMessageViews.put(NET_ERROR, mDefaultNetErrorView);
    }

    /**
     * 有参调用的设置网络错误页
     * 提供给外部使用者
     * @param message
     * @param image
     */
    public void setNetErrorMessageView(String message, Drawable image){
        if(null == mDefaultNetErrorView){
            mDefaultNetErrorView = (LinearLayout)mLayoutInflater.inflate(R.layout.status_layout_net_error_message, null);
        }else {
            mDefaultNetErrorView = (LinearLayout)mMapMessageViews.get(NET_ERROR);
        }

        TextView mTvNetError = (TextView)mDefaultNetErrorView.findViewById(R.id.tv_net_error_view);
        ImageView mIvNetError = (ImageView)mDefaultNetErrorView.findViewById(R.id.iv_net_error_view);
        if (null != message){
            mTvNetError.setText(message);
            mTvNetError.setVisibility(VISIBLE);
        }else {
            mTvNetError.setVisibility(GONE);
        }
        if (null != image){
            mIvNetError.setImageDrawable(image);
            mIvNetError.setVisibility(VISIBLE);
        }else {
            mIvNetError.setVisibility(GONE);
        }

        mMapMessageViews.put(NET_ERROR, mDefaultNetErrorView);
    }

    /**
     * 无参调用空数据页面
     * 用于内部调用
     */

    private void setEmptyMessageView(){
        if (null == mDefaultEmptyMessageView) {
            mDefaultEmptyMessageView = (LinearLayout)mLayoutInflater.inflate(R.layout.status_layout_empty_message, null);
        }
        mMapMessageViews.put(EMPTY, mDefaultEmptyMessageView);
    }

    public void setEmptyMessageView(int messageId, int imageId, int messageInBtnId){
        String messageInBtn = mContext.getString(messageInBtnId);
        setEmptyMessageView(messageId, imageId, messageInBtn);
    }

    public void setEmptyMessageView(int messageId, int imageId, String messageInBtn){
        Drawable image = ContextCompat.getDrawable(mContext, imageId);
        setEmptyMessageView(messageId, image, messageInBtn);
    }

    public void setEmptyMessageView(int messageId, Drawable image, String messageInBtn){
        String message = mContext.getString(messageId);
        setEmptyMessageView(message, image, messageInBtn);
    }

    /**
     * 有参调用设置空数据页
     * 提供给外部使用者
     * @param message
     * @param image
     * @param messageInBtn
     */

    public void setEmptyMessageView(String message, Drawable image, String messageInBtn){
        if(null == mDefaultEmptyMessageView){
            mDefaultEmptyMessageView = (LinearLayout)mLayoutInflater.inflate(R.layout.status_layout_empty_message, null);
        }else {
            mDefaultEmptyMessageView = (LinearLayout)mMapMessageViews.get(EMPTY);
        }

        TextView mTvEmpty = (TextView)mDefaultEmptyMessageView.findViewById(R.id.tv_empty_view);
        ImageView mIvEmpty = (ImageView)mDefaultEmptyMessageView.findViewById(R.id.iv_empty_view);
        Button mBtnEmpty = (Button)mDefaultEmptyMessageView.findViewById(R.id.btn_empty_view);
        if (null != message){
            mTvEmpty.setText(message);
            mTvEmpty.setVisibility(VISIBLE);
        }else {
            mTvEmpty.setVisibility(GONE);
        }
        if (null != image){
            mIvEmpty.setImageDrawable(image);
            mIvEmpty.setVisibility(VISIBLE);
        }else {
            mIvEmpty.setVisibility(GONE);
        }
        if (null != messageInBtn) {
            mBtnEmpty.setText(message);
            mBtnEmpty.setVisibility(VISIBLE);
        }else {
            mBtnEmpty.setVisibility(GONE);
        }
        mMapMessageViews.put(EMPTY, mDefaultEmptyMessageView);
    }
    /**
     * 有参调用 设置默认页
     * 用于内部使用
     * @param message
     * @param image
     * @param messageInBtn
     */
    private void setDefaultView(String message, Drawable image, String messageInBtn){
        if(null == mDefaultView){
            mDefaultView = (LinearLayout)mLayoutInflater.inflate(R.layout.status_layout_default_message, null);
        }else {
            mDefaultView = (LinearLayout)mMapMessageViews.get(DEFAULT);
        }

        TextView mTvDefault = (TextView)mDefaultView.findViewById(R.id.tv_default_view);
        ImageView mIvDefault = (ImageView)mDefaultView.findViewById(R.id.iv_default_view);
        Button mBtnDefault = (Button)mDefaultView.findViewById(R.id.btn_default_view);
        if (null != message){
            mTvDefault.setText(message);
            mTvDefault.setVisibility(VISIBLE);
        }else {
            mTvDefault.setVisibility(GONE);
        }
        if (null != image){
            mIvDefault.setImageDrawable(image);
            mIvDefault.setVisibility(VISIBLE);
        }else {
            mIvDefault.setVisibility(GONE);
        }
        if (null != messageInBtn) {
            mBtnDefault.setText(message);
            mBtnDefault.setVisibility(VISIBLE);
        }else {
            mBtnDefault.setVisibility(GONE);
        }
        mMapMessageViews.put(DEFAULT, mDefaultView);
    }

    /**
     * 外部添加状态
     * 若状态与已有状态碰撞
     * 跳出错误
     * @param key
     * @param view
     * @throws IllegalNumException
     */
    public void addStatus(int key, View view) throws IllegalNumException {
        if(1 == key||2 == key||3 == key) {
            throw new IllegalNumException();
        }
        mMapMessageViews.put(key, view);
    }

    /**
     * 显示指定状态页
     * 并将其他页面隐藏
     * 用于内部以及外部电泳
     * @param key
     */
    public void showStatusView(int key){
        setContentView(false);
        View mMessageView = mMapMessageViews.get(key);
        hiddenStatusViews();
        addView(mMessageView);
        mMessageView.setVisibility(VISIBLE);
    }

    /**
     * 隐藏mMapMessageViews的所有页面
     */

    private void hiddenStatusViews(){
        for (View v : mMapMessageViews.values()){
            removeView(v);
        }
    }

}
