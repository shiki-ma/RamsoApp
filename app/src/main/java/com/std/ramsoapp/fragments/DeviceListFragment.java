package com.std.ramsoapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.StringCallback;
import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.recyclerview.RecyclerItemClickListener;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.activity.DeviceActivity;
import com.std.ramsoapp.activity.DeviceScanActivity;
import com.std.ramsoapp.adapter.TaskListAdapter;
import com.std.ramsoapp.base.BaseApplication;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.TaskInfo;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Maik on 2016/2/18.
 */
public class DeviceListFragment extends BaseFrament {
    private FGORecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter adapter;
    private ProgressDialog dialog;

    private List<TaskInfo> deviceList = new ArrayList<>();
    private String userId;
    private int pageNum = 1;
    private EditText etSearch;
    private ImageView ivScan;
    private ImageView ivSearch;

    private enum GET_DATA_METHOD {
        LOAD, REFRESH, MORE
    }

    @Override
    protected void initData() {
        AppPreferences appPreferences = new AppPreferences(getActivity());
        userId = appPreferences.getString("userName", "");
    }

    @Override
    protected void lazyData() {
        getDataFromServer(GET_DATA_METHOD.LOAD);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_search, container, false);
        findViews(view);
        initRecyclerView();
        setListener();
        setUserVisibleHint(true);
        return view;
    }

    private void setListener() {
        ivScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeviceScanActivity.class);
                startActivityForResult(intent, Constant.DEVICE_RESULT_STATE);
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDataFromServer(GET_DATA_METHOD.LOAD);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TaskListAdapter(deviceList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.enableLoadmore();
        mRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getDataFromServer(GET_DATA_METHOD.REFRESH);
            }
        });
        mRecyclerView.setOnLoadMoreListener(new FGORecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                pageNum++;
                getDataFromServer(GET_DATA_METHOD.MORE);
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(BaseApplication.getApplication(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                ((DeviceActivity) getActivity()).setActionText(getString(R.string.device_title));
                replaceFragment(deviceList.get(position).getAssetId());
            }
        }));
    }

    private void replaceFragment(String taskId) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.fl_device, TaskDetailFragment.newInstance(taskId, "3"));
        ft.addToBackStack(null);
        ft.commit();
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.fgo_device_view);
        etSearch = (EditText) view.findViewById(R.id.et_device_search);
        ivScan = (ImageView) view.findViewById(R.id.iv_device_scan);
        ivSearch = (ImageView) view.findViewById(R.id.iv_device_search);
    }

    private void getDataFromServer(final GET_DATA_METHOD method) {
        if (method == GET_DATA_METHOD.LOAD) {
            dialog.setMessage(this.getString(R.string.waiting));
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        String faultKey = etSearch.getText().toString();
        OkHttpUtils
                .get()
                .url(Constant.URL_TASKLIST)
                .addParams("userCode", userId)
                .addParams("currPage", String.valueOf(pageNum))
                .addParams("queryName", faultKey)
                .addParams("queryType", Constant.DEVICE_SEARCH)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        if (method == GET_DATA_METHOD.REFRESH) {
                            mRecyclerView.setRefreshing(false);
                        } else if (method == GET_DATA_METHOD.LOAD) {
                            dialog.dismiss();
                        }
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Logger.d(response);
                        dealData(response, method);
                        dialog.dismiss();
                    }
                });
    }

    private void dealData(String jsonStr, final GET_DATA_METHOD method) {
        List<TaskInfo> tpList = new Gson().fromJson(jsonStr, new TypeToken<ArrayList<TaskInfo>>() {
        }.getType());
        if (tpList == null) {
            Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (method == GET_DATA_METHOD.REFRESH || method == GET_DATA_METHOD.LOAD) {
            deviceList.clear();
        }
        if (tpList.size() <= 1) {
            if (method == GET_DATA_METHOD.MORE) {
                pageNum--;
                mRecyclerView.noMoreData();
                Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
            }
        } else {
            tpList.remove(tpList.size() - 1);
            deviceList.addAll(tpList);
        }
        adapter.notifyDataSetChanged();
        if (method == GET_DATA_METHOD.REFRESH) {
            mRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data !=  null) {
            String taskId = data.getExtras().getString("taskId");
            replaceFragment(taskId);
        }
    }
}
