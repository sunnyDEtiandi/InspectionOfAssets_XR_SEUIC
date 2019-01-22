package com.xiangrong.yunyang.inspectionofassets.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiangrong.yunyang.inspectionofassets.R;
import com.xiangrong.yunyang.inspectionofassets.adapter.DishFragmentAdapter;
import com.xiangrong.yunyang.inspectionofassets.base.BaseMvpFragment;
import com.xiangrong.yunyang.inspectionofassets.entity.CurrentFileName;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.DishContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.presenter.DishPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 16:46
 * 文件    InspectionOfAssets
 * 描述   盘的碎片（盘盈——盘亏——无盈盘）
 */
public class FragmentDish extends BaseMvpFragment<DishPresenter> implements DishContract.View {

    private RecyclerView mRecyclerView;

    private DishFragmentAdapter mFragmentDishAdapter;

    private List<School> mDbToSchoolList;

    private String ownershipDataSheetName;

    public static Fragment newInstance(int tab) {
        Bundle bundle = new Bundle();
        FragmentDish fragmentWin = new FragmentDish();
        bundle.putInt("tab", tab);
        fragmentWin.setArguments(bundle);
        return fragmentWin;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_dish;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView(mRootView);
    }

    @Override
    protected DishPresenter createPresenter() {
        return new DishPresenter();
    }

    private void initView(View view) {
        initRecy(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            final int tab = bundle.getInt("tab");
            switch (tab) {
                case 0:
                    // 全部
                    findDbLitePal("全部");
                    break;
                case 1:
                    // 盘亏
                    findDbLitePal("盘亏");
                    break;
                case 2:
                    // 无盈亏
                    findDbLitePal("无盈亏");
                    break;
                default:
                    break;
            }
        }
    }

    private void initRecy(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.query_fragment_recy_dish);
        mDbToSchoolList = new ArrayList<>();
        mFragmentDishAdapter = new DishFragmentAdapter(getActivity(), mDbToSchoolList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mFragmentDishAdapter);
    }

    /**
     * 根据inventoryResults（盘点结果）去数据库中查找对应数据
     *
     * @param string
     */
    private void findDbLitePal(String string) {
        checkActivityAttached();
        mPresenter.getDishFragmentDetail(ownershipDataSheetName, string);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCurrentFileNameEvent(CurrentFileName currentFile) {
        ownershipDataSheetName = currentFile.getFileName();
    }

    @Override
    public void getDishFragmentDetail(List<School> mDbToSchoolList) {
        if (mFragmentDishAdapter != null) {
            mFragmentDishAdapter.setDataNotify(mDbToSchoolList);
        }
    }

}
