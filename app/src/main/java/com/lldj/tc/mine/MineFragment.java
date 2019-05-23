package com.lldj.tc.mine;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.lldj.tc.mine.info.InfoDetailActivity;
import com.lldj.tc.mine.msg.MsgActivity;
import com.lldj.tc.mine.qrcode.QRCodeActivity;
import com.lldj.tc.mine.verify.VerifyEventBus;
import com.lldj.tc.mine.verify.VerifyTipActivity;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
我的
 */
public class MineFragment extends BaseLazyFragment {


    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.msg_iv)
    ImageView msgIv;
    @BindView(R.id.personal_layout)
    LinearLayout personalLayout;
    @BindView(R.id.code_iv)
    ImageView codeIv;
    @BindView(R.id.close_tag_iv)
    ImageView closeTagIv;
    @BindView(R.id.zhuanye_titile)
    TextView zhuanyeTitile;
    @BindView(R.id.xuefen_title)
    TextView xuefenTitle;
    @BindView(R.id.tag_recycleview)
    RecyclerView tagRecycleview;
    @BindView(R.id.in_a_batch_tag_tv)
    TextView inABatchTagTv;
    @BindView(R.id.tag_layout)
    RelativeLayout tagLayout;
    @BindView(R.id.certification_arrow)
    ImageView certificationArrow;
    @BindView(R.id.top_authentication_layout)
    RelativeLayout topAuthenticationLayout;
    @BindView(R.id.arrow_iv)
    ImageView arrowIv;
    @BindView(R.id.info_layout)
    RelativeLayout infoLayout;
    @BindView(R.id.certification_bottom_arrow)
    ImageView certificationBottomArrow;
    @BindView(R.id.bottom_authentication_layout)
    RelativeLayout bottomAuthenticationLayout;
    Unbinder unbinder;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.real_name_verify_layout)
    RelativeLayout realNameVerifyLayout;
    private TextView mTitle;
    private TagFlexLayoutAdapter mTagAdapter;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragement_mine;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mTitle = rootView.findViewById(R.id.title_tv);
        mTitle.setText("我的");
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mContext);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。主轴为水平方向，起点在左端
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
        tagRecycleview.setLayoutManager(flexboxLayoutManager);
        String[] testDatas = new String[]{"耳鼻喉科", "内科", "外科", "妇产科", "内分泌", "耳鼻喉科", " 内分泌", "内科", "耳鼻喉科", "内科", "外科", "妇产科"};
        mTagAdapter = new TagFlexLayoutAdapter(mContext, testDatas);
        tagRecycleview.setAdapter(mTagAdapter);
    }


    @OnClick({R.id.close_tag_iv, R.id.in_a_batch_tag_tv, R.id.personal_layout, R.id.msg_iv, R.id.code_iv, R.id.info_layout, R.id.real_name_verify_layout, R.id.top_authentication_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close_tag_iv:
                tagLayout.setVisibility(View.GONE);
                break;
            case R.id.in_a_batch_tag_tv:
                String[] testDatas2 = new String[]{"体检科", "心血管内科", "神经内科", "消化内科", "眼科", "空腔科", "医疗美容科", " 骨科", "内科", "精神心理科", "五官科", "中医科", "妇产科"};
                mTagAdapter.changeData(testDatas2);
                break;
            case R.id.personal_layout:
                String mName = nameTv.getText().toString().trim();
                InputNameActivity.launch(mContext, mName);
                break;
            case R.id.msg_iv:
                MsgActivity.launch(mContext);
                break;
            case R.id.code_iv:
                QRCodeActivity.launch(mContext);
                break;
            case R.id.info_layout:
                InfoDetailActivity.launch(mContext);
                break;
            case R.id.real_name_verify_layout:
                VerifyTipActivity.launch(mContext, 1);
                break;
            case R.id.top_authentication_layout:
                VerifyTipActivity.launch(mContext, 0);
                break;

        }
    }


//    private static PopupWindow getPopupWindow(Activity activity, View root) {
//        final PopupWindow popupWindow = new PopupWindow(root);
//        // 使其和子控件能获得焦点
//        popupWindow.setFocusable(true);
//        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        // 设置允许在外点击消失
//        popupWindow.setOutsideTouchable(true);
////        popupWindow.setAnimationStyle(R.style.umeng_socialize_shareboard_animation);
//        //设置弹出窗体需要软键盘
//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
////        setAlpha(activity,true);
//        //软键盘不会挡着popupwindow
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
//        return popupWindow;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Subscribe
    public void onEvent(Object pObject) {
        if (pObject instanceof NameEventBus) {
            NameEventBus mNameEvent = (NameEventBus) pObject;
            nameTv.setText(mNameEvent.getName());
        } else if (pObject instanceof VerifyEventBus) {
//            VerifySuccDialogActivity.launch(mContext);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
