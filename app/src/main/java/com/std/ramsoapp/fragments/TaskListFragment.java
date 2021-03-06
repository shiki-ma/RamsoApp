package com.std.ramsoapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.recyclerview.RecyclerItemClickListener;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.activity.TaskListActivity;
import com.std.ramsoapp.activity.TaskScanActivity;
import com.std.ramsoapp.adapter.TaskListAdapter;
import com.std.ramsoapp.base.BaseApplication;
import com.std.ramsoapp.base.BaseFrament;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.StringCallback;
import com.std.ramsoapp.domain.TaskInfo;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Maik on 2016/2/18.
 */
public class TaskListFragment extends BaseFrament {
    private FGORecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter adapter;
    private ProgressDialog dialog;

    private String faultType;
    private List<TaskInfo> taskList = new ArrayList<>();
    private String userId;
    private int pageNum = 1;

    private enum GET_DATA_METHOD {
        LOAD, REFRESH, MORE
    }

    public static TaskListFragment newInstance(String faultType) {
        TaskListFragment newInstance = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("faultType", faultType);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    protected void initData() {
        AppPreferences appPreferences = new AppPreferences(getActivity());
        userId = appPreferences.getString("userName", "");
    }

    @Override
    protected void lazyData() {
        //手动刷新
        if (getFirstLoad()) {
            taskList.clear();
        }
        if (taskList.size() <= 0) {
            getDataFromServer(GET_DATA_METHOD.LOAD);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        faultType = getArguments().getString("faultType");
        findViews(view);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TaskListAdapter(taskList);
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
        if (faultType.equals(Constant.TASK_BEFORE_DEAL) || faultType.equals(Constant.TASK_DEAL)) {
            mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(BaseApplication.getApplication(), new RecyclerItemClickListener.OnItemClickListener() {

                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), TaskScanActivity.class);
                    Bundle taskBundle = new Bundle();
                    taskBundle.putString("taskId", taskList.get(position).getTaskId());
                    taskBundle.putString("assetId", taskList.get(position).getAssetId());
                    intent.putExtras(taskBundle);
                    getActivity().startActivityForResult(intent, Constant.TASK_RESULT_STATE);
                }
            }));
        }
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.fgo_recycler_view);
    }

    private void getDataFromServer(final GET_DATA_METHOD method) {
        if (method == GET_DATA_METHOD.LOAD) {
            dialog.setMessage(this.getString(R.string.waiting));
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        String faultKey = ((TaskListActivity) getActivity()).getSearchKey();
        OkHttpUtils
                .get()
                .url(Constant.URL_TASKLIST)
                .addParams("userCode", userId)
                .addParams("currPage", String.valueOf(pageNum))
                .addParams("queryName", faultKey)
                .addParams("queryType", faultType)
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
        if (method == GET_DATA_METHOD.REFRESH) {
            taskList.clear();
        }
        if (tpList.size() <= 1) {
            if (method == GET_DATA_METHOD.MORE) {
                pageNum--;
                mRecyclerView.noMoreData();
                Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
            }
        } else {
            tpList.remove(tpList.size() - 1);
            taskList.addAll(tpList);
        }
        adapter.notifyDataSetChanged();
        if (method == GET_DATA_METHOD.REFRESH) {
            mRecyclerView.setRefreshing(false);
            linearLayoutManager.scrollToPosition(0);
        }
    }

    public void refreshFragment() {
        lazyData();
        this.setFirstLoad(false);
    }
}
