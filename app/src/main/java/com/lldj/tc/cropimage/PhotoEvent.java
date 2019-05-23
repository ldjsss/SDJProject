package com.lldj.tc.cropimage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 2016/2/24.
 */
public class PhotoEvent {
    //从哪个页面过来的
    private String mClassName = null;
    //canceled选择照片取消 ,ok 选择ok
    private String mActionName = null;
    //照片为多选的数组类似博爱
    private List<String> mSelectPhotoPath = null;
    //裁切完后的图片路径
    private String photoPath = null;

    public PhotoEvent(String photoPath, String pClassName, String pActionName) {
        this.photoPath = photoPath;
        this.mClassName = pClassName;
        this.mActionName = pActionName;
    }

    public PhotoEvent(List<String> mSelectPhotoPath, String mClassName, String pActionName) {
        this.mClassName = mClassName;
        this.mSelectPhotoPath = mSelectPhotoPath;
    }

    public PhotoEvent(String pphotoPath) {
        this.photoPath = pphotoPath;
    }

    public List<String> getmSelectPhotoPath() {
        if (null == mSelectPhotoPath) {
            mSelectPhotoPath = new ArrayList<>();
        }
        return mSelectPhotoPath;
    }

    public void setmSelectPhotoPath(List<String> mSelectPhotoPath) {
        this.mSelectPhotoPath = mSelectPhotoPath;
    }

    public String getmClassName() {
        if (null == mClassName) {
            mClassName = "";
        }
        return mClassName;
    }

    public void setmClassName(String mClassName) {
        this.mClassName = mClassName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getmActionName() {
        return mActionName;
    }
}
