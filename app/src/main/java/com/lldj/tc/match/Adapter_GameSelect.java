package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.httpMgr.beans.FormatModel.ResultsModel;
import com.lldj.tc.httpMgr.beans.FormatModel.matchModel.BetModel;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;
import com.lldj.tc.toolslibrary.util.Clog;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter_GameSelect extends RecyclerView.Adapter<Adapter_GameSelect.MyViewHolder> {
    private Context context;
    private Adapter_GameSelect.MyViewHolder mHolder  = null;
    private List<ResultsModel> datas = new ArrayList<>();
    public int selectID = 0;

    public Adapter_GameSelect(Context context, List<ResultsModel> datas){
        this.context = context;
        this.datas = datas;

        this.datas.add(0, new ResultsModel(0, context.getResources().getString(R.string.allgames), "ssss"));
        selectID = SharePreUtils.getInstance().getSelectGame(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = View.inflate(context, R.layout.gameselectcell,null);
        Adapter_GameSelect.MyViewHolder holder = new Adapter_GameSelect.MyViewHolder(v);
//        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResultsModel _data = datas.get(position);

        this.mHolder = (Adapter_GameSelect.MyViewHolder) holder;

        this.mHolder.bottomCommon();

    }

    @Override
    public int getItemCount() {
        return (int)Math.ceil(datas.size()/3.0);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout[]layout = new RelativeLayout[3];
        private int oneWidth = 0;
        public MyViewHolder(View itemView) {
            super(itemView);
            int[] ids = new int[]{R.id.view1, R.id.view2, R.id.view3};
            oneWidth = (AppUtils.getScreenWidth(context) - (itemView.findViewById(R.id.view).getLayoutParams().width)*4)/3;
            for (int i = 0; i < 3; i++) {
                layout[i] = itemView.findViewById(ids[i]);
                layout[i].getLayoutParams().height = oneWidth;
                layout[i].getLayoutParams().width = oneWidth;
            }
        }

        public void bottomCommon() {
            int pos = getAdapterPosition();
            for (int i = 0; i < 3; i++) {
                int _len = pos*3+i;
                ResultsModel _data = null;

                if(datas.size() >= _len){
                    _data = datas.get(_len);
                    layout[i].setVisibility(View.VISIBLE);
                }
                else{
                    layout[i].setVisibility(View.GONE);
                    return;
                }

                int _id = _data.getId();
                View view = layout[i].findViewById(R.id.game_view);

                if(view == null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    view = inflater.inflate(R.layout.onegamecell, null);
                    view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    view.getLayoutParams().width = oneWidth;
                    view.getLayoutParams().height = oneWidth;
                    layout[i].addView(view);
                    view.setId(R.id.game_view);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectID = (int) v.getTag();
                            notifyDataSetChanged();
                        }
                    });
                }

                Object _tag = view.getTag();
                if(_tag == null || (int)view.getTag() != _id){
                    ImageView img = (ImageView) view.findViewById(R.id.imggameicon);
                    HttpTool.getBitmapUrl(_data.getLogo(), new HttpTool.bmpListener() {
                        @Override
                        public void onFinish(Bitmap bitmap) {
                            if (bitmap != null) img.setImageBitmap(bitmap);
                        }
                    });
                }
                ((TextView)view.findViewById(R.id.gamename)).setText(_data.getName());

                setViewColor(view, (selectID == _id));

                view.setTag(_id);
            }

        }

        private void setViewColor(View v, boolean select){
            if(select)
                v.findViewById(R.id.selectline).setBackgroundColor(Color.parseColor("#85E1E2"));
            else
                v.findViewById(R.id.selectline).setBackgroundColor(Color.parseColor("#383b4a"));
        }
    }
}
