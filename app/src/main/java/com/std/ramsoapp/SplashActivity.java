package com.std.ramsoapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.FileCallback;
import com.shiki.okttp.callback.StringCallback;
import com.shiki.utils.ApkUtils;
import com.shiki.utils.DeviceUtils;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.domain.VersionInfo;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

/**
 * Created by Maik on 2016/1/27.
 */
public class SplashActivity extends BaseActivity {
    private TextView tvVersion;
    private RelativeLayout rlRoot;
    private VersionInfo versionInfo;
    private Timer timer;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        findViews();
        tvVersion.setText("版本号：" + ApkUtils.getVersionName(this));
        AlphaAnimation anim = new AlphaAnimation(0.2f, 1);
        anim.setDuration(2000);
        rlRoot.startAnimation(anim);
        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                checkVersion();
            }
        };
        timer.schedule(task, 2000);
    }

    private void checkVersion() {
        OkHttpUtils
                .get()
                .url(Constant.URL_UPDATE)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(SplashActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                        enterHome();
                    }

                    @Override
                    public void onResponse(String response) {
                        versionInfo = new Gson().fromJson(response, VersionInfo.class);
                        if (versionInfo == null) {
                            Toast.makeText(SplashActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
                            enterHome();
                        } else {
                            if (ApkUtils.getVersionCode(SplashActivity.this) < versionInfo.getVersionCode()) {
                                showUpdateDialog();
                            } else {
                                enterHome();
                            }
                        }
                    }
                });
    }

    private void findViews() {
        tvVersion = (TextView) findViewById(R.id.tv_version);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
    }

    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本:" + versionInfo.getVersionName());
        builder.setMessage(versionInfo.getVersionDesc());
        builder.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadAPK();
                    }
                });
        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enterHome();
                    }
                });
        builder.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.show();
    }

    private void downloadAPK() {
        final ProgressDialog dialog = new ProgressDialog(SplashActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle(getString(R.string.downloading));
        dialog.setMessage(getString(R.string.waiting));
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();

        if (DeviceUtils.existSDCard() && !StringUtils.isEmpty(versionInfo.getVersionUrl())) {
            OkHttpUtils.get().url(versionInfo.getVersionUrl()).build().execute(new FileCallback(Environment.getExternalStorageDirectory().getAbsolutePath(), Constant.APK_NAME) {

                @Override
                public void inProgress(float progress) {
                    dialog.setProgress(Math.round(progress * 100));
                }

                @Override
                public void onError(Call call, Exception e) {
                    dialog.dismiss();
                    Toast.makeText(SplashActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    enterHome();
                }

                @Override
                public void onResponse(File response) {
                    dialog.dismiss();
                    ApkUtils.install(SplashActivity.this, response);
                }
            });
        }
    }

    private void enterHome() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }
}
