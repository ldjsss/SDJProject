package com.lldj.tc.view;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lldj.tc.R;

public class BottomView extends RelativeLayout implements View.OnClickListener {
    /**
     * 首页
     */
    public static final int TAB_1 = 0;
    /**
     * 求知
     */
    public static final int TAB_2 = 1;

    /**
     * 乐业
     */
    public static final int TAB_3 = 2;
    /**
     * 我的
     */
    public static final int TAB_4 = 3;
    //中间的
    public static final int TAB_Middle = 4;

    LinearLayout LLBottomItemFirst;
    LinearLayout LLBottomItemQiuzhi;
    LinearLayout LLBottomItemMiddle;
    LinearLayout LLBottomItemLeYe;
    LinearLayout LLBottomItemLeMine;
    private int tabs = 5;

    public BottomView(Context context) {
        super(context);
        initView(context);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.new_main_bottom_view, this, true);
        findViewId();

        setChoice(TAB_1);
    }

    public void findViewId() {
        LLBottomItemFirst = findViewById(R.id.LL_bottom_item_first);
        LLBottomItemFirst.setOnClickListener(this);
        LLBottomItemQiuzhi = findViewById(R.id.LL_bottom_item_qiuzhi);
        LLBottomItemQiuzhi.setOnClickListener(this);
        LLBottomItemLeYe = findViewById(R.id.LL_bottom_item_leye);
        LLBottomItemLeYe.setOnClickListener(this);
        LLBottomItemLeMine = findViewById(R.id.LL_bottom_item_mine);
        LLBottomItemLeMine.setOnClickListener(this);
        LLBottomItemMiddle = findViewById(R.id.LL_bottom_item_middle);
        LLBottomItemMiddle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.LL_bottom_item_first:
                if (bottomClickListener != null) {
                    bottomClickListener.onItemClick(TAB_1);
                    setChoice(TAB_1);
                }
                break;
            case R.id.LL_bottom_item_qiuzhi:
                if (bottomClickListener != null) {
                    bottomClickListener.onItemClick(TAB_2);
                    setChoice(TAB_2);
                }
                break;
            case R.id.LL_bottom_item_middle:
                if (bottomClickListener != null) {
                    bottomClickListener.onItemClick(TAB_Middle);
                    setChoice(TAB_Middle);
                }
                break;
            case R.id.LL_bottom_item_leye:
                if (bottomClickListener != null) {
                    bottomClickListener.onItemClick(TAB_3);
                    setChoice(TAB_3);
                }
                break;
            case R.id.LL_bottom_item_mine:
                if (bottomClickListener != null) {
                    bottomClickListener.onItemClick(TAB_4);
                    setChoice(TAB_4);
                }
                break;
        }
    }

    /**
     * @param position 下标
     */
    public void setChoice(int position) {
        LLBottomItemFirst.setSelected(position == TAB_1);
        LLBottomItemQiuzhi.setSelected(position == TAB_2);
        LLBottomItemLeYe.setSelected(position == TAB_3);
        LLBottomItemLeMine.setSelected(position == TAB_4);
        LLBottomItemMiddle.setSelected(position == TAB_Middle);
    }

    BottomClickListener bottomClickListener;

    public void setBottomClickListener(BottomClickListener bottomClickListener) {
        this.bottomClickListener = bottomClickListener;
    }


    public interface BottomClickListener {
        void onItemClick(int position);
    }

}
