package com.lldj.tc.photo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lldj.tc.cropimage.CropImageActivity;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.immersionbar.ImmersionBar;
import com.lldj.tc.toolslibrary.util.FileUtil;
import com.lldj.tc.toolslibrary.util.ImageUtil;
import com.lldj.tc.toolslibrary.util.PermissionUtils;
import com.lldj.tc.toolslibrary.util.ScreenUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.util.GlobalVariable;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: <p>
 * user: lenovo<p>
 * Creat Time: 2018/12/7 17:24<p>
 * Modify Time: 2018/12/7 17:24<p>
 */


public class OpenCameraOrGalleryActivity extends BaseActivity implements PermissionUtils.PermissionGrant {

    @BindView(R.id.toolbar_back_iv)
    ImageView toolbarBackIv;
    @BindView(R.id.take_photo)
    TextView takePhoto;
    @BindView(R.id.selectd_from_camera_tv)
    TextView selectdFromCameraTv;
    @BindView(R.id.cancel_tv)
    TextView cancelTv;
    @BindView(R.id.edit_root_layout)
    LinearLayout editRootLayout;
    @BindView(R.id.toolbar_layout)
    RelativeLayout toolbarLayout;
    //图片文件
    private File mImageFile;
    private String takePhotoFileName = "takePhoto.jpg";
    // 拍照回传码
    public final static int CAMERA_REQUEST_CODE = 0;
    // 相册选择回传吗
    public final static int GALLERY_REQUEST_CODE = 1;
    // 拍照的照片的存储位置
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    private Uri imageUri;


    private int mCropWidth;
    private int mCropHeight;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, OpenCameraOrGalleryActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opencamera_or_gallery);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(toolbarLayout).init();
        int mScreenWidth = ScreenUtil.getScreenWidth(mContext);
        mCropWidth = mScreenWidth;
        mCropHeight = mScreenWidth;
    }


    // 手机拍照
    public void goCamera() {
        int permissionCheck;
        try {
            permissionCheck = ActivityCompat.checkSelfPermission(OpenCameraOrGalleryActivity.this, Manifest.permission.CAMERA);
        } catch (RuntimeException e) {
            ToastUtils.show_middle_pic(this, R.mipmap.cancle_icon, getString(R.string.permission_camera_exception), ToastUtils.LENGTH_SHORT);
            takePhoto();
            return;
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(OpenCameraOrGalleryActivity.this, PermissionUtils.CODE_CAMERA, this);
        } else {
            takePhoto();
        }
    }

    //打开照相机
    private void takePhoto() {
        if (FileUtil.isExistSdcard(this)) {
            Uri imageUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mImageFile = new File(getCacheDir(), System.currentTimeMillis() + takePhotoFileName);
                imageUri = FileProvider.getUriForFile(OpenCameraOrGalleryActivity.this, "com.starshow.auction.fileprovider", mImageFile);
            } else {
                mImageFile = new File(ImageUtil.getCacheDirectory(this), System.currentTimeMillis() + takePhotoFileName);
                imageUri = Uri.fromFile(mImageFile);
            }
            Intent intent = getTakePickIntent(imageUri);
            startActivityForResult(intent, GlobalVariable.CAMERA_REQUEST_CODE);
        } else {
            ToastUtils.show_middle(mContext, "存储卡错误", ToastUtils.LENGTH_SHORT);
        }
    }

    private Intent getTakePickIntent(Uri pUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager pm = getPackageManager();
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

    //选取系统相册的权限申请
    private void choosePhotoPermission() {
        PermissionUtils.requestPermission(OpenCameraOrGalleryActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, this);
//        if (ContextCompat.checkSelfPermission(OpenCameraOrGalleryActivity.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
//            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
//            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
//            ActivityCompat.requestPermissions(OpenCameraOrGalleryActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    GlobalVariable.CAMERA_REQUEST_CODE);
//
//        } else { //权限已经被授予，在这里直接写要执行的相应方法即可
//            choosePhoto();
//        }

    }

    //选取相册图片
    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        startActivityForResult(intentToPickPic, GlobalVariable.GALLERY_REQUEST_CODE);
    }


    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                choosePhoto();
                break;
            case PermissionUtils.CODE_CAMERA:
                takePhoto();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionRefused() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GlobalVariable.CAMERA_REQUEST_CODE:
                if (resultCode == 0) {
                    finish();
                    return;
                }
                // 系统拍照
                if (mImageFile != null && resultCode == -1) {
//                    if (mIsCrop) {
//                        mHandler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
                    CropImageActivity.launch(OpenCameraOrGalleryActivity.this, OpenCameraOrGalleryActivity.class.getName(), mImageFile.getPath(), mCropWidth, mCropHeight);
                    finish();
//                            }
//                        }, 500);
//                    } else {
//                        mSelectedPhotos.add(mImageFile.getPath());
//                        ensure_action();
//                    }
                }
                break;
            case GlobalVariable.GALLERY_REQUEST_CODE:
                // 获取图片
                try {
                    //该uri是上一个Activity返回的
                    imageUri = data.getData();
                    if (imageUri != null) {
                        String pPath = FileUtil.getRealFilePath(mContext, imageUri);
                        CropImageActivity.launch(OpenCameraOrGalleryActivity.this, OpenCameraOrGalleryActivity.class.getName(), pPath, mCropWidth, mCropHeight);
                        finish();

//                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        fragment4ImageView0.setImageBitmap(bit);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case REQUEST_PHOTO_PREVIEW:
//                mSelectedPhotos = data.getStringArrayListExtra("selectList");
//                ArrayList<PhotoInfo> mendPhotoList = data.getParcelableArrayListExtra("currentPhotoList");
//                if (mendPhotoList.size() < mCurPhotoList.size()) {
//                    for (int i = 0; i < mCurPhotoList.size(); i++) {
//                        for (int j = 0; j < mendPhotoList.size(); j++) {
//                            if (mCurPhotoList.get(i).getPhotoPath().equals(mendPhotoList.get(j).getPhotoPath())) {
//                                mCurPhotoList.set(i, mendPhotoList.get(j));
//                                break;
//                            }
//                            if (j == mendPhotoList.size() - 1) {
//                                mCurPhotoList.get(i).setChecked(false);
//                            }
//
//                        }
//                    }
//
//
//                } else {
//                    mCurPhotoList = mendPhotoList;
//                }
//                switch (resultCode) {
//                    case PhotoViewActivity.SEND_RESULT_CODE:
//                        ensure_action();
//                        break;
//                    case PhotoViewActivity.back_RESULT_CODE:
//                        mAdapter.changeData(mCurPhotoList);
//                        setBtnStatus(mSelectedPhotos.size());
//                        break;
//                }
//
//                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick({R.id.take_photo, R.id.selectd_from_camera_tv, R.id.cancel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                goCamera();
                break;
            case R.id.selectd_from_camera_tv:
                choosePhotoPermission();
                break;
            case R.id.cancel_tv:
                finish();
                overridePendingTransition(0, 0);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, this);

    }
}
