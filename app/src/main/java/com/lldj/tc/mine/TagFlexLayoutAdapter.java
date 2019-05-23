package com.lldj.tc.mine;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lldj.tc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 标签的布局Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class TagFlexLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private String[] testDatas;

    public TagFlexLayoutAdapter(Context mContext, String[] ptestDatas) {
        this.mContext = mContext;
        this.testDatas = ptestDatas;

    }
    public void changeData( String[] ptestDatas) {
        this.testDatas = ptestDatas;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.flex_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewHolder mHolder = (viewHolder) holder;
        mHolder.tagTv.setText(testDatas[position]);
        //https://github.com/google/flexbox-layout
//        FlexboxLayoutManager (within RecyclerView)
//        The second one is FlexboxLayoutManager that can be used within RecyclerView.
//
//         RecyclerView recyclerView = (RecyclerView) context.findViewById(R.id.recyclerview);
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
//        layoutManager.setFlexDirection(FlexDirection.COLUMN);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
//        recyclerView.setLayoutManager(layoutManager);
//        or for the attributes for the children of the FlexboxLayoutManager you can do like:
//
//          mImageView.setImageDrawable(drawable);
//        ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
//        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
//            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
//            flexboxLp.setFlexGrow(1.0f);
//            flexboxLp.setAlignSelf(AlignSelf.FLEX_END);
//        }

    }

    @Override
    public int getItemCount() {
        return testDatas.length;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tag_tv)
        TextView tagTv;
        @BindView(R.id.list_item_layout)
        LinearLayout listItemLayout;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
