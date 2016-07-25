package com.std.ramsoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.std.ramsoapp.R;

/**
 * Created by Maik on 2016/3/15.
 */
public class TaskDetailAdapter extends FGORecyclerViewAdapter<TaskDetailAdapter.ModeViewHolder> {
    private String[] detailTitle = new String[]{"资产内部编号", "资产外部编号", "资产类型", "状态", "资产名称", "品牌", "规格型号", "启用日期",
            "中继方式", "维保到期日期", "中继容量", "工程编号", "中继方向", "供应商", "在用数字中继", "所属单位类型", "在用模拟中继", "所属单位",
            "引示号", "建设单位", "上级资产", "开机密码", "客户名称", "用户端点", "操作密码", "审核时间", "审核结果", "端点地址", "客户联系人", "客户联系人电话"};
    private SparseArray<String> taskArray;

    public TaskDetailAdapter(SparseArray<String> taskArray) {
        this.taskArray = taskArray;
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
    public void onBindViewHolder(TaskDetailAdapter.ModeViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= detailTitle.length : position < detailTitle.length) && (customHeaderView != null ? position > 0 : true)) {
            int index = customHeaderView != null ? position - 1 : position;
            holder.tvDetailTitle.setText(detailTitle[index]);
            holder.tvDetailContent.setText(taskArray.get(index));
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
