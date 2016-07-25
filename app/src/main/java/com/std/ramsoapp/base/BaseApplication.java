package com.std.ramsoapp.base;

import android.app.Application;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * Created by Maik on 2016/1/20.
 */
public class BaseApplication extends Application {
    //获取到主线程的上下文
    private static BaseApplication mContext;
    //获取到主线程的handler
    private static Handler mMainThreadHanler;
    //获取到主线程
    private static Thread mMainThread;
    //获取到主线程的id
    private static int mMainThreadId;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = this;
        mMainThreadHanler = new Handler();
        mMainThread = Thread.currentThread();
        //获取到调用线程的id
        mMainThreadId = android.os.Process.myTid();

        Fresco.initialize(this);

        Logger.init();
    }

    public static BaseApplication getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHanler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }
}
