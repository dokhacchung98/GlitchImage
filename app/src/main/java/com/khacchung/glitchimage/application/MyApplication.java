package com.khacchung.glitchimage.application;

import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {
    private static MyApplication instance;
    public static int imgWidth = 100;
    public static int imgHeight = 100;

    public static int countAction = 0;

    public static void addCountAction() {
        countAction++;
        if (countAction >= 4) {
            countAction = 0;
        }
    }

    private String pathImage = "";
    private Bitmap imgBMP;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
    }

    public void setPathImage(String path) {
        pathImage = path;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setImgBMP(Bitmap imgBMP) {
        this.imgBMP = imgBMP;
    }

    public Bitmap getImgBMP() {
        return imgBMP;
    }
}
