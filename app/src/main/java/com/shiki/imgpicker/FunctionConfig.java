package com.shiki.imgpicker;

import com.shiki.imgpicker.domain.PhotoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/2/23.
 */
public class FunctionConfig {
    private boolean mutiSelect;
    private int maxSize;
    private boolean camera;
    private boolean preview;
    private ArrayList<String> selectedList;

    public boolean isMutiSelect() {
        return mutiSelect;
    }

    public void setMutiSelect(boolean mutiSelect) {
        this.mutiSelect = mutiSelect;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isPreview() {
        return preview;
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<PhotoInfo> selectedList) {
        if (selectedList != null) {
            ArrayList<String> list = new ArrayList<>();
            for (PhotoInfo info : selectedList) {
                if (info != null) {
                    list.add(info.getPhotoPath());
                }
            }
            this.selectedList = list;
        }
    }
}
