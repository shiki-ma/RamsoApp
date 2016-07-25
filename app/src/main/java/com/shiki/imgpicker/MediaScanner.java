package com.shiki.imgpicker;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * Created by Maik on 2016/2/26.
 */
public class MediaScanner {
    private MediaScannerConnection mediaScanConn = null;
    private MusicSannerClient client = null;
    private String filePath = null;
    private String fileType = null;
    private String[] filePaths = null;

    public MediaScanner(Context context) {
        if (client == null) {
            client = new MusicSannerClient();
        }
        if (mediaScanConn == null) {
            mediaScanConn = new MediaScannerConnection(context, client);
        }
    }

    class MusicSannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
        public void onMediaScannerConnected() {
            if (filePath != null) {
                mediaScanConn.scanFile(filePath, fileType);
            }
            if (filePaths != null) {
                for (String file : filePaths) {
                    mediaScanConn.scanFile(file, fileType);
                }
            }
            filePath = null;
            fileType = null;
            filePaths = null;
        }

        public void onScanCompleted(String path, Uri uri) {
            mediaScanConn.disconnect();
        }
    }

    public void scanFile(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
        mediaScanConn.connect();
    }

    public void scanFile(String[] filePaths, String fileType) {
        this.filePaths = filePaths;
        this.fileType = fileType;
        mediaScanConn.connect();
    }

    public void unScanFile() {
        mediaScanConn.disconnect();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
