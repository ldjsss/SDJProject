package com.lldj.tc.firstpage.banner;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lldj.tc.R;


import java.util.List;

public enum MyBannerUtils {
    INSTANCE;
    public void initBanner(final Context mContext, ConvenientBanner convenientBanner, final List<BannerInfo> pBannerDataList) {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public BannerHolderView createHolder(View itemView) {
                        return new BannerHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.firstpage_banner_item_layout;
                    }
                }, pBannerDataList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.my_dot, R.mipmap.my_dot2})
//                .setOnItemClickListener(this);
           //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
        ;
//        if (!bannerRe.isTurning()) {
//            bannerRe.setPages(
//                    new CBViewHolderCreator<BannerHolderView>() {
//                        @Override
//                        public BannerHolderView createHolder() {
//                            return new BannerHolderView();
//                        }
//                    }, pBannerDataList)
//                    .setPageIndicator(new int[]{R.mipmap.my_dot, R.mipmap.my_dot2})
//                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
//        } else {
//            bannerRe.setPages(
//                    new CBViewHolderCreator<BannerHolderView>() {
//                        @Override
//                        public BannerHolderView createHolder() {
//                            return new BannerHolderView();
//                        }
//                    }, pBannerDataList);
//            bannerRe.notifyDataSetChanged();
//        }
//        bannerRe.setPointViewVisible(pBannerDataList.size() > 1);
//        if (pBannerDataList.size() == 1) {
//            bannerRe.stopTurning();
//            bannerRe.setCanLoop(false);
//        } else {
//            if (!bannerRe.isTurning())
//                bannerRe.startTurning(3000);
//            bannerRe.setCanLoop(true);
//        }
//        bannerRe.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                BannerInfo minfo = pBannerDataList.get(position);
////                minfo.bannerClick(mContext, minfo, fromClass, pFromFragment);
//            }
//        });
    }

}
