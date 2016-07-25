package com.shiki.imgpicker;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.shiki.imgpicker.domain.PhotoFolderInfo;
import com.shiki.imgpicker.domain.PhotoInfo;
import com.std.ramsoapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maik on 2016/2/29.
 */
public class FGOGalleryUtil {

    public static List<PhotoFolderInfo> getAllPhotoFolder(Context context, Map<String, PhotoInfo> selectPhotoMap) {
        List<PhotoFolderInfo> allFolderList = new ArrayList<>();
        final String[] projectionPhotos = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Thumbnails.DATA
        };
        final ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
        HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();
        Cursor cursor = null;
        PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
        allPhotoFolderInfo.setFolderId(0);
        allPhotoFolderInfo.setFolderName(context.getResources().getString(R.string.all_photo));
        allPhotoFolderInfo.setPhotoList(new ArrayList<PhotoInfo>());
        allPhotoFolderList.add(0, allPhotoFolderInfo);
        List<String> selectedList = FGOGallery.getCurrentFunctionConfig().getSelectedList();
        try {
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                while (cursor.moveToNext()) {
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    File file = new File(path);
                    if (file.exists() && file.length() > 0) {
                        final PhotoInfo photoInfo = new PhotoInfo();
                        photoInfo.setPhotoId(imageId);
                        photoInfo.setPhotoPath(path);
                        if (allPhotoFolderInfo.getCoverPhoto() == null) {
                            allPhotoFolderInfo.setCoverPhoto(photoInfo);
                        }
                        allPhotoFolderInfo.getPhotoList().add(photoInfo);
                        PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);
                        if (photoFolderInfo == null) {
                            photoFolderInfo = new PhotoFolderInfo();
                            photoFolderInfo.setPhotoList(new ArrayList<PhotoInfo>());
                            photoFolderInfo.setFolderId(bucketId);
                            photoFolderInfo.setFolderName(bucketName);
                            photoFolderInfo.setCoverPhoto(photoInfo);
                            bucketMap.put(bucketId, photoFolderInfo);
                            allPhotoFolderList.add(photoFolderInfo);
                        }
                        photoFolderInfo.getPhotoList().add(photoInfo);
                        if (selectedList != null && selectedList.size() > 0 && selectedList.contains(path)) {
                            selectPhotoMap.put(path, photoInfo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        allFolderList.addAll(allPhotoFolderList);
        /*if (selectedList != null) {
            selectedList.clear();
        }*/
        return allFolderList;
    }

    public static void displayImage(Activity activity, String path, final SimpleDraweeView imageView, final Drawable defaultDrawable, int width, int height) {
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(activity.getResources())
                .setFadeDuration(300)
                .setPlaceholderImage(defaultDrawable)
                .setFailureImage(defaultDrawable)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        imageView.setHierarchy(hierarchy);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(path)
                .build();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))//图片目标大小
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(imageView.getController())
                .setImageRequest(imageRequest)
                .build();
        imageView.setController(controller);
    }
}
