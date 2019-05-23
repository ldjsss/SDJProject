package com.lldj.tc.mine.info;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lldj.tc.R;
import com.lldj.tc.sharepre.SharePreUtils;
import com.lldj.tc.toolslibrary.view.BaseLazyFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * description: 科室选择<p>
 * user: lenovo<p>
 * Creat Time: 2018/12/7 11:20<p>
 * Modify Time: 2018/12/7 11:20<p>
 */


public class dapartmentFragement extends BaseLazyFragment implements DepartmentAdapter.OnItemClickListener {
    @BindView(R.id.one_department_recycleview)
    RecyclerView oneDepartmentRecycleview;
    @BindView(R.id.two_department_recycleview)
    RecyclerView twoDepartmentRecycleview;
    @BindView(R.id.caree_tip_close_iv)
    ImageView careeTipCloseIv;
    Unbinder unbinder;
    @BindView(R.id.ensure_tv)
    TextView ensureTv;
    Unbinder unbinder1;
    private DepartmentAdapter mOneAdapter;
    private ArrayList<DepartmentOneBean> mDataList;
    private DepartmentAdapter mTwoAdapter;
    private int level_one_dapartment_position;
    private int level_two_dapartment_position;

    @Override

    protected void lazyLoad() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragement_dapartment;
    }

    @Override
    public void initView(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        //一级科室
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        oneDepartmentRecycleview.setLayoutManager(linearLayoutManager);
        mOneAdapter = new DepartmentAdapter(mContext, 0);
        oneDepartmentRecycleview.setAdapter(mOneAdapter);
        mOneAdapter.setOnItemClickListener(this);
        setData();
        //二级科室
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        twoDepartmentRecycleview.setLayoutManager(linearLayoutManager1);
        mTwoAdapter = new DepartmentAdapter(mContext, 1);
        twoDepartmentRecycleview.setAdapter(mTwoAdapter);
        mTwoAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.caree_tip_close_iv, R.id.ensure_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.caree_tip_close_iv:
                ((InfoDetailActivity) getActivity()).setDepartment();
                break;
            case R.id.ensure_tv:
                String level1dapartmentstr = mDataList.get(level_one_dapartment_position).getDapartment();
                String level2dapartmentstr = mDataList.get(level_one_dapartment_position).getMtwoDepartmentList().get(level_two_dapartment_position).getDapartment();
                SharePreUtils.setDepartment(mContext, level1dapartmentstr + "-" + level2dapartmentstr);
                ((InfoDetailActivity) getActivity()).setDepartment();
                break;
        }
    }

    public void setData() {
        mDataList = new ArrayList<>();
        DepartmentOneBean moneBean = new DepartmentOneBean("内科");
        ArrayList<DepartmentOneBean> mTwoList = new ArrayList<>();
        mTwoList.add(new DepartmentOneBean("心血管内科"));
        mTwoList.add(new DepartmentOneBean("消化内科"));
        mTwoList.add(new DepartmentOneBean(" 肾内科"));
        mTwoList.add(new DepartmentOneBean("呼吸内科"));
        mTwoList.add(new DepartmentOneBean("内分泌代谢科"));
        mTwoList.add(new DepartmentOneBean("血液内科"));
        mTwoList.add(new DepartmentOneBean(" 风湿内科"));
        mTwoList.add(new DepartmentOneBean("神经内科"));
        mTwoList.add(new DepartmentOneBean("感染科"));
        mTwoList.add(new DepartmentOneBean("帕金森病中心"));
        moneBean.setMtwoDepartmentList(mTwoList);
        mDataList.add(moneBean);

        DepartmentOneBean mBean1 = new DepartmentOneBean("外科");
        ArrayList<DepartmentOneBean> mList1 = new ArrayList<>();
        mList1.add(new DepartmentOneBean("普通外科(微创外科中心)"));
        mList1.add(new DepartmentOneBean("胃肠外科"));
        mList1.add(new DepartmentOneBean("肝胆外科"));
        mList1.add(new DepartmentOneBean("甲状腺外科"));
        mList1.add(new DepartmentOneBean("乳腺外科泌尿外科·男性科"));
        mList1.add(new DepartmentOneBean("器官移植中心"));
        mList1.add(new DepartmentOneBean("碎石中心"));
        mList1.add(new DepartmentOneBean("心脏血管外科"));
        mList1.add(new DepartmentOneBean("胸外科"));
        mList1.add(new DepartmentOneBean("骨科·关节外科与运动"));
        mList1.add(new DepartmentOneBean("骨科·脊柱创伤外科"));
        mList1.add(new DepartmentOneBean("整形美容激光中心"));
        mList1.add(new DepartmentOneBean("神经外科"));
        mBean1.setMtwoDepartmentList(mList1);
        mDataList.add(mBean1);
        mOneAdapter.changeData(mDataList);
    }


    @Override
    public void onItemClickListener(int plevelOnePosition, int position, int pType) {

        switch (pType) {
            case 0:
                for (int i = 0; i < mDataList.size(); i++) {
                    if (plevelOnePosition == i) {
                        mDataList.get(i).setSelect(true);
                    } else {
                        mDataList.get(i).setSelect(false);
                    }
                }
                //二级科室
                for (int i = 0; i < mDataList.get(plevelOnePosition).getMtwoDepartmentList().size(); i++) {
                    mDataList.get(plevelOnePosition).getMtwoDepartmentList().get(i).setSelect(false);
                }
                mOneAdapter.changeData(mDataList);
                mTwoAdapter.changeData(plevelOnePosition, mDataList.get(plevelOnePosition).getMtwoDepartmentList());
                level_one_dapartment_position = plevelOnePosition;
                break;
            case 1:
                for (int i = 0; i < mDataList.get(plevelOnePosition).getMtwoDepartmentList().size(); i++) {
                    if (position == i) {
                        mDataList.get(plevelOnePosition).getMtwoDepartmentList().get(i).setSelect(true);
                    } else {
                        mDataList.get(plevelOnePosition).getMtwoDepartmentList().get(i).setSelect(false);
                    }
                }
                mTwoAdapter.changeData(mDataList.get(plevelOnePosition).getMtwoDepartmentList());
                level_two_dapartment_position = position;
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
