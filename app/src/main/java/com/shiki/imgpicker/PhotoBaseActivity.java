package com.shiki.imgpicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Window;
import android.widget.Toast;

import com.shiki.imgpicker.domain.PhotoInfo;
import com.shiki.utils.DateUtils;
import com.shiki.utils.DeviceUtils;
import com.shiki.utils.io.FileUtils;
import com.std.ramsoapp.R;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * Created by Maik on 2016/2/26.
 */
public abstract class PhotoBaseActivity extends Activity {
    static final int TAKE_REQUEST_CODE = 2000;
    private Uri mTakePhotoUri;
    private MediaScanner mMediaScanner;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("takePhotoUri", mTakePhotoUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTakePhotoUri = savedInstanceState.getParcelable("takePhotoUri");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mMediaScanner = new MediaScanner(this);
    }

    protected void takePhotoAction() {
        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(this, R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }
        File takePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/RamsoApp/");
        if (!takePhotoFolder.exists()) {
            takePhotoFolder.mkdirs();
        }
        boolean isSucess = FileUtils.mkdirs(takePhotoFolder);
        File toFile = new File(takePhotoFolder, "IMG" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + ".jpg");
        if (isSucess) {
            mTakePhotoUri = Uri.fromFile(toFile);
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
            startActivityForResult(captureIntent, PhotoBaseActivity.TAKE_REQUEST_CODE);
        } else {
            Toast.makeText(this, R.string.take_photo_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PhotoBaseActivity.TAKE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && mTakePhotoUri != null) {
                final String path = mTakePhotoUri.getPath();
                if (new File(path).exists()) {
                    final PhotoInfo info = new PhotoInfo();
                    info.setPhotoId(getRandom(10000, 99999));
                    info.setPhotoPath(path);
                    updateGallery(path);
                    takeResult(info);
                } else {
                    Toast.makeText(this, R.string.take_photo_fail, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.take_photo_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getRandom(int min, int max) {
        Random random = new Random();
        int result = random.nextInt(max) % (max - min + 1) + min;
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaScanner != null) {
            mMediaScanner.unScanFile();
        }
        finish();
    }

    private void updateGallery(String filePath) {
        if (mMediaScanner != null) {
            mMediaScanner.scanFile(filePath, "image/jpeg");
        }
    }

    protected abstract void takeResult(PhotoInfo info);
}
