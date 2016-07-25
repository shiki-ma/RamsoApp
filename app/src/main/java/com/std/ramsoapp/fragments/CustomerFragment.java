package com.std.ramsoapp.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.MenuAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.MenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/1/28.
 */
public class CustomerFragment extends BaseFrament {
    private List<MenuInfo> menuInfos;
    private GridView gvMenu;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gvMenu = (GridView) view.findViewById(R.id.gv_main);
        gvMenu.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvMenu.setAdapter(new MenuAdapter(getActivity(), menuInfos));
        return view;
    }

    @Override
    protected void initData() {
        menuInfos = new ArrayList<>();
        MenuInfo info = new MenuInfo();
        info.setImgResource(R.drawable.ic_customer_info);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_customer_visit);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_customer_business);
        menuInfos.add(info);
    }
}
