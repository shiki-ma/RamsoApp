package com.std.ramsoapp.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.R;
import com.std.ramsoapp.domain.FaultInfo;

import java.util.List;

/**
 * Created by Maik on 2016/2/3.
 */
public class FaultListAdapter extends FGORecyclerViewAdapter<FaultListAdapter.ModeViewHolder> {
    private List<FaultInfo> faultList;

    public FaultListAdapter(List<FaultInfo> faultList) {
        this.faultList = faultList;
    }

    @Override
    public ModeViewHolder getViewHolder(View view) {
        return new ModeViewHolder(view, false);
    }

    @Override
    public ModeViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fault_list_item, parent, false);
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ModeViewHolder viewHolder = new ModeViewHolder(v, true);
        return viewHolder;
    }

    public int getAdapterItemCount() {
        return faultList.size();
    }

    @Override
    public void onBindViewHolder(ModeViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= faultList.size() : position < faultList.size()) && (customHeaderView != null ? position > 0 : true)) {
            int index = customHeaderView != null ? position - 1 : position;
            FaultInfo faultInfo = faultList.get(index);
            holder.tvFaultAssetName.setText(StringUtils.format(faultInfo.getAssetName()));
            holder.tvFaultTerName.setText(StringUtils.format(faultInfo.getFaultTermName()));
            holder.tvFaultAddress.setText(StringUtils.format(faultInfo.getTermAddress()));
            holder.tvFaultContact.setText(StringUtils.format(faultInfo.getCustConnector()));
            holder.tvFaultTelephone.setText(StringUtils.format(faultInfo.getCustConnectorTel()));
            holder.tvFaultDate.setText("受理时间：" + StringUtils.format(faultInfo.getFaultDate()));
            holder.tvFaultRequireDate.setText("最后期限：" + StringUtils.format(faultInfo.getFaultRequiredDate()));
            holder.tvFaultDesc.setText("现象描述：" + StringUtils.format(faultInfo.getFaultDesc()));
            if (StringUtils.format(faultInfo.getFaultResult()).equals("2")) {
                holder.tvFaultAssetName.setTextColor(Color.RED);
            }
        }
    }

    class ModeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFaultAssetName;
        public TextView tvFaultTerName;
        public TextView tvFaultDate;
        public TextView tvFaultRequireDate;
        public TextView tvFaultDesc;
        public RelativeLayout rlFaultList;
        public TextView tvFaultAddress;
        public TextView tvFaultContact;
        public TextView tvFaultTelephone;

        public ModeViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                tvFaultAssetName = (TextView) itemView.findViewById(R.id.tv_fault_assetname);
                tvFaultTerName = (TextView) itemView.findViewById(R.id.tv_fault_termname);
                tvFaultDate = (TextView) itemView.findViewById(R.id.tv_fault_date);
                tvFaultRequireDate = (TextView) itemView.findViewById(R.id.tv_fault_requiredate);
                tvFaultDesc = (TextView) itemView.findViewById(R.id.tv_fault_desc);
                tvFaultAddress = (TextView) itemView.findViewById(R.id.tv_fault_address);
                tvFaultContact = (TextView) itemView.findViewById(R.id.tv_fault_contact);
                tvFaultTelephone = (TextView) itemView.findViewById(R.id.tv_fault_telephone);
                rlFaultList = (RelativeLayout) itemView.findViewById(R.id.rl_fault_list_item);
            }
        }

    }
}
