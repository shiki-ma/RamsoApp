package com.std.ramsoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;

/**
 * Created by Maik on 2016/3/14.
 */
public class TaskScanActivity extends CaptureActivity {
    private String assetId;
    private String taskId;
    private TaskScanMethod execMethod;

    public enum TaskScanMethod {
        TASK_SCAN_WITH_CODE, TASK_SCAN_WITH_NO_CODE
    }

    @Override
    protected void processCode(String codeText) {
        if (execMethod == TaskScanMethod.TASK_SCAN_WITH_NO_CODE) {
            Intent intent = new Intent(TaskScanActivity.this, AssetListActivity.class);
            Bundle taskBundle = new Bundle();
            taskBundle.putString("assetId", codeText);
            intent.putExtras(taskBundle);
            startActivity(intent);
        } else if ((execMethod == TaskScanMethod.TASK_SCAN_WITH_CODE && assetId.equals(codeText))) {
            Intent intent = new Intent(TaskScanActivity.this, TaskExecuteActivity.class);
            Bundle taskBundle = new Bundle();
            taskBundle.putString("taskId", taskId);
            intent.putExtras(taskBundle);
            startActivityForResult(intent, Constant.TASK_RESULT_STATE);
        } else {
            Toast.makeText(this, getString(R.string.scan_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initData() {
        Bundle taskBundle = this.getIntent().getExtras();
        if (taskBundle == null) {
            execMethod = TaskScanMethod.TASK_SCAN_WITH_NO_CODE;
        } else {
            execMethod = TaskScanMethod.TASK_SCAN_WITH_CODE;
            taskId = taskBundle.getString("taskId");
            assetId = taskBundle.getString("assetId");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(Constant.TASK_RESULT_STATE);
        finish();
    }
}
