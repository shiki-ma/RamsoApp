package com.shiki.imgpicker;

import android.content.Context;
import android.content.Intent;

import com.shiki.imgpicker.domain.PhotoInfo;

import java.util.List;

/**
 * Created by Maik on 2016/2/25.
 */
public class FGOGallery {
    private static OnHanlderResultCallback mCallback;
    private static FunctionConfig mCurrentFunctionConfig;
    private static Context mContext;

    public static OnHanlderResultCallback getCallback() {
        return mCallback;
    }

    public static FunctionConfig getCurrentFunctionConfig() {
        return mCurrentFunctionConfig;
    }

    public static void openGallery(Context context, FunctionConfig config, OnHanlderResultCallback callback) {
        mContext = context;
        mCurrentFunctionConfig = config;
        mCallback = callback;
        Intent intent = new Intent(mContext, PhotoSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 处理结果
     */
    public static interface OnHanlderResultCallback {

        public void onHanlderSuccess(List<PhotoInfo> resultList);

        public void onHanlderFailure(String errorMsg);
    }
}
