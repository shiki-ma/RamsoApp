package com.std.ramsoapp.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.fragments.DeviceListFragment;

/**
 * Created by Maik on 2016/4/5.
 */
public class DeviceActivity extends BaseActivity {
    private ActionBar actionBar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_device);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadFragment(new DeviceListFragment());
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_device, fragment);
        ft.commit();
    }

    public void setActionText(String actionText) {
        actionBar.setTitle(actionText);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
