package com.std.ramsoapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.StringCallback;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.domain.FaultInfo;
import com.std.ramsoapp.fragments.FaultAuditFragment;
import com.std.ramsoapp.fragments.FaultDealFragment;
import com.std.ramsoapp.fragments.FaultDetailFragment;
import com.viewpagerindicator.TabPageIndicator;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Maik on 2016/3/15.
 */
public class FaultDealActivity extends BaseActivity {
    private static final String[] TITLE = new String[]{"受理情况", "信息填写", "审核情况"};
    private ViewPager mFaultPager;
    private TabPageIndicator mFaultIndicator;
    private List<Fragment> content_list;
    private String faultId;
    private ActionBar actionBar;
    private TextView tvHour;
    private TextView tvMin;
    private TextView tvSec;
    private ProgressDialog dialog;
    private String userId;

    @Override
    protected void preData() {
        Bundle faultBundle = this.getIntent().getExtras();
        faultId = faultBundle.getString("faultId");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        AppPreferences appPreferences = new AppPreferences(this);
        userId = appPreferences.getString("userName", "");
        getDataFromServer();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fault_deal);
        findViews();
    }

    private void getDataFromServer() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.waiting));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpUtils
                .get()
                .url(Constant.URL_FAULTDETAIL)
                .addParams("userCode", userId)
                .addParams("faultId", faultId)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        dialog.dismiss();
                        Toast.makeText(FaultDealActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        dealData(response);
                        dialog.dismiss();
                    }
                });
    }

    private void dealData(String jsonStr) {
        FaultInfo faultInfo = new Gson().fromJson(jsonStr, FaultInfo.class);
        if (faultInfo == null) {
            Toast.makeText(this, R.string.data_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (faultInfo.getStatusCode().equals(Constant.RES_FAILURE)) {
            Toast.makeText(this, faultInfo.getStatusMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        //更新UI
        showExpireDate(faultInfo.getNum() * 60 * 1000);
        content_list = new ArrayList<>();
        content_list.add(FaultDetailFragment.newInstance(faultInfo));
        content_list.add(FaultDealFragment.newInstance(faultInfo));
        content_list.add(FaultAuditFragment.newInstance(faultInfo));
        setAdapter();
        mFaultPager.setOffscreenPageLimit(2);
        mFaultIndicator.setVisibility(View.VISIBLE);
        mFaultIndicator.setViewPager(mFaultPager);
    }

    private void showExpireDate(long expire) {
        if (expire <= 0) {
            tvHour.setText("0");
            tvMin.setText("0");
            tvSec.setText("0");
        } else {
            new CountDownTimer(expire, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int hours = (int) ((millisUntilFinished / 1000) / 3600);
                    int minutes = (int) (((millisUntilFinished / 1000) - (hours * 3600)) / 60);
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);
                    tvHour.setText(String.valueOf(hours));
                    tvMin.setText(String.valueOf(minutes));
                    tvSec.setText(String.valueOf(seconds));
                }

                @Override
                public void onFinish() {
                    tvSec.setText("0");
                }
            }.start();
        }
    }

    private void setAdapter() {
        mFaultPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return TITLE.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLE[position % TITLE.length];
            }

            @Override
            public Fragment getItem(int position) {
                return content_list.get(position);
            }
        });
    }

    private void findViews() {
        mFaultPager = (ViewPager) findViewById(R.id.fault_pager);
        mFaultIndicator = (TabPageIndicator) findViewById(R.id.fault_indicator);
        tvHour = (TextView) findViewById(R.id.tv_countdown_hour);
        tvMin = (TextView) findViewById(R.id.tv_countdown_min);
        tvSec = (TextView) findViewById(R.id.tv_countdown_sec);
    }
}
