package com.lldj.tc.match;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lldj.tc.R;
import com.lldj.tc.http.beans.FormatModel.ResultsModel;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.http.HttpTool;
import com.lldj.tc.toolslibrary.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lldj.tc.toolslibrary.view.BaseActivity.bActivity;

public class Adapter_GameSelect extends RecyclerView.Adapter<Adapter_GameSelect.MyViewHolder> {
    private Context context;
    private Adapter_GameSelect.MyViewHolder mHolder  = null;
    private List<ResultsModel> datas = new ArrayList<>();
    public List<Integer> selects = new ArrayList();
    private boolean frashLine = false;

    private ResultsModel firstData;

    public Adapter_GameSelect(Context context, List<ResultsModel> datas){
        this.context = context;
        if(firstData == null)firstData = new ResultsModel(0, bActivity.getResources().getString(R.string.allgames), "BankCardAPI", 0);
        datas.add(0, firstData);
        this.datas.addAll(datas);

        String _selectsStr = SharePreUtils.getInstance().getSelectGame(context);
        if(TextUtils.isEmpty(_selectsStr)) {
            selects.add(firstData.getId());
            return;
        }
        String[] strs = _selectsStr.split("&game_ids=");
        for (int i = 0; i < strs.length; i++) {
            if(!TextUtils.isEmpty(strs[i])) selects.add(Integer.parseInt(strs[i]));
        }
    }

    public String getSelect(){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < selects.size(); i++) {
            buffer.append("&game_ids=");
            buffer.append(selects.get(i));
        }

        return buffer.toString();
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

        this.mHolder.bottomCommon(position);

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

        public void bottomCommon(int pos) {
            ResultsModel _data = null;
            for (int i = 0; i < 3; i++) {
                int _len = pos*3+i;

                if(datas.size() > _len){
                    _data = datas.get(_len);
                    layout[i].setVisibility(_data == null?View.GONE:View.VISIBLE);
                }
                else{
                    layout[i].setVisibility(View.GONE);
                    layout[i].setTag("-1");
                    _data = null;
                }

                int _id = _data == null ? -1:_data.getId();
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
                            int _selectID = (int) v.getTag();
                            addSelect(_selectID);
                            notifyDataSetChanged();
                        }
                    });
                }

                Object _tag = view.getTag();
                if((_tag == null || (int)view.getTag() != _id) && _data != null && frashLine == false){
                    ImageView img = (ImageView) view.findViewById(R.id.imggameicon);
                    if(_id == 0 ){
                        img.setImageResource(R.mipmap.game_arena);
                    }
                    else {
                        HttpTool.getBitmapUrl(_data.getLogo(), new HttpTool.bmpListener() {
                            @Override
                            public void onFinish(Bitmap bitmap) {
                                if (bitmap != null) img.setImageBitmap(bitmap);
                            }
                        });
                    }

                    ((TextView)view.findViewById(R.id.gamename)).setText(_data.getName());
                }

                setViewColor(view, containID(_id) >= 0 ? true : false);

                view.setTag(_id);
            }

        }

        private void addSelect(int selectID){
            int index = containID(selectID);
            if(selectID == firstData.getId()) {
                selects.clear();
                selects.add(selectID);
            }
            else {
                if(index >= 0){
                    selects.remove(index);
                    if(selects.size() <= 0)selects.add(firstData.getId());
                }
                else{
                   int _zero = containID(firstData.getId());
                    if(_zero >= 0) selects.remove(_zero);
                    selects.add(selectID);
                }
            }
        }

        private int containID(int selectID){
            for (int i = 0; i < selects.size(); i++) {
                if (selects.get(i) == selectID) return i;
            }
            return -1;
        }

        private void setViewColor(View v, boolean select){
            if(select)
                v.findViewById(R.id.selectline).setBackgroundColor(Color.parseColor("#85E1E2"));
            else
                v.findViewById(R.id.selectline).setBackgroundColor(Color.parseColor("#383b4a"));
        }
    }
}
