package com.std.ramsoapp.domain;

/**
 * Created by Maik on 2016/3/17.
 */
public class RequireInfo extends ROResult {
    private String taskId;
    private String checkProject;
    private String confirmRmk;
    private String imageName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCheckProject() {
        return checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public String getConfirmRmk() {
        return confirmRmk;
    }

    public void setConfirmRmk(String confirmRmk) {
        this.confirmRmk = confirmRmk;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
