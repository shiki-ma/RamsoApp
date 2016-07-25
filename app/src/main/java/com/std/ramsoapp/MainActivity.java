package com.std.ramsoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.fragments.BoxFragment;
import com.std.ramsoapp.fragments.BusinessFragment;
import com.std.ramsoapp.fragments.CustomerFragment;
import com.std.ramsoapp.fragments.MyFragment;
import com.std.ramsoapp.view.ContentViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ContentViewPager mContentPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> content_list = null;
    private ActionBar actionBar;

    @Override
    protected void preData() {
        content_list = new ArrayList<>();
        content_list.add(new BusinessFragment());
        content_list.add(new CustomerFragment());
        content_list.add(new MyFragment());
        content_list.add(new BoxFragment());
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        Bundle menuBundle = this.getIntent().getExtras();
        int menuId = menuBundle.getInt("menuId");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mContentPager = (ContentViewPager) findViewById(R.id.vp_main);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_tabbar);

        mContentPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return content_list.get(position);
            }

            @Override
            public int getCount() {
                return content_list.size();
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_business:
                        setTitle("业务");
                        mContentPager.setCurrentItem(0);
                        break;
                    case R.id.rb_customer:
                        setTitle("客户");
                        mContentPager.setCurrentItem(1);
                        break;
                    case R.id.rb_profile:
                        setTitle("我的");
                        mContentPager.setCurrentItem(2);
                        break;
                    case R.id.rb_box:
                        setTitle("百宝箱");
                        mContentPager.setCurrentItem(3);
                        break;
                }
            }
        });

        setTabBarCheck(menuId);
    }

    private void setTabBarCheck(int menuId) {
        switch (menuId) {
            case 0:
                mRadioGroup.check(R.id.rb_business);
                break;
            case 1:
                mRadioGroup.check(R.id.rb_customer);
                break;
            case 2:
                mRadioGroup.check(R.id.rb_profile);
                break;
            case 3:
                mRadioGroup.check(R.id.rb_box);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
        }
        return false;
    }
}
