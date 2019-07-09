package com.lldj.tc.toolslibrary.recycleDialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.toolslibrary.R;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerView;
import com.lldj.tc.toolslibrary.recycleview.LRecyclerViewAdapter;
import com.lldj.tc.toolslibrary.recycleview.LoadingFooter;
import com.lldj.tc.toolslibrary.recycleview.RecyclerViewStateUtils;
import com.lldj.tc.toolslibrary.view.BaseDialog;

import java.util.ArrayList;

public class BaseRecycleDialog extends BaseDialog implements LRecyclerView.LScrollListener {

    LinearLayoutManager layoutManager;
    private RecycleCell mAdapter = null;
    private ArrayList<String> mlist = new ArrayList<>();

    public BaseRecycleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        View view = View.inflate(context, R.layout.base_recycleview, null);
        setContentView(view);

        Window window = this.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
        window.setAttributes(layoutParams);

    }

    public void showView(RecycleCell _mAdapter) {
        if(_mAdapter == null) return;

        mAdapter = _mAdapter;
        LRecyclerView recycleview = (LRecyclerView)findViewById(R.id.baserecycleview);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycleview.setLayoutManager(layoutManager);
        recycleview.setAdapter(new LRecyclerViewAdapter(getContext(), mAdapter));
        recycleview.setLScrollListener(this);
        recycleview.setPullRefreshEnabled(false);


        mlist.add("测试");
        mAdapter.changeData(mlist);
        RecyclerViewStateUtils.setFooterViewState(getOwnerActivity(), recycleview, 10, LoadingFooter.State.Normal, null);

        show();
    }

    @Override
    public void onRefresh() { }

    @Override
    public void onScrollUp() { }

    @Override
    public void onScrollDown() { }

    @Override
    public void onBottom() { }

    @Override
    public void onScrolled(int distanceX, int distanceY) { }
}
