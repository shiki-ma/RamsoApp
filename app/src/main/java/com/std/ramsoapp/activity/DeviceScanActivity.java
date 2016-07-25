package com.std.ramsoapp.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.client.android.CaptureActivity;
import com.std.ramsoapp.Constant;

/**
 * Created by Maik on 2016/4/6.
 */
public class DeviceScanActivity extends CaptureActivity {

    @Override
    protected void processCode(String codeText) {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", codeText);
        data.putExtras(bundle);
        setResult(Constant.DEVICE_RESULT_STATE, data);
        finish();
    }

    @Override
    protected void initData() {

    }
}
