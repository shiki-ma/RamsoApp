package com.std.ramsoapp.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shiki.imgpicker.FGOGalleryUtil;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.recyclerview.FGORecyclerViewAdapter;
import com.std.ramsoapp.R;

import java.util.List;

/**
 * Created by Maik on 2016/3/18.
 */
public class TaskUploadAdapter extends FGORecyclerViewAdapter<TaskUploadAdapter.ModeViewHolder> {
    private List<PhotoInfo> photoInfoList;
    private Activity mActivity;


    public TaskUploadAdapter(List<PhotoInfo> photoInfoList, Activity activity) {
        this.photoInfoList = photoInfoList;
        this.mActivity = activity;
    }

    @Override
    public ModeViewHolder getViewHolder(View view) {
        return new ModeViewHolder(view, false);
    }

    @Override
    public ModeViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_upload_item, parent, false);
        ModeViewHolder viewHolder = new ModeViewHolder(v, true);
        return viewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return photoInfoList.size();
    }

    @Override
    public void onBindViewHolder(ModeViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= photoInfoList.size() : position < photoInfoList.size()) && (customHeaderView != null ? position > 0 : true)) {
            int index = customHeaderView != null ? position - 1 : position;
            PhotoInfo photoInfo = photoInfoList.get(index);
            String path = "";
            if (photoInfo != null) {
                path = photoInfo.getPhotoPath();
            }
            Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
            FGOGalleryUtil.displayImage(mActivity, path, holder.sdvPhoto, defaultDrawable,120, 120);
            holder.tvTag.setText(photoInfo.getTag());
        }
    }

    class ModeViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdvPhoto;
        public TextView tvTag;

        public ModeViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                sdvPhoto = (SimpleDraweeView) itemView.findViewById(R.id.sdv_photo);
                tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            }
        }

    }
}
