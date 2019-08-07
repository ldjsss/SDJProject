package com.lldj.tc.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.HttpMsg;
import com.lldj.tc.http.beans.ActivityBean;
import com.lldj.tc.http.beans.BaseBean;
import com.lldj.tc.http.beans.TaskBean;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.ImageLoader;
import com.lldj.tc.toolslibrary.view.ToastUtils;
import com.lldj.tc.utils.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;


public class Adapter_ActivityCell extends RecyclerView.Adapter {

    private Context mContext;
    private viewHolder mHolder = null;
    private List<TaskBean.TaskMode> messList = new ArrayList<>();
    private List<ActivityBean.ActivityMode> _List = new ArrayList<>();
    private int ViewType = 0;
    private ImageLoader imageLoader = new ImageLoader(bActivity, R.mipmap.game_arena, R.mipmap.game_arena);


    public Adapter_ActivityCell(Context mContext, int _ViewType) {
        this.mContext = mContext;
        this.ViewType = _ViewType;
    }

    public void changeData(List<TaskBean.TaskMode> _messList) {
        this.messList = _messList;

        notifyDataSetChanged();
    }

    public void changeData1(List<ActivityBean.ActivityMode> _List) {
        this._List = _List;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new viewHolder(LayoutInflater.from(mContext).inflate(this.ViewType == 0? R.layout.useactivitycelllayout : R.layout.taskcelllayout, parent, false));
        holder.isRecyclable();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        this.mHolder = (viewHolder) holder;

        this.mHolder.bottomCommon();
    }

    @Override
    public int getItemCount() {
        return (ViewType == 0 ? _List.size() : messList.size());
    }


    class viewHolder extends RecyclerView.ViewHolder {

        public viewHolder(View itemView) {
            super(itemView);
        }

        public void bottomCommon() {

            int pos = getAdapterPosition() - 1;

            if(ViewType == 0){
                ActivityBean.ActivityMode _data = _List.get(pos);

                ((TextView)itemView.findViewById(R.id.activitytitle)).setText(_data.getActivity_name());
                ((TextView)itemView.findViewById(R.id.activitydes)).setText(_data.getActivity_desc());
                ImageView gameicon = itemView.findViewById(R.id.activityimg);
                imageLoader.getAndSetImage(_data.getActivity_img(), gameicon);
            }
            else {
                TaskBean.TaskMode _data = messList.get(pos);

                ((TextView)itemView.findViewById(R.id.messtitle)).setText(_data.getTask_name());
                ((TextView)itemView.findViewById(R.id.des)).setText(_data.getTask_desc());

                TextView complete = itemView.findViewById(R.id.complete);
                complete.setText(String.format("(%s/%s)", _data.getUser_set_num(), _data.getTask_set_num()));

                TextView tbcomplete = (TextView)itemView.findViewById(R.id.tbcomplete);
                if (_data.getStatus() == 1) {//0:未完成1:已完成未领取2:已领取
                    tbcomplete.setText(mContext.getString(R.string.get));
                } else if (_data.getStatus() == 2) {
                    tbcomplete.setText(mContext.getString(R.string.finished));
                }

                tbcomplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (_data.getStatus() == 1) {//0:未完成1:已完成未领取2:已领取
                            HttpMsg.getInstance().sendGetTask(String.valueOf(_data.getId()), SharePreUtils.getToken(mContext), BaseBean.class, new HttpMsg.Listener() {
                                @Override
                                public void onFinish(Object _res) {
                                    BaseBean res = (BaseBean) _res;
                                    if (res.getCode() == GlobalVariable.succ) {
                                        _data.setStatus(2);
                                        ToastUtils.show_middle_pic(mContext, R.mipmap.cancle_icon, mContext.getString(R.string.gettasksucc), ToastUtils.LENGTH_SHORT);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                });
            }

        }

    }
}
