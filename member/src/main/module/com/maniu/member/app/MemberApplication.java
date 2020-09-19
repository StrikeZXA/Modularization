package com.maniu.member.app;

import android.util.Log;

import com.maniu.base.BaseApplication;

/**
 * @author WEN
 * @Description:
 * @date 2020/9/19 22:21
 */
public class MemberApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: "+"MemberApplication");
    }
}
