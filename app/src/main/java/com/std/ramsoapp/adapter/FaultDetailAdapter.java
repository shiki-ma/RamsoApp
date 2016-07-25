package com.std.ramsoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.shiki.utils.StringUtils;
import com.std.ramsoapp.R;

/**
 * Created by Maik on 2016/3/15.
 */
public class FaultDetailAdapter extends FGORecyclerViewAdapter<FaultDetailAdapter.ModeViewHolder> {
    private String[] detailTitle = new String[]{"故障编号", "故障来源", "客户名称", "端点名称", "设备名称", "现象描述", "其他情况", "故障类型",
            "故障级别", "备注", "审核结果", "审核意见", "审核时间", "端点地址", "客户联系人", "客户联系人电话"};
    private SparseArray<String> faultArray;

    public FaultDetailAdapter(SparseArray<String> faultArray) {
        this.faultArray = faultArray;
    }

    @Override
    public ModeViewHolder getViewHolder(View view) {
        return new ModeViewHolder(view, false);
    }

    @Override
    public ModeViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_detail_item, parent, false);
        ModeViewHolder viewHolder = new ModeViewHolder(v, true);
        return viewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return detailTitle.length;
    }

    @Override
    public void onBindViewHolder(FaultDetailAdapter.ModeViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= detailTitle.length : position < detailTitle.length) && (customHeaderView != null ? position > 0 : true)) {
            int index = customHeaderView != null ? position - 1 : position;
            holder.tvDetailTitle.setText(detailTitle[index]);
            holder.tvDetailContent.setText(StringUtils.format(faultArray.get(index)));
        }
    }

    class ModeViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDetailTitle;
        public TextView tvDetailContent;

        public ModeViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                tvDetailTitle = (TextView) itemView.findViewById(R.id.tv_detail_title);
                tvDetailContent = (TextView) itemView.findViewById(R.id.tv_detail_content);
            }
        }

    }
}
