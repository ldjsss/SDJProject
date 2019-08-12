package com.lldj.tc.match;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.event.ObData;
import com.lldj.tc.toolslibrary.event.Observable;
import com.lldj.tc.toolslibrary.event.Observer;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.lldj.tc.utils.EventType;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment_Banner extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_banner2)
    TextBannerView tvBanner2;
    private List<String> msgList;
    private int ViewType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewType = getArguments().getInt( "ARG");
    }

    @Override
    public int getContentView() {
        return R.layout.fragement_banner;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);

        initData();

        setListener();

        if(ViewType <= 0) tvBanner2.startViewAnimator();
    }

    private void initData() {
        msgList = new ArrayList<>();
        msgList.add("欢迎各位来到星云电竞！");
        msgList.add("Welcome to the NEBULA！");
        msgList.add("大家好，我是星云电竞，欢迎大家的到来！");
        msgList.add("Hello, everyone, I am NEBULA！");
        msgList.add("星云电竞！星云电竞！星云电竞！星云电竞！星云电竞！");
        /**
         * 设置数据，方式一
         */
        tvBanner2.setDatas(msgList);

//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /**
         * 设置数据（带图标的数据），方式二
         */
        //第一个参数：数据 。第二参数：drawable.  第三参数drawable尺寸。第四参数图标位置
//        mTvBanner2.setDatasWithDrawableIcon(msgList,drawable,18, Gravity.LEFT);
    }

    private void setListener() {
        tvBanner2.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(mContext, String.valueOf(position)+">>"+data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void show(Fragment fragment) {
        super.show(fragment);
        /**调用startViewAnimator()重新开始文字轮播*/
        tvBanner2.startViewAnimator();
    }

    @Override
    public void hide(Fragment fragment) {
        super.hide(fragment);
        /**调用stopViewAnimator()暂停文字轮播，避免文字重影*/
        tvBanner2.stopViewAnimator();
    }

    public void stopViewAnimator() {
        if(tvBanner2 != null)tvBanner2.stopViewAnimator();
    }

    public void startViewAnimator() {
        if(tvBanner2 != null){
            tvBanner2.startViewAnimator();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}