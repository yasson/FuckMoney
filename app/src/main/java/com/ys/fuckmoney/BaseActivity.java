package com.ys.fuckmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ys.fuckmoney.view.ViewHelper;

/**
 * Created by senyang on 2017/1/3.
 */

public class BaseActivity extends AppCompatActivity {
    protected int mSw = 0;
    protected int mSh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView(savedInstanceState));
        initView();
    }

    protected int getContentView(Bundle savedInstanceState) {
        return 0;
    }

    protected void initView() {
        mSw = ViewHelper.getScreenWidth(this);
        mSh = ViewHelper.getScreenHeight(this);
    }

    protected <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

}
