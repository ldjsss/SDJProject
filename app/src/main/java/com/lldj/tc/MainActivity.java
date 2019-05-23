package com.lldj.tc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lldj.tc.main.ViewPagerAdapterMain;
import com.lldj.tc.view.BottomView;
import com.lldj.tc.toolslibrary.view.BaseActivity;
import com.lldj.tc.toolslibrary.view.ViewPagerSlide;

public class MainActivity extends BaseActivity {
    private  BottomView mBottomBar;
    private ViewPagerSlide mViewPager;
    private ViewPagerAdapterMain mPagerAdapter;

    public static void launch(Context pContext) {
        Intent mIntent = new Intent(pContext, MainActivity.class);
        pContext.startActivity(mIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void initData() {
//        ImmersionBar.with(this).keyboardEnable(true).setOnKeyboardListener(this).init();
        mBottomBar = findViewById(R.id.bottom_view);
        mViewPager = findViewById(R.id.viewPager_main);
        initViewPager();

    }

    public void initViewPager() {
        mPagerAdapter = new ViewPagerAdapterMain(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setSlidAble(false);
        mViewPager.setOffscreenPageLimit(mPagerAdapter.COUNT);
        mBottomBar.setBottomClickListener(new BottomView.BottomClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != 4) {
                    mViewPager.setCurrentItem(position);
                } else {
                    Toast.makeText(MainActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    @Subscribe
//    public void onEvent(Object pObject) {
//        if (pObject instanceof NameEventBus) {
//            NameEventBus mLoginType = (NameEventBus) pObject;
//            if(mLoginType.isKeybordisShow()){
//                mBottomBar.setVisibility(View.GONE);
//            }else{
//                mBottomBar.setVisibility(View.VISIBLE);
//            }
//
//
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
