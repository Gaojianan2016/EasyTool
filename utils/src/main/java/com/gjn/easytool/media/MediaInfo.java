package com.gjn.easytool.media;

/**
 * Creator: Gjn
 * Time: 2019/12/16 14:57
 * Eamil: 732879625@qq.com
 **/
public class MediaInfo {
    private String path;
    private String name;
    private String parent;
    private String parentPath;
    private String mimeType;
    private int width;
    private int height;
    private String resolution;
    private int size;
    private int time;
    private int orientation;
    private int rotation;
    private String thumbnailPath;
    private boolean isVideo;
    private int dirSize;
    private Object otherObj;

    public MediaInfo() {
    }

    public MediaInfo(String path, String name, String parent, String parentPath, int size) {
        this.path = path;
        this.name = name;
        this.parent = parent;
        this.parentPath = parentPath;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public int getDirSize() {
        return dirSize;
    }

    public void setDirSize(int dirSize) {
        this.dirSize = dirSize;
    }

    public Object getOtherObj() {
        return otherObj;
    }

    public void setOtherObj(Object otherObj) {
        this.otherObj = otherObj;
    }
}
