package com.shiki.imgpicker.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shiki.imgpicker.FGOGallery;
import com.shiki.imgpicker.FGOGalleryUtil;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.utils.adapter.ViewHolderAdapter;
import com.std.ramsoapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Maik on 2016/2/24.
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo> {
    private Map<String, PhotoInfo> mSelectList;
    private int mScreenWidth;
    private int mRowWidth;

    private Activity mActivity;

    public PhotoListAdapter(Activity activity, List<PhotoInfo> list, Map<String, PhotoInfo> selectList, int screenWidth) {
        super(activity, list);
        this.mSelectList = selectList;
        this.mScreenWidth = screenWidth;
        this.mRowWidth = mScreenWidth / 3;
        this.mActivity = activity;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.fgo_photo_list_item, parent);
        setHeight(view);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoInfo photoInfo = getDatas().get(position);
        String path = "";
        if (photoInfo != null) {
            path = photoInfo.getPhotoPath();
        }
        Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
        FGOGalleryUtil.displayImage(mActivity, path, holder.mIvThumb, defaultDrawable, mRowWidth, mRowWidth);
        holder.mView.setAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.gf_flip_horizontal_in));
        if (FGOGallery.getCurrentFunctionConfig().isMutiSelect()) {
            holder.mIvCheck.setVisibility(View.VISIBLE);
            if (mSelectList.get(photoInfo.getPhotoPath()) != null) {
                holder.mIvCheck.setBackgroundColor(Color.rgb(0x3F, 0x51, 0xB5));
            } else {
                holder.mIvCheck.setBackgroundColor(Color.rgb(0xd2, 0xd2, 0xd7));
            }
        } else {
            holder.mIvCheck.setVisibility(View.GONE);
        }
    }

    private void setHeight(View view) {
        int height = mScreenWidth / 3 - 8;
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }


    public static class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {
        public SimpleDraweeView mIvThumb;
        public ImageView mIvCheck;
        View mView;

        public PhotoViewHolder(View view) {
            super(view);
            mView = view;
            mIvThumb = (SimpleDraweeView) view.findViewById(R.id.iv_thumb);
            mIvCheck = (ImageView) view.findViewById(R.id.iv_check);
        }
    }
}
