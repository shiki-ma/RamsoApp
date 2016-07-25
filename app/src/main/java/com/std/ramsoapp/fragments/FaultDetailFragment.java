package com.std.ramsoapp.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiki.recyclerview.FGORecyclerView;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.R;
import com.std.ramsoapp.adapter.FaultDetailAdapter;
import com.std.ramsoapp.base.BaseFrament;
import com.std.ramsoapp.domain.FaultInfo;

/**
 * Created by Maik on 2016/3/15.
 */
public class FaultDetailFragment extends BaseFrament {
    private FaultInfo faultInfo;
    private FGORecyclerView mRecyclerView;
    private FaultDetailAdapter adapter;
    private SparseArray<String> faultArray = new SparseArray<>();

    public static FaultDetailFragment newInstance(FaultInfo faultInfo) {
        FaultDetailFragment newInstance = new FaultDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("faultInfo", faultInfo);
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        faultInfo = getArguments().getParcelable("faultInfo");
        findViews(view);
        initRecyclerView();
        return view;
    }

    private void findViews(View view) {
        mRecyclerView = (FGORecyclerView) view.findViewById(R.id.detail_recycler_view);
    }

    private void initRecyclerView() {
        initFaultArray();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FaultDetailAdapter(faultArray);
        mRecyclerView.setAdapter(adapter);
    }

    private void initFaultArray() {
        faultArray.put(0, StringUtils.format(faultInfo.getFaultCode()));
        faultArray.put(1, StringUtils.format(faultInfo.getFaultSource()));
        faultArray.put(2, StringUtils.format(faultInfo.getFaultCustName()));
        faultArray.put(3, StringUtils.format(faultInfo.getFaultTermName()));
        faultArray.put(4, StringUtils.format(faultInfo.getAssetName()));
        faultArray.put(5, StringUtils.format(faultInfo.getFaultDesc()));
        faultArray.put(6, StringUtils.format(faultInfo.getFaultOthCondition()));
        faultArray.put(7, StringUtils.format(faultInfo.getFaultType()));
        faultArray.put(8, StringUtils.format(faultInfo.getFaultLevelName()));
        faultArray.put(9, StringUtils.format(faultInfo.getFaultRemark()));
        faultArray.put(10, StringUtils.format(faultInfo.getFaultResultName()));
        faultArray.put(11, StringUtils.format(faultInfo.getConfirmRmk()));
        faultArray.put(12, StringUtils.format(faultInfo.getConfirmDate()));
        faultArray.put(13, StringUtils.format(faultInfo.getTermAddress()));
        faultArray.put(14, StringUtils.format(faultInfo.getCustConnector()));
        faultArray.put(15, StringUtils.format(faultInfo.getCustConnectorTel()));
    }
}
