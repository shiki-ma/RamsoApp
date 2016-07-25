package com.std.ramsoapp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.fragments.TaskListFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/2/18.
 */
public class TaskListActivity extends BaseActivity {
    private static final String[] TITLE = new String[]{"待处理", "处理中", "待审核", "已审核"};
    private EditText etSearch;
    private ImageView ivSearch;
    private ViewPager mFaultPager;
    private TabPageIndicator mFaultIndicator;
    private List<Fragment> content_list;
    private ActionBar actionBar;

    @Override
    protected void preData() {
        content_list = new ArrayList<>();
        content_list.add(TaskListFragment.newInstance(Constant.TASK_BEFORE_DEAL));
        content_list.add(TaskListFragment.newInstance(Constant.TASK_DEAL));
        content_list.add(TaskListFragment.newInstance(Constant.TASK_BEFORE_REVIEW));
        content_list.add(TaskListFragment.newInstance(Constant.TASK_AFTER_REVIEW));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_list_main);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        findViews();
        setListener();
        setAdapter();
        mFaultIndicator.setVisibility(View.VISIBLE);
        mFaultIndicator.setViewPager(mFaultPager);
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

            /**
             @Override public int getItemPosition(Object object) {
             return POSITION_NONE;
             }

             @Override public Object instantiateItem(ViewGroup container, int position) {
             Fragment fragment = (Fragment) super.instantiateItem(container, position);
             String fragmentTag = fragment.getTag();
             if (content_update[position]) {
             FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
             ft.remove(fragment);
             fragment = content_list.get(position);
             fragment.setMenuVisibility(false);
             fragment.setUserVisibleHint(false);
             ft.add(container.getId(), fragment, fragmentTag);
             ft.attach(fragment);
             ft.commit();
             content_update[position] = false;
             }
             return fragment;
             }
             **/
        });
    }

    private void refresh() {
        int pageCount = mFaultPager.getAdapter().getCount();
        for (int i = 0; i < pageCount; i++) {
            TaskListFragment faultList = (TaskListFragment) mFaultPager.getAdapter().instantiateItem(mFaultPager, i);
            faultList.setFirstLoad(true);
            if (i == mFaultPager.getCurrentItem()) {
                faultList.refreshFragment();
            }
        }
    }

    private void setListener() {
        ivSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        etSearch.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    refresh();
                }
                return false;
            }
        });
    }

    private void findViews() {
        ivSearch = (ImageView) findViewById(R.id.iv_task_search);
        etSearch = (EditText) findViewById(R.id.et_task_search);
        mFaultPager = (ViewPager) findViewById(R.id.fault_pager);
        mFaultIndicator = (TabPageIndicator) findViewById(R.id.fault_indicator);
    }

    public String getSearchKey() {
        return etSearch.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refresh();
    }
}
