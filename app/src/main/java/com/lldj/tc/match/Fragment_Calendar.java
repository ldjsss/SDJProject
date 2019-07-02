package com.lldj.tc.match;

import android.os.Bundle;
import android.view.View;

import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseFragment;

public class Fragment_Calendar extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ViewType = getArguments().getInt( "ARG");
//        Clog.e("sssssss", " -----------xcl onCreate = " + ViewType);
    }

    @Override
    public int getContentView() {
        return R.layout.fragement_calendar;
    }

    @Override
    public void initView(View rootView) {


    }

}