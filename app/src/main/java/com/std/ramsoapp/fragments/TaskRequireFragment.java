package com.std.ramsoapp.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.callback.StringCallback;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.RequireInfo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Maik on 2016/3/16.
 */
public class TaskRequireFragment extends BaseFrament {
    private String taskId;
    private StringBuilder sbRequire;
    private ProgressDialog dialog;
    private TextView tvRequire;
    private TextView tvAudit;
    private ImageView ivAudit;

    public static TaskRequireFragment newInstance(String taskId) {
        TaskRequireFragment newInstance = new TaskRequireFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        newInstance.setArguments(bundle);
        return newInstance;
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
                .url(Constant.URL_TASKREQUIRE)
                .addParams("taskId", taskId)
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
        List<RequireInfo> requireList = new Gson().fromJson(jsonStr, new TypeToken<ArrayList<RequireInfo>>() {
        }.getType());
        if (requireList == null) {
            Toast.makeText(getActivity(), R.string.data_error, Toast.LENGTH_SHORT).show();
            return;
        }
        if (requireList.size() > 1) {
            for (int i = 0; i < requireList.size(); i++) {
                RequireInfo requireInfo = requireList.get(i);
                if (!StringUtils.isEmpty(requireInfo.getCheckProject())) {
                    sbRequire.append(i + 1 + "、" + StringUtils.format(requireInfo.getCheckProject()) + "\n");
                } else if (!StringUtils.isEmpty(requireInfo.getConfirmRmk())) {
                    ivAudit.setVisibility(View.VISIBLE);
                    tvAudit.setVisibility(View.VISIBLE);
                    tvAudit.setText(StringUtils.format(requireInfo.getConfirmRmk()));
                    tvAudit.setTextColor(Color.RED);
                }
            }
            tvRequire.setText(sbRequire.toString());
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_require, container, false);
        taskId = getArguments().getString("taskId");
        sbRequire = new StringBuilder();
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        tvRequire = (TextView) view.findViewById(R.id.tv_task_require);
        ivAudit = (ImageView) view.findViewById(R.id.iv_task_aduit);
        tvAudit = (TextView) view.findViewById(R.id.tv_task_audit);
    }
}
