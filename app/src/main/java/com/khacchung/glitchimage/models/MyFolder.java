package com.khacchung.glitchimage.models;

public class MyFolder {
    private String pathFolder;
    private String nameFolder;
    private int sizeItem;
    private boolean isSelect = false;

    public MyFolder(String pathFolder, String nameFolder, int sizeItem) {
        this.pathFolder = pathFolder;
        this.nameFolder = nameFolder;
        this.sizeItem = sizeItem;
    }

    public String getPathFolder() {
        return pathFolder;
    }

    public void setPathFolder(String pathFolder) {
        this.pathFolder = pathFolder;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public int getSizeItem() {
        return sizeItem;
    }

    public void setSizeItem(int sizeItem) {
        this.sizeItem = sizeItem;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
