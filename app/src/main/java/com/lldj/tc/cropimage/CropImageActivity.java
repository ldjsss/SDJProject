package com.lldj.tc.cropimage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.Clog;
import com.lldj.tc.toolslibrary.util.FileUtil;
import com.lldj.tc.toolslibrary.util.ImageUtil;
import com.lldj.tc.toolslibrary.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.ButterKnife;

public class CropImageActivity extends BaseActivity implements View.OnClickListener {
    public static final String FROMCLASSNAME = "fromclassname";
    CropImageView cropImg;
    TextView cancel;
    TextView ok;
    /**
     * 图片的路径
     */
    private String img_url;
    /**
     * 缓存的图片
     */
    private Bitmap tmpBitmap = null;

    private int mBitmapW = 1024;
    private int mBitmapH = 1024;
    private String mFromClassName = "";


    public static void launch(Activity context, String pFromClassName, String img_url, int targetWidth, int targetHeight) {
        Intent intent = new Intent(context, CropImageActivity.class);
        intent.putExtra("img_url", img_url);
        intent.putExtra(FROMCLASSNAME, pFromClassName);
        intent.putExtra("targetWidth", targetWidth);
        intent.putExtra("targetHeight", targetHeight);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_image_crop);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        cropImg = (CropImageView) findViewById(R.id.cropImg);
        cancel = (TextView) findViewById(R.id.cancel);
        ok = (TextView) findViewById(R.id.ok);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        img_url = mContext.getIntent().getStringExtra("img_url");
        mBitmapW = mContext.getIntent().getIntExtra("targetWidth", mBitmapW);
        mBitmapH = mContext.getIntent().getIntExtra("targetHeight", mBitmapH);
        mFromClassName = getIntent().getStringExtra(FROMCLASSNAME);
        if (img_url != "" || img_url != null) {
            tmpBitmap = ImageUtil.geAutoRotatedBitmap(img_url, mBitmapH, mBitmapW);
            if (null == tmpBitmap) {
                finish();

            }
            cropImg.setDrawable(new BitmapDrawable(getResources(), tmpBitmap), mBitmapW, mBitmapH);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tmpBitmap != null) {
            tmpBitmap.recycle();
            tmpBitmap = null;
        }
        if (cropImg != null) {
            cropImg = null;
        }
        System.gc();
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new PhotoEvent("canceled"));
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel) {
            EventBus.getDefault().post(new PhotoEvent("", mFromClassName, "canceled"));
            finish();
        } else if (id == R.id.ok) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String cacheDirectory = ImageUtil.getCacheDirectory(mContext) + File.separator + "crop";
                        FileUtil.deleteFile(cacheDirectory);
                        String fileName = System.currentTimeMillis() + ".jpg";
                        String destPath = cacheDirectory + File.separator + fileName;
                        FileUtil.writeImage(cropImg.getCropImage(), destPath, 100);
                        Clog.e("裁切后图片路径", destPath);
                        EventBus.getDefault().post(new PhotoEvent(destPath, mFromClassName, "ok"));
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }).start();
        }

    }
}
