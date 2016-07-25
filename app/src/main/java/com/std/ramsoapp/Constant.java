package com.std.ramsoapp;

/**
 * Created by Maik on 2016/1/29.
 */
public class Constant {
    public static final String APK_NAME = "ramso.apk";

    public static final String URL_SERVER = "http://61.152.144.168:60001/ramso";
    //public static final String URL_SERVER = "http://10.0.3.2:8080/ramso";
    public static final String URL_REGISTER = URL_SERVER + "/service/foreign/LoginCheckServlet";
    public static final String URL_TASKLIST = URL_SERVER + "/task/foreign/TaskListServlet";
    public static final String URL_TASKDETAIL = URL_SERVER + "/task/foreign/TaskDetailServlet";
    public static final String URL_TASKREQUIRE = URL_SERVER + "/task/foreign/TaskRequiredListServlet";
    public static final String URL_TASKIMAGE = URL_SERVER + "/task/foreign/TaskImageListServlet";
    public static final String URL_TASKSUBMIT = URL_SERVER + "/task/foreign/TaskUploadServlet";
    public static final String URL_UPDATE = URL_SERVER + "/task/foreign/AppVersionServlet";
    public static final String URL_FAULTLIST = URL_SERVER + "/fault/foreign/RepairListServlet";
    public static final String URL_FAULTDETAIL = URL_SERVER + "/fault/foreign/RepairDetailServlet";
    public static final String URL_FAULTSUBMIT = URL_SERVER + "/fault/foreign/RepairUploadServlet";

    //登陆响应状态
    public static final String RES_SUCCESS = "0000";
    public static final String RES_FAILURE = "0001";

    //任务类型
    public static final String TASK_BEFORE_DEAL = "1";
    public static final String TASK_DEAL = "2";
    public static final String TASK_BEFORE_REVIEW = "3";
    public static final String TASK_AFTER_REVIEW = "4";
    public static final String TASK_SEARCH = "5";
    public static final String DEVICE_SEARCH = "6";

    //故障类型
    public static final String FAULT_BEFORE_DEAL = "1";
    public static final String FAULT_BEFORE_REVIEW = "2";
    public static final String FAULT_AFTER_REVIEW = "3";

    public static final int TASK_RESULT_STATE = 1000;
    public static final int FAULT_RESULT_STATE = 1001;
    public static final int DEVICE_RESULT_STATE = 1002;
}
