package com.shiki.imgpicker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiki.imgpicker.adapter.PhotoPreviewAdapter;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.std.ramsoapp.R;

import java.util.List;

/**
 * Created by Maik on 2016/2/26.
 */
public class PhotoPreviewActivity extends Activity implements ViewPager.OnPageChangeListener {
    static final String PHOTO_LIST = "photo_list";
    private RelativeLayout mTitleBar;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvIndicator;
    private ViewPager mVpPager;
    private List<PhotoInfo> mPhotoList;
    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fgo_photo_preview);
        findViews();
        setListener();
        mPhotoList = (List<PhotoInfo>) getIntent().getSerializableExtra(PHOTO_LIST);
        mPhotoPreviewAdapter = new PhotoPreviewAdapter(this, mPhotoList);
        mVpPager.setAdapter(mPhotoPreviewAdapter);
    }

    private void findViews() {
        mTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvIndicator = (TextView) findViewById(R.id.tv_indicator);
        mVpPager = (ViewPager) findViewById(R.id.vp_pager);
    }

    private void setListener() {
        mVpPager.addOnPageChangeListener(this);
        mIvBack.setOnClickListener(mBackListener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTvIndicator.setText((position + 1) + "/" + mPhotoList.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
