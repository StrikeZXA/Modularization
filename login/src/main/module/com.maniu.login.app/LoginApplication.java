package com.maniu.login.app;

import android.util.Log;

import com.maniu.base.BaseApplication;

/**
 * @author WEN
 * @Description:
 * @date 2020/9/19 22:12
 */
public class LoginApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: "+"LoginApplication");
    }
}
