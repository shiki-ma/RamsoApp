package com.std.ramsoapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.shiki.imgpicker.FGOGallery;
import com.shiki.imgpicker.FunctionConfig;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.okttp.OkHttpUtils;
import com.shiki.okttp.builder.PostFormBuilder;
import com.shiki.okttp.callback.ROResultCallback;
import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.Constant;
import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.FaultUploadAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.FaultInfo;
import com.std.ramsoapp.domain.ROResult;

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
public class FaultDealFragment extends BaseFrament {
    private FaultInfo faultInfo;
    private String userId;
    private FGORecyclerView mRecyclerView;
    private List<PhotoInfo> photoList;
    private ProgressDialog dialog;
    private FaultUploadAdapter adapter;
    private Button btUpload;

    private Button btSubmit;
    private SwitchButton sbFaultAssist;
    private EditText etFaultAnalyse;
    private EditText etFaultTreatment;
    private EditText etFaultAstCondition;
    private Spinner spFaultResult;
    private EditText etFaultFeedback;

    public static FaultDealFragment newInstance(FaultInfo faultInfo) {
        FaultDealFragment newInstance = new FaultDealFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("faultInfo", faultInfo);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    protected void initData() {
        AppPreferences appPreferences = new AppPreferences(getActivity());
        userId = appPreferences.getString("userName", "");
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_deal, container, false);
        faultInfo = getArguments().getParcelable("faultInfo");
        photoList = new ArrayList<>();
        findViews(view);
        final String spResults[] = new String[]{"故障解决，恢复正常", "故障转移，替代方案"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, spResults);
        spFaultResult.setAdapter(spAdapter);
        initDealInfo();
        initRecyclerView();
        setListener();
        return view;
    }

    private void initDealInfo() {
        etFaultAnalyse.setText(StringUtils.format(faultInfo.getHandlerAnalyse()));
        etFaultTreatment.setText(StringUtils.format(faultInfo.getHandlerTreatment()));
        etFaultAstCondition.setText(StringUtils.format(faultInfo.getHandlerAstCondition()));
        if (!StringUtils.isEmpty(faultInfo.getHandlerResult()))
            spFaultResult.setSelection(Integer.parseInt(faultInfo.getHandlerResult()) - 1, true);
        etFaultFeedback.setText(StringUtils.format(faultInfo.getHandlerFeedback()));
        if (faultInfo.getHandlerAssist() != null && StringUtils.format(faultInfo.getHandlerAssist()).equals("1")) {
            sbFaultAssist.setChecked(true);
        }
    }

    private void setListener() {
        btSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("faultId", faultInfo.getFaultId());
                params.put("userCode", userId);
                params.put("handlerAnalyse", etFaultAnalyse.getText().toString());
                params.put("handlerTreatment", etFaultTreatment.getText().toString());
                params.put("handlerAssist", sbFaultAssist.isChecked() ? "1" : "2");
                params.put("handlerAstCondition", etFaultAstCondition.getText().toString());
                params.put("handlerResult", String.valueOf(spFaultResult.getSelectedItemPosition() + 1));
                params.put("handlerFeedback", etFaultFeedback.getText().toString());
                PostFormBuilder builder = OkHttpUtils.post().params(params);
                for (PhotoInfo photoInfo : photoList) {
                    File file = new File(photoInfo.getPhotoPath());
                    builder.addFile("mFiles", file.getName(), file);
                }
                dialog.setMessage(getString(R.string.submiting));
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
                builder.url(Constant.URL_FAULTSUBMIT).build().execute(new ROResultCallback() {

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
                                getActivity().setResult(Constant.FAULT_RESULT_STATE);
                                getActivity().finish();
                            }
                        });
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                getActivity().setResult(Constant.FAULT_RESULT_STATE);
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
    }

    private void findViews(View view) {
        dialog = new ProgressDialog(getActivity());
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.fault_upload_list);
        btUpload = (Button) view.findViewById(R.id.bt_upload);
        btSubmit = (Button) view.findViewById(R.id.bt_fault_submit);
        etFaultAnalyse = (EditText) view.findViewById(R.id.et_fault_analyse);
        etFaultTreatment = (EditText) view.findViewById(R.id.et_fault_treatment);
        etFaultAstCondition = (EditText) view.findViewById(R.id.et_fault_astcondition);
        spFaultResult = (Spinner) view.findViewById(R.id.sp_fault_result);
        etFaultFeedback = (EditText) view.findViewById(R.id.et_fault_feedback);
        sbFaultAssist = (SwitchButton) view.findViewById(R.id.sb_fault_assist);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FaultUploadAdapter(photoList, getActivity());
        mRecyclerView.setAdapter(adapter);
    }
}
