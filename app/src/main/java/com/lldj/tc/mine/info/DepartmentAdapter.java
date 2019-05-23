package com.lldj.tc.mine.info;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lldj.tc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description: 科室的Adapter<p>
 * user: wlj<p>
 * Creat Time: 2018/11/23 15:57<p>
 * Modify Time: 2018/11/23 15:57<p>
 */


public class DepartmentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<DepartmentOneBean> mDataList = new ArrayList<>();
    private int type;
    private int levelonePostion;

    public DepartmentAdapter(Context mContext, int pType) {
        this.mContext = mContext;
        this.type = pType;
    }

    public void changeData(ArrayList<DepartmentOneBean> plist) {
        mDataList = plist;
        notifyDataSetChanged();
    }

    public void changeData(int plevelonePostion, ArrayList<DepartmentOneBean> plist) {
        mDataList = plist;
        this.levelonePostion = plevelonePostion;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragemet_department_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewHolder mHolder = (viewHolder) holder;
        DepartmentOneBean mItem = mDataList.get(position);
        mHolder.departmentItemTv.setText(mItem.getDapartment());
        if (mItem.isSelect()) {
            mHolder.departmentItemTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            mHolder.departmentItemTv.setTextColor(mContext.getResources().getColor(R.color.color_969696));
        }
        if (type == 0) {
            if (mItem.isSelect()) {
                mHolder.departmentItemTv.setBackgroundColor(mContext.getResources().getColor(R.color.color_f0f0f0));
            } else {
                mHolder.departmentItemTv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }

        }

        mHolder.departmentItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case 0:
                        if (null != mClickListener) {
                            mClickListener.onItemClickListener(position, 0, 0);
                        }
                        break;
                    case 1:
                        if (null != mClickListener) {
                            mClickListener.onItemClickListener(levelonePostion, position, 1);
                        }
                        break;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return null != mDataList ? mDataList.size() : 0;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.department_item_tv)
        TextView departmentItemTv;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int plevelOnePosition, int position, int pType);

    }

    private OnItemClickListener mClickListener;

    public void setOnItemClickListener(OnItemClickListener pListener) {
        this.mClickListener = pListener;
    }


}
