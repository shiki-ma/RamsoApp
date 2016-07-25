package com.shiki.imgpicker.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shiki.imgpicker.FGOGalleryUtil;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.utils.DeviceUtils;
import com.std.ramsoapp.R;
import com.shiki.utils.adapter.ViewHolderRecyclingPagerAdapter;

import java.util.List;

/**
 * Created by Maik on 2016/2/26.
 */
public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<PhotoPreviewAdapter.PreviewViewHolder, PhotoInfo> {
    private Activity mActivity;
    private DisplayMetrics mDisplayMetrics;

    public PhotoPreviewAdapter(Activity activity, List<PhotoInfo> list) {
        super(activity, list);
        this.mActivity = activity;
        this.mDisplayMetrics = DeviceUtils.getScreenPix(mActivity);
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = getLayoutInflater().inflate(R.layout.fgo_photo_preview_vp_item, null);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreviewViewHolder holder, int position) {
        PhotoInfo photoInfo = getDatas().get(position);
        String path = "";
        if (photoInfo != null) {
            path = photoInfo.getPhotoPath();
        }
        Drawable defaultDrawable = mActivity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
        FGOGalleryUtil.displayImage(mActivity, path, holder.mImageView, defaultDrawable, mDisplayMetrics.widthPixels / 2, mDisplayMetrics.heightPixels / 2);
    }

    public static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder {
        SimpleDraweeView mImageView;

        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (SimpleDraweeView) view;
        }
    }
}
