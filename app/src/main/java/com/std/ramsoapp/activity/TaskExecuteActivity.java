package com.std.ramsoapp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.fragments.TaskDetailFragment;
import com.std.ramsoapp.fragments.TaskRequireFragment;
import com.std.ramsoapp.fragments.TaskUploadFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/3/15.
 */
public class TaskExecuteActivity extends BaseActivity {
    private static final String[] TITLE = new String[]{"设备信息", "图片上传", "任务要求"};
    private ViewPager mTaskPager;
    private TabPageIndicator mTaskIndicator;
    private List<Fragment> content_list;
    private String taskId;
    private ActionBar actionBar;

    @Override
    protected void preData() {
        Bundle taskBundle = this.getIntent().getExtras();
        taskId = taskBundle.getString("taskId");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        content_list = new ArrayList<>();
        content_list.add(TaskDetailFragment.newInstance(taskId, "1"));
        content_list.add(TaskUploadFragment.newInstance(taskId));
        content_list.add(TaskRequireFragment.newInstance(taskId));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_task_execute);
        findViews();
        setAdapter();
        mTaskPager.setOffscreenPageLimit(2);
        mTaskIndicator.setVisibility(View.VISIBLE);
        mTaskIndicator.setViewPager(mTaskPager);
    }

    private void setAdapter() {
        mTaskPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

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
        mTaskPager = (ViewPager) findViewById(R.id.task_pager);
        mTaskIndicator = (TabPageIndicator) findViewById(R.id.task_indicator);
    }
}
