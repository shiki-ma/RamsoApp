package com.std.ramsoapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shiki.utils.StringUtils;
import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.MenuAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.MenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/1/28.
 */
public class BusinessFragment extends BaseFrament {
    private List<MenuInfo> menuInfos;
    private GridView gvMenu;

    @Override
    protected void initData() {
        menuInfos = new ArrayList<>();
        MenuInfo info = new MenuInfo();
        info.setImgResource(R.drawable.ic_task_list);
        info.setRootClass("com.std.ramsoapp.activity.TaskListActivity");
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_task_exec);
        info.setRootClass("com.std.ramsoapp.activity.TaskScanActivity");
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_fault_list);
        info.setRootClass("com.std.ramsoapp.activity.FaultListActivity");
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_device_search);
        info.setRootClass("com.std.ramsoapp.activity.DeviceActivity");
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_report_stat);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setImgResource(R.drawable.ic_other);
        info.setRootClass("com.std.ramsoapp.activity.TestActivity");
        menuInfos.add(info);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gvMenu = (GridView) view.findViewById(R.id.gv_main);
        gvMenu.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvMenu.setAdapter(new MenuAdapter(getActivity(), menuInfos));
        gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String rootClass = menuInfos.get(position).getRootClass();
                    if (!StringUtils.isEmpty(rootClass)) {
                        Intent intent = new Intent(getActivity(), Class.forName(rootClass));
                        startActivity(intent);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
