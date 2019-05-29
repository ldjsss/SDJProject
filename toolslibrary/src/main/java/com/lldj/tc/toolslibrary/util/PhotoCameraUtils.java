package com.lldj.tc.toolslibrary.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.io.File;
import java.util.List;

public class PhotoCameraUtils {
    public static PhotoCameraUtils instance;
    //图片文件
    private File mImageFile;
    private String takePhotoFileName = "takePhoto.jpg";

    public static PhotoCameraUtils getInstance() {
        if (null == instance) {
            synchronized (PhotoCameraUtils.class) {
                if (null == instance) {
                    instance = new PhotoCameraUtils();
                }
            }
        }
        return instance;
    }

    //选取相册图片
    public void choosePhoto(Activity pActivity) {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        pActivity.startActivityForResult(intentToPickPic, GlobalVariable.GALLERY_REQUEST_CODE);
    }

    //打开照相机
    public void takePhoto(Activity pActivity) {
        if (FileUtil.isExistSdcard(pActivity)) {
            Uri imageUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mImageFile = new File(pActivity.getCacheDir(), System.currentTimeMillis() + takePhotoFileName);
                imageUri = FileProvider.getUriForFile(pActivity, "com.starshow.auction.fileprovider", mImageFile);
            } else {
                mImageFile = new File(ImageUtil.getCacheDirectory(pActivity), System.currentTimeMillis() + takePhotoFileName);
                imageUri = Uri.fromFile(mImageFile);
            }
            Intent intent = getTakePickIntent(pActivity, imageUri);
            pActivity.startActivityForResult(intent, GlobalVariable.CAMERA_REQUEST_CODE);
        } else {
            ToastUtils.show_middle(pActivity, "存储卡错误", ToastUtils.LENGTH_SHORT);
        }
    }

    private Intent getTakePickIntent(Activity pActivity, Uri pUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager pm = pActivity.getPackageManager();
        List<ResolveInfo> listCam = pm.queryIntentActivities(intent, 0);
        if (null != listCam) {
            for (ResolveInfo res : listCam) {
                //Clog.i(TAG, "getTakePickIntent packageName[" + res.activityInfo.packageName + "] name[" + res.activityInfo.name + "]");
                if (res.activityInfo.packageName.contains("com.sec.android.app.camera") || res.activityInfo.packageName.contains("com.android.app.camera")
                        || res.activityInfo.packageName.contains("com.android.camera") || res.activityInfo.packageName.contains("com.miui.camera")) {
                    intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    break;
                }
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pUri);
        return intent;
    }

    public String getImageUrl() {
        return mImageFile.getPath();
    }


}
