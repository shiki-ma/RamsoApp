package com.std.ramsoapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiki.utils.StringUtils;
import com.std.ramsoapp.R;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.FaultInfo;

/**
 * Created by Maik on 2016/3/16.
 */
public class FaultAuditFragment extends BaseFrament {
    private FaultInfo faultInfo;
    private TextView tvAudit;
    private ImageView ivAudit;

    public static FaultAuditFragment newInstance(FaultInfo faultInfo) {
        FaultAuditFragment newInstance = new FaultAuditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("faultInfo", faultInfo);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_audit, container, false);
        faultInfo = getArguments().getParcelable("faultInfo");
        findViews(view);
        showAudit();
        return view;
    }

    private void showAudit() {
        if (faultInfo.getFaultResultName() != null && faultInfo.getFaultResultName().equals("不通过")) {
            ivAudit.setVisibility(View.VISIBLE);
            tvAudit.setVisibility(View.VISIBLE);
            tvAudit.setText(StringUtils.format(faultInfo.getConfirmRmk()));
            tvAudit.setTextColor(Color.RED);
        }
    }

    private void findViews(View view) {
        ivAudit = (ImageView) view.findViewById(R.id.iv_fault_aduit);
        tvAudit = (TextView) view.findViewById(R.id.tv_fault_audit);
    }
}
