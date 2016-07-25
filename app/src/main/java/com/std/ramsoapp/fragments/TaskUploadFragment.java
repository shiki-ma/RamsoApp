package com.std.ramsoapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyleduo.switchbutton.SwitchButton;
import com.shiki.imgpicker.FGOGallery;
import com.shiki.imgpicker.FunctionConfig;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.builder.PostFormBuilder;
import com.shiki.okttp.callback.ROResultCallback;
import com.shiki.okttp.callback.StringCallback;
import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.recyclerview.RecyclerItemClickListener;

import com.shiki.utils.StringUtils;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.TaskUploadAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.ROResult;
import com.std.ramsoapp.domain.RequireInfo;

import net.grandcentrix.tray.AppPreferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Maik on 2016/3/18.
 */
public class TaskUploadFragment extends BaseFrament {
    private String taskId;
    private String userId;
    private FGORecyclerView mRecyclerView;
    private List<PhotoInfo> photoList;
    private ProgressDialog dialog;
    private TaskUploadAdapter adapter;
    private Button btUpload;

    private String[] imgeRequire;
    private int[] selectedImg;
    private Button btSubmit;
    private SwitchButton sbTaskResult;
    private EditText etTaskMemo;

    public static TaskUploadFragment newInstance(String taskId) {
        TaskUploadFragment newInstance = new TaskUploadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
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
                .url(Constant.URL_TASKIMAGE)
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
            imgeRequire = new String[requireList.size() - 1];
            selectedImg = new int[requireList.size() - 1];
            for (int i = 0; i < requireList.size(); i++) {
                String imageName = requireList.get(i).getImageName();
                if (!StringUtils.isEmpty(imageName)) {
                    imgeRequire[i] = imageName;
                    selectedImg[i] = -1;
                }
            }
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_upload, container, false);
        taskId = getArguments().getString("taskId");
        photoList = new ArrayList<>();
        findViews(view);
        initRecyclerView();
        setListener();
        return view;
    }

    private void setListener() {
        btSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectedImg != null) {
                    for (int selected : selectedImg) {
                        if (selected == -1) {
                            Toast.makeText(getActivity(), getString(R.string.no_key_image), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                //数据提交
                Map<String, String> params = new HashMap<>();
                params.put("taskId", taskId);
                params.put("userCode", userId);
                params.put("taskResult", sbTaskResult.isChecked() ? "1" : "2");
                params.put("commRmk", etTaskMemo.getText().toString());
                PostFormBuilder builder = OkHttpUtils.post().params(params);
                for (PhotoInfo photoInfo : photoList) {
                    File file = new File(photoInfo.getPhotoPath());
                    if (StringUtils.isEmpty(photoInfo.getTag()))
                        builder.addFile("mFiles", file.getName(), file);
                    else
                        builder.addFile("mFiles", "." + photoInfo.getTag() + "." + file.getName(), file);
                }
                dialog.setMessage(getString(R.string.submiting));
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                builder.url(Constant.URL_TASKSUBMIT).build().execute(new ROResultCallback() {

                    @Override
                    public void onError(Call call, Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ROResult response) {
                        dialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("提示").setMessage(response.getStatusMessage()).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().setResult(Constant.TASK_RESULT_STATE);
                                getActivity().finish();
                            }
                        });
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                getActivity().setResult(Constant.TASK_RESULT_STATE);
                                getActivity().finish();
                            }
                        });
                        builder.show();
                    }
                });
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FunctionConfig pickConfig = new FunctionConfig();
                pickConfig.setMaxSize(10);
                pickConfig.setMutiSelect(true);
                pickConfig.setSelectedList(photoList);
                FGOGallery.openGallery(getActivity(), pickConfig, new FGOGallery.OnHanlderResultCallback() {

                    @Override
                    public void onHanlderSuccess(List<PhotoInfo> resultList) {
                        if (resultList != null) {
                            if (selectedImg != null) {
                                for (int i = 0; i < selectedImg.length; i++) {
                                    selectedImg[i] = -1;
                                }
                            }
                            for (int i = 0; i < resultList.size(); i++) {
                                PhotoInfo pInfo = resultList.get(i);
                                for (PhotoInfo pOraInfo : photoList) {
                                    if (pInfo.getPhotoPath().equals(pOraInfo.getPhotoPath())) {
                                        pInfo.setTag(pOraInfo.getTag());
                                    }
                                }
                                if (!StringUtils.isEmpty(pInfo.getTag())) {
                                    for (int j = 0; j < imgeRequire.length; j++) {
                                        if (pInfo.getTag().equals(imgeRequire[j])) {
                                            selectedImg[j] = i;
                                        }
                                    }
                                }
                            }
                            photoList.clear();
                            photoList.addAll(resultList);
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onHanlderFailure(String errorMsg) {
                        Toast.makeText(getActivity(), R.string.photo_list_fail, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                if (imgeRequire == null) return;
                ActionSheet.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                        .setCancelButtonTitle(getString(R.string.cancel))
                        .setOtherButtonTitles(imgeRequire)
                        .setCancelableOnTouchOutside(true)
                        .setListener(new ActionSheet.ActionSheetListener() {

                            @Override
                            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                            }

                            @Override
                            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                                if (selectedImg[index] >= 0 && selectedImg[index] <= photoList.size()) {
                                    photoList.get(selectedImg[index]).setTag("");
                                }
                                selectedImg[index] = position;
                                photoList.get(position).setTag(imgeRequire[index]);
                                adapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        }));
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        sbTaskResult = (SwitchButton) view.findViewById(R.id.sb_task_result);
        etTaskMemo = (EditText) view.findViewById(R.id.et_task_memo);
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.task_upload_list);
        btUpload = (Button) view.findViewById(R.id.bt_upload);
        btSubmit = (Button) view.findViewById(R.id.bt_task_submit);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TaskUploadAdapter(photoList, getActivity());
        mRecyclerView.setAdapter(adapter);
    }
}
