package com.std.ramsoapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.StringCallback;
import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.TaskDetailAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.TaskInfo;

import net.grandcentrix.tray.AppPreferences;

import okhttp3.Call;

/**
 * Created by Maik on 2016/3/15.
 */
public class TaskDetailFragment extends BaseFrament {
    private String userId;
    private String taskId;
    private String isFlag;
    private ProgressDialog dialog;
    private FGORecyclerView mRecyclerView;
    private TaskDetailAdapter adapter;
    private SparseArray<String> taskArray = new SparseArray<>();

    public static TaskDetailFragment newInstance(String taskId, String isFlag) {
        TaskDetailFragment newInstance = new TaskDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        bundle.putString("isFlag", isFlag);
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
        getDataFromServer();
    }

    private void getDataFromServer() {
        dialog.setMessage(this.getString(R.string.waiting));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpUtils
                .get()
                .url(Constant.URL_TASKDETAIL)
                .addParams("userCode", userId)
                .addParams("taskId", taskId)
                .addParams("isFlag", isFlag)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        dealData(response);
                        dialog.dismiss();
                    }
                });
    }

    private void dealData(String jsonStr) {
        TaskInfo taskInfo = new Gson().fromJson(jsonStr, TaskInfo.class);
        if (taskInfo == null) {
            Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (taskInfo.getStatusCode().equals(Constant.RES_SUCCESS)) {
            taskArray.put(0, StringUtils.format(taskInfo.getAssetCode()));
            taskArray.put(1, StringUtils.format(taskInfo.getAssetOutCode()));
            taskArray.put(2, StringUtils.format(taskInfo.getAssetTypeName()));
            taskArray.put(3, StringUtils.format(taskInfo.getAssetStatus()));
            taskArray.put(4, StringUtils.format(taskInfo.getAssetName()));
            taskArray.put(5, StringUtils.format(taskInfo.getAssetBrand()));
            taskArray.put(6, StringUtils.format(taskInfo.getAssetModel()));
            taskArray.put(7, StringUtils.format(taskInfo.getAssetUsedDate()));
            taskArray.put(8, StringUtils.format(taskInfo.getAssetWay()));
            taskArray.put(9, StringUtils.format(taskInfo.getAssetMaintenDate()));
            taskArray.put(10, StringUtils.format(taskInfo.getAssetCapacity()));
            taskArray.put(11, StringUtils.format(taskInfo.getAssetProjCode()));
            taskArray.put(12, StringUtils.format(taskInfo.getAssetDirection()));
            taskArray.put(13, StringUtils.format(taskInfo.getAssetSupplyName()));
            taskArray.put(14, StringUtils.format(taskInfo.getAssetDigitalRelay()));
            taskArray.put(15, StringUtils.format(taskInfo.getAssetBelongType()));
            taskArray.put(16, StringUtils.format(taskInfo.getAssetSimulationRelay()));
            taskArray.put(17, StringUtils.format(taskInfo.getAssetBelongName()));
            taskArray.put(18, StringUtils.format(taskInfo.getAssetCited()));
            taskArray.put(19, StringUtils.format(taskInfo.getAssetBuildName()));
            taskArray.put(20, StringUtils.format(taskInfo.getAssetForeignName()));
            taskArray.put(21, StringUtils.format(taskInfo.getAssetStartPasswd()));
            taskArray.put(22, StringUtils.format(taskInfo.getCustName()));
            taskArray.put(23, StringUtils.format(taskInfo.getTermName()));
            taskArray.put(24, StringUtils.format(taskInfo.getAssetOperatePasswd()));
            taskArray.put(25, StringUtils.format(taskInfo.getAssetConfirmDate()));
            taskArray.put(26, StringUtils.format(taskInfo.getAssetResult()));
            taskArray.put(27, StringUtils.format(taskInfo.getTermAddress()));
            taskArray.put(28, StringUtils.format(taskInfo.getCustConnector()));
            taskArray.put(29, StringUtils.format(taskInfo.getCustConnectorTel()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        taskId = getArguments().getString("taskId");
        isFlag = getArguments().getString("isFlag");
        findViews(view);
        initRecyclerView();
        setUserVisibleHint(true);
        return view;
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.detail_recycler_view);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskDetailAdapter(taskArray);
        mRecyclerView.setAdapter(adapter);
    }
}
