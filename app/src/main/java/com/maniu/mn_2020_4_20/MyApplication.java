package com.maniu.mn_2020_4_20;

import android.app.Application;

import com.maniu.arouter.ARouter;

/**
 * @author WEN
 * @Description:
 * @date 2020/9/18 23:41
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
