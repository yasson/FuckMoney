package com.ys.fuckmoney;

import android.app.Application;

import com.ys.fuckmoney.core.ApplicationContextGetter;
import com.ys.fuckmoney.utils.T;

public class FuckMoneyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContextGetter.getInstance().init(this);
        T.init();
    }
}
