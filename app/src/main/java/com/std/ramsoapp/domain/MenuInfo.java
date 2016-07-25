package com.std.ramsoapp.domain;

/**
 * Created by Maik on 2016/2/1.
 */
public class MenuInfo extends ROResult {
    private String title;
    private String action;
    private int imgResource;
    private String rootClass;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public String getRootClass() {
        return rootClass;
    }

    public void setRootClass(String rootClass) {
        this.rootClass = rootClass;
    }
}
