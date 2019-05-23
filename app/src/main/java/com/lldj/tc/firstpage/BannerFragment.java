package com.lldj.tc.firstpage;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseFragment;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BannerFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_banner2)
    TextBannerView tvBanner2;
    private List<String> msgList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewType = getArguments().getInt( "ARG");
//        Clog.e("sssssss", " -----------xcl onCreate = " + ViewType);
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

    }

    private void initData() {
        msgList = new ArrayList<>();
        msgList.add("学好Java、Android、C#、C、ios、html+css+js");
        msgList.add("走遍天下都不怕！！！！！");
        msgList.add("不是我吹，就怕你做不到，哈哈");
        msgList.add("superluo");
        msgList.add("你是最棒的，奔跑吧孩子！");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}