package com.std.ramsoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.orhanobut.logger.Logger;
import com.shiki.utils.StringUtils;
import com.shiki.utils.coder.MD5Coder;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.domain.ROResult;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.ROResultCallback;

import net.grandcentrix.tray.AppPreferences;

import okhttp3.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btnLogin;
    private ProgressDialog dialog;
    private EditText etUserId;
    private EditText etPasswd;
    private SwitchButton sbRemember;
    private AppPreferences appPreferences;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        findViews();
        setListener();
        showUserAndPasswd();
    }

    private void showUserAndPasswd() {
        appPreferences = new AppPreferences(this);
        String strUserId = appPreferences.getString("userName", "");
        String strPasswd = appPreferences.getString("passwd", "");
        int isRemember = appPreferences.getInt("isRemember", 0);
        etUserId.setText(strUserId);
        if (isRemember == 1) {
            etPasswd.setText(strPasswd);
            sbRemember.setChecked(true);
        }
    }

    private void findViews() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        dialog = new ProgressDialog(this);
        etUserId = (EditText) findViewById(R.id.et_account);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        sbRemember = (SwitchButton) findViewById(R.id.sb_remember);
    }

    private void setListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String strUserId = etUserId.getText().toString().trim();
                String strPasswd = etPasswd.getText().toString().trim();
                checkLogin(strUserId, strPasswd);
        }
    }

    private void checkLogin(final String userName, final String passwd) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passwd)) {
            Toast.makeText(this, R.string.username_passwd_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage(this.getString(R.string.logining));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        OkHttpUtils.get()
                .url(Constant.URL_REGISTER).addParams("userCode", userName).addParams("userPasswd", MD5Coder.getMD5Code(passwd).toUpperCase()).build()
                .execute(new ROResultCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        Logger.d(e.getMessage());
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ROResult response) {
                        if (response.getStatusCode().equals(Constant.RES_SUCCESS)) {
                            appPreferences.put("userName", userName);
                            appPreferences.put("passwd", passwd);
                            appPreferences.put("isRemember", sbRemember.isChecked() ? 1 : 0);
                            dialog.dismiss();
                            // 进入主页面
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                            finish();
                            overridePendingTransition(R.anim.left_to_right_in, R.anim.left_to_right_out);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, response.getStatusMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
