package com.lldj.tc.firstpage;

import android.os.Bundle;
import android.view.View;

import com.lldj.tc.R;
import com.lldj.tc.mainUtil.HandlerType;
import com.lldj.tc.toolslibrary.handler.HandlerInter;
import com.lldj.tc.toolslibrary.view.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSet extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_set;
    }

    @Override
    public void initView(View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @OnClick({R.id.back_main_iv, R.id.right_arrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main_iv:
                HandlerInter.getInstance().sendEmptyMessage(HandlerType.LEFTBACK);
                break;
            case R.id.right_arrow:
                break;
        }
    }
}
