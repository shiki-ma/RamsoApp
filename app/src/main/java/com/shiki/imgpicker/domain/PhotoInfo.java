package com.shiki.imgpicker.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maik on 2016/2/23.
 */
public class PhotoInfo implements Parcelable {
    private int photoId;
    private String photoPath;
    private int width;
    private int height;
    private String tag;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photoId);
        dest.writeString(this.photoPath);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.tag);
    }

    public PhotoInfo() {
    }

    protected PhotoInfo(Parcel in) {
        this.photoId = in.readInt();
        this.photoPath = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.tag = in.readString();
    }

    public static final Parcelable.Creator<PhotoInfo> CREATOR = new Parcelable.Creator<PhotoInfo>() {
        @Override
        public PhotoInfo createFromParcel(Parcel source) {
            return new PhotoInfo(source);
        }

        @Override
        public PhotoInfo[] newArray(int size) {
            return new PhotoInfo[size];
        }
    };
}
