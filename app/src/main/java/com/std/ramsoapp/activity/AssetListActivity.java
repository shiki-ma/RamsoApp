package com.std.ramsoapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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
import com.std.ramsoapp.adapter.TaskListAdapter;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.base.BaseApplication;
import com.std.ramsoapp.domain.TaskInfo;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Maik on 2016/3/23.
 */
public class AssetListActivity extends BaseActivity {
    private List<TaskInfo> assetList = new ArrayList<>();
    private ProgressDialog dialog;
    private FGORecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TaskListAdapter adapter;
    private String userId;
    private String assetId;
    private ActionBar actionBar;

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_list);
        Bundle assetBundle = this.getIntent().getExtras();
        assetId = assetBundle.getString("assetId");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        findViews();
        initRecyclerView();
    }

    @Override
    protected void preData() {
        AppPreferences appPreferences = new AppPreferences(this);
        userId = appPreferences.getString("userName", "");
    }

    @Override
    protected void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        dialog.setMessage(this.getString(R.string.waiting));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpUtils
                .get()
                .url(Constant.URL_TASKLIST)
                .addParams("userCode", userId)
                .addParams("queryName", assetId)
                .addParams("queryType", Constant.TASK_SEARCH)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AssetListActivity.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Logger.d(response);
                        dealData(response);
                        dialog.dismiss();
                    }
                });
    }

    private void dealData(String jsonStr) {
        List<TaskInfo> tpList = new Gson().fromJson(jsonStr, new TypeToken<ArrayList<TaskInfo>>() {
        }.getType());
        if (tpList == null) {
            Toast.makeText(AssetListActivity.this, R.string.data_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (tpList.size() > 1) {
            tpList.remove(tpList.size() - 1);
            assetList.clear();
            assetList.addAll(tpList);
            adapter.notifyDataSetChanged();
        }
    }

    private void findViews() {
        dialog = new ProgressDialog(this);
        mRecyclerView = (FGORecyclerView) findViewById(R.id.fgo_recycler_view);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TaskListAdapter(assetList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(BaseApplication.getApplication(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AssetListActivity.this, TaskExecuteActivity.class);
                Bundle taskBundle = new Bundle();
                taskBundle.putString("taskId", assetList.get(position).getTaskId());
                intent.putExtras(taskBundle);
                startActivityForResult(intent, Constant.TASK_RESULT_STATE);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getDataFromServer();
    }
}
