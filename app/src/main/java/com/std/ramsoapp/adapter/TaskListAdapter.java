package com.std.ramsoapp.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.std.ramsoapp.R;
import com.std.ramsoapp.domain.TaskInfo;

import java.util.List;

/**
 * Created by Maik on 2016/2/3.
 */
public class TaskListAdapter extends FGORecyclerViewAdapter<TaskListAdapter.ModeViewHolder> {
    private List<TaskInfo> taskList;

    public TaskListAdapter(List<TaskInfo> taskList) {
        this.taskList = taskList;
    }

    @Override
    public ModeViewHolder getViewHolder(View view) {
        return new ModeViewHolder(view, false);
    }

    @Override
    public ModeViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ModeViewHolder viewHolder = new ModeViewHolder(v, true);
        return viewHolder;
    }

    public int getAdapterItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(ModeViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= taskList.size() : position < taskList.size()) && (customHeaderView != null ? position > 0 : true)) {
            int index = customHeaderView != null ? position - 1 : position;
            TaskInfo taskInfo = taskList.get(index);
            holder.tvTaskAssetName.setText(taskInfo.getAssetName());
            holder.tvTaskTerName.setText(taskInfo.getTermName());
            holder.tvTaskDate.setText(taskInfo.getTaskDate());
            holder.tvTaskAddress.setText(taskInfo.getTermAddress());
            holder.tvTaskContact.setText(taskInfo.getCustConnector());
            holder.tvTaskTelephone.setText(taskInfo.getCustConnectorTel());
            if (taskInfo.getAssetResult() != null && taskInfo.getAssetResult().equals("2")) {
                //holder.rlTaskList.setBackgroundColor(Color.RED);
                holder.tvTaskAssetName.setTextColor(Color.RED);
            }
            if (taskInfo.getAssetStatus() != null) {
                holder.tvTaskDate.setVisibility(View.GONE);
                holder.tvTaskStatus.setVisibility(View.VISIBLE);
                holder.tvTaskStatus.setText(taskInfo.getAssetStatus());
                holder.tvTaskStatus.setTextColor(Color.WHITE);
                GradientDrawable gradientDrawable = (GradientDrawable) holder.tvTaskStatus.getBackground();
                if (taskInfo.getAssetStatus().equals("已入库")) {
                    gradientDrawable.setColor(Color.GREEN);
                } else if (taskInfo.getAssetStatus().equals("变更中")) {
                    gradientDrawable.setColor(Color.BLUE);
                } else if (taskInfo.getAssetStatus().equals("转移中")) {
                    gradientDrawable.setColor(Color.RED);
                } else if (taskInfo.getAssetStatus().equals("已报废")) {
                    gradientDrawable.setColor(Color.YELLOW);
                } else if (taskInfo.getAssetStatus().equals("异常")) {
                    gradientDrawable.setColor(Color.MAGENTA);
                } else if (taskInfo.getAssetStatus().equals("报废中")) {
                    gradientDrawable.setColor(Color.CYAN);
                }
            }
        }
    }

    class ModeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTaskAssetName;
        public TextView tvTaskTerName;
        public TextView tvTaskDate;
        public TextView tvTaskStatus;
        public RelativeLayout rlTaskList;
        public TextView tvTaskAddress;
        public TextView tvTaskContact;
        public TextView tvTaskTelephone;

        public ModeViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                tvTaskAssetName = (TextView) itemView.findViewById(R.id.tv_task_assetname);
                tvTaskTerName = (TextView) itemView.findViewById(R.id.tv_task_termname);
                tvTaskDate = (TextView) itemView.findViewById(R.id.tv_task_taskdate);
                rlTaskList = (RelativeLayout) itemView.findViewById(R.id.rl_task_list_item);
                tvTaskStatus = (TextView) itemView.findViewById(R.id.tv_task_status);
                tvTaskAddress = (TextView) itemView.findViewById(R.id.tv_task_address);
                tvTaskContact = (TextView) itemView.findViewById(R.id.tv_task_contact);
                tvTaskTelephone = (TextView) itemView.findViewById(R.id.tv_task_telephone);
            }
        }

    }
}
