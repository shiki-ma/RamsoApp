package com.shiki.imgpicker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiki.imgpicker.adapter.PhotoListAdapter;
import com.shiki.imgpicker.domain.PhotoFolderInfo;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.std.ramsoapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maik on 2016/2/24.
 */
public class PhotoSelectActivity extends PhotoBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final int HANLDER_TAKE_PHOTO_EVENT = 1000;
    private final int HANDLER_REFRESH_LIST_EVENT = 1001;

    private GridView mGvPhotoList;
    private ListView mLvFolderList;
    private LinearLayout mLlFolderPanel;//文件夹选择暂时不实现
    private ImageView mIvTakePhoto;
    private ImageView mIvBack;
    private ImageView mIvClear;
    private ImageView mIvPreView;
    private ImageView mIvDone;
    private TextView mTvChooseCount;
    private TextView mTvSubTitle;//文件夹选择暂时不实现
    private LinearLayout mLlTitle;
    private TextView mTvEmptyView;
    private ImageView mIvFolderArrow;

    private List<PhotoFolderInfo> mAllPhotoFolderList;
    private List<PhotoInfo> mCurPhotoList;
    private PhotoListAdapter mPhotoListAdapter;
    private HashMap<String, PhotoInfo> mSelectPhotoMap = new HashMap<>();

    private FunctionConfig config;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectPhotoMap", mSelectPhotoMap);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectPhotoMap = (HashMap<String, PhotoInfo>) savedInstanceState.getSerializable("selectPhotoMap");
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANLDER_TAKE_PHOTO_EVENT) {
                PhotoInfo photoInfo = (PhotoInfo) msg.obj;
                takeRefreshGallery(photoInfo);
                refreshSelectCount();
            } else if (msg.what == HANDLER_REFRESH_LIST_EVENT) {
                refreshSelectCount();
                mPhotoListAdapter.notifyDataSetChanged();
                if (mAllPhotoFolderList.get(0).getPhotoList() == null ||
                        mAllPhotoFolderList.get(0).getPhotoList().size() == 0) {
                    mTvEmptyView.setText(R.string.no_photo);
                }
                mGvPhotoList.setEnabled(true);
                mLlTitle.setEnabled(true);
                mIvTakePhoto.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fgo_photo_select);

        config = FGOGallery.getCurrentFunctionConfig();

        findViews();
        setListener();
        mAllPhotoFolderList = new ArrayList<>();
        mCurPhotoList = new ArrayList<>();
        mPhotoListAdapter = new PhotoListAdapter(this, mCurPhotoList, mSelectPhotoMap, 720);
        mGvPhotoList.setAdapter(mPhotoListAdapter);
        mGvPhotoList.setEmptyView(mTvEmptyView);
        getPhotos();
    }

    private void setListener() {
        mLlTitle.setOnClickListener(this);
        mIvTakePhoto.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mIvFolderArrow.setOnClickListener(this);
        mLvFolderList.setOnItemClickListener(this);
        mGvPhotoList.setOnItemClickListener(this);
        mIvClear.setOnClickListener(this);
        mIvPreView.setOnClickListener(this);
        mIvDone.setOnClickListener(this);
    }

    private void findViews() {
        mGvPhotoList = (GridView) findViewById(R.id.gv_photo_list);
        mLvFolderList = (ListView) findViewById(R.id.lv_folder_list);
        mLlFolderPanel = (LinearLayout) findViewById(R.id.ll_folder_panel);
        mIvTakePhoto = (ImageView) findViewById(R.id.iv_take_photo);
        mTvChooseCount = (TextView) findViewById(R.id.tv_choose_count);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        mLlTitle = (LinearLayout) findViewById(R.id.ll_title);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvFolderArrow = (ImageView) findViewById(R.id.iv_folder_arrow);
        mIvPreView = (ImageView) findViewById(R.id.iv_preview);
        mIvDone = (ImageView) findViewById(R.id.iv_done);
    }

    private void getPhotos() {
        mTvEmptyView.setText(R.string.waiting);
        mGvPhotoList.setEnabled(false);
        mLlTitle.setEnabled(false);
        mIvTakePhoto.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAllPhotoFolderList.clear();
                List<PhotoFolderInfo> allFolderList = FGOGalleryUtil.getAllPhotoFolder(PhotoSelectActivity.this, mSelectPhotoMap);
                mAllPhotoFolderList.addAll(allFolderList);
                mCurPhotoList.clear();
                if (allFolderList.size() > 0) {
                    if (allFolderList.get(0).getPhotoList() != null) {
                        mCurPhotoList.addAll(allFolderList.get(0).getPhotoList());
                    }
                }
                refreshAdapter();
            }
        }.start();
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_preview) {
            Intent intent = new Intent(this, PhotoPreviewActivity.class);
            intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, new ArrayList<>(mSelectPhotoMap.values()));
            startActivity(intent);
        } else if (id == R.id.iv_clear) {
            mSelectPhotoMap.clear();
            mPhotoListAdapter.notifyDataSetChanged();
            refreshSelectCount();
        } else if (id == R.id.iv_take_photo) {
            if (config.isMutiSelect() && mSelectPhotoMap.size() == config.getMaxSize()) {
                Toast.makeText(this, R.string.select_max_tips, Toast.LENGTH_SHORT).show();
                return;
            }
            takePhotoAction();
        } else if (id == R.id.iv_done) {
            if(mSelectPhotoMap.size() > 0) {
                ArrayList<PhotoInfo> photoList = new ArrayList<>(mSelectPhotoMap.values());
                resultData(photoList);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo info = mCurPhotoList.get(position);
        if (!config.isMutiSelect()) {
            mSelectPhotoMap.clear();
            mSelectPhotoMap.put(info.getPhotoPath(), info);
            ArrayList<PhotoInfo> list = new ArrayList<>();
            list.add(info);
            resultData(list);
            return;
        }
        boolean checked = false;
        if (mSelectPhotoMap.get(info.getPhotoPath()) == null) {
            if (config.isMutiSelect() && mSelectPhotoMap.size() == config.getMaxSize()) {
                Toast.makeText(this, R.string.select_max_tips, Toast.LENGTH_SHORT).show();
                return;
            } else {
                mSelectPhotoMap.put(info.getPhotoPath(), info);
                checked = true;
            }
        } else {
            mSelectPhotoMap.remove(info.getPhotoPath());
            checked = false;
        }
        refreshSelectCount();
        PhotoListAdapter.PhotoViewHolder holder = (PhotoListAdapter.PhotoViewHolder) view.getTag();
        if (holder != null) {
            if (checked) {
                holder.mIvCheck.setBackgroundColor(Color.rgb(0x3F, 0x51, 0xB5));
            } else {
                holder.mIvCheck.setBackgroundColor(Color.rgb(0xd2, 0xd2, 0xd7));
            }
        } else {
            mPhotoListAdapter.notifyDataSetChanged();
        }
    }

    public void refreshSelectCount() {
        mTvChooseCount.setText(getString(R.string.selected, mSelectPhotoMap.size(), config.getMaxSize()));
        if (mSelectPhotoMap.size() > 0 && config.isMutiSelect()) {
            mIvClear.setVisibility(View.VISIBLE);
            mIvPreView.setVisibility(View.VISIBLE);
            mIvDone.setVisibility(View.VISIBLE);
        } else {
            mIvClear.setVisibility(View.GONE);
            mIvPreView.setVisibility(View.GONE);
            mIvDone.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectPhotoMap.clear();
        System.gc();
    }

    @Override
    protected void takeResult(PhotoInfo photoInfo) {
        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANLDER_TAKE_PHOTO_EVENT;
        if (!config.isMutiSelect()) {
            mSelectPhotoMap.clear();
            mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
            ArrayList<PhotoInfo> list = new ArrayList<>();
            list.add(photoInfo);
            resultData(list);
            mHanlder.sendMessageDelayed(message, 100);
        } else {
            mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
            mHanlder.sendMessageDelayed(message, 100);
        }
    }

    private void takeRefreshGallery(PhotoInfo photoInfo) {
        mCurPhotoList.add(0, photoInfo);
        mPhotoListAdapter.notifyDataSetChanged();
    }

    protected void resultData(ArrayList<PhotoInfo> photoList) {
        FGOGallery.OnHanlderResultCallback callback = FGOGallery.getCallback();
        if (callback != null) {
            if (photoList != null && photoList.size() > 0) {
                callback.onHanlderSuccess(photoList);
            } else {
                callback.onHanlderFailure(getString(R.string.photo_list_fail));
            }
        }
        finish();
    }
}
