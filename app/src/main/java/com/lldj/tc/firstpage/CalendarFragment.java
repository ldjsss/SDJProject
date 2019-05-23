package com.lldj.tc.firstpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.lldj.tc.R;
import com.lldj.tc.toolslibrary.view.BaseFragment;

import butterknife.ButterKnife;

public class CalendarFragment extends BaseFragment {

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