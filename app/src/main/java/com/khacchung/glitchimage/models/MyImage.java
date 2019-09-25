package com.khacchung.glitchimage.models;

public class MyImage {
    private String pathImage;
    private String pathParent;

    public MyImage(String pathImage, String pathParent) {
        this.pathImage = pathImage;
        this.pathParent = pathParent;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getPathParent() {
        return pathParent;
    }

    public void setPathParent(String pathParent) {
        this.pathParent = pathParent;
    }
}
