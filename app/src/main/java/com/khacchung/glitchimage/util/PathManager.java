package com.khacchung.glitchimage.util;

import android.content.Context;

import java.io.File;

public class PathManager {
    public static final String FOLDER_VIDEO = "Videos";
    public static final String FOLDER_IMAGE = "Images";

    public static String getPathFolder(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/GlitchImage/";
    }

    private Context context;

    public PathManager(Context context) {
        this.context = context;
        createFolderImage();
        createFolderVideo();
    }

    public void createFolderImage() {
        File fileImg = new File(getPathFolder(context) + FOLDER_IMAGE);
        if (!fileImg.exists()) {
            fileImg.mkdirs();
        }
    }

    public void createFolderVideo() {
        File fileVideo = new File(getPathFolder(context) + FOLDER_VIDEO);
        if (!fileVideo.exists()) {
            fileVideo.mkdirs();
        }
    }

    public boolean checkFolderExists(String folderName) {
        File f = new File(getPathFolder(context) + folderName);
        if (f.exists()) {
            return true;
        }
        return false;
    }
}