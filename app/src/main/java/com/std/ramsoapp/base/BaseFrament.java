package com.std.ramsoapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Maik on 2016/1/28.
 */
public abstract class BaseFrament extends Fragment {
    private boolean isVisible;
    private boolean isPrepared;
    private boolean isFirstLoad = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
    }

    // 初始化Fragment布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        lazyData();
        isFirstLoad = false;
    }

    protected void initData() {
    }

    protected void lazyData() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void setFirstLoad(boolean isFirstLoad) {
        this.isFirstLoad = isFirstLoad;
    }

    public boolean getFirstLoad() {
        return isFirstLoad;
    }
}
