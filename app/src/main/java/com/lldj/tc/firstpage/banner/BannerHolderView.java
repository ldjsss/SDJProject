package com.lldj.tc.firstpage.banner;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.util.ScreenUtil;

/**
 * Created by wang on 2016/11/8.
 */

public class BannerHolderView extends Holder<BannerInfo> {
    private RelativeLayout mImgLayout;
    private Context mContext;

    public BannerHolderView(View itemView) {
        super(itemView);
    }

/*    @Override
    public View createView(Context context) {
        mContext = context;
        View view = View.inflate(context, R.layout.firstpage_banner_item_layout, null);
        mImgLayout = view.findViewById(R.id.img_layout);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerInfo data) {
        int mWidth = ScreenUtil.getScreenWidth(mContext);
        int mHeight = mWidth * 200 / 375;
        ScreenUtil.setLinearLayoutParams(mImgLayout, mWidth, mHeight);

//        GlideUtil.normalPicCenterCrop_43_750_563(context, data.getCover(), imageView);
//        typeName.setVisibility(View.GONE);
//            if (data.getStatus().equals("1")) {
//                typeName.setVisibility(View.VISIBLE);
//                typeName.setText("直播中");
//            } else if (data.getStatus().equals("3")) {
//                typeName.setVisibility(View.VISIBLE);
//                typeName.setText("直播预告");
//            } else {
//                typeName.setVisibility(View.GONE);
//                typeName.setText("");
//            }
    }*/

    @Override
    protected void initView(View itemView) {
        mImgLayout = itemView.findViewById(R.id.img_layout);
    }

    @Override
    public void updateUI(BannerInfo data) {
        int mWidth = ScreenUtil.getScreenWidth(mContext);
        int mHeight = mWidth * 200 / 375;
        ScreenUtil.setLinearLayoutParams(mImgLayout, mWidth, mHeight);
    }
}
