package com.xiangrong.yunyang.inspectionofassets;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.xiangrong.yunyang.inspectionofassets.adapter.QueryDataAdapter;
import com.xiangrong.yunyang.inspectionofassets.base.BaseMvpPresenterActivity;
import com.xiangrong.yunyang.inspectionofassets.base.BasePresenter;
import com.xiangrong.yunyang.inspectionofassets.entity.CurrentFileName;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.fragment.FragmentDish;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.QueryDataContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.presenter.QueryDataPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryDataActivity extends BaseMvpPresenterActivity<QueryDataPresenter> implements QueryDataContract.View {

    @BindView(R.id.title_back_title_layout)
    AppCompatTextView titleBackTitleLayout;
    @BindView(R.id.image_back_title_layout)
    AppCompatImageView imageBackTitleLayout;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    ViewPager container;

    private String ownershipDataSheetName;

    private QueryDataAdapter mQueryDataAdapter;

    private static final String POSITION = "POSITION";

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_data);
        ButterKnife.bind(this);
        // EventBus的注册
        EventBus.getDefault().register(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        titleBackTitleLayout.setText(getString(R.string.text_result));
        mFragments = new ArrayList<>();
        mPresenter.getTitleNameAndCount(ownershipDataSheetName);
    }

    /**
     * image_back_title_layout 回退按钮的点击事件
     */
    @OnClick(R.id.image_back_title_layout)
    public void onViewClicked() {
        finish();
    }

    /**
     * 页面异常销毁后，需要保存的数据
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabs.getSelectedTabPosition());
    }

    /**
     * 页面异常销毁后，重启后需要将保存的数据进行恢复
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        container.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    /**
     * EventBus的解注册
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected QueryDataPresenter createPresenter() {
        return new QueryDataPresenter();
    }

    /**
     * 拿到当前选中的Excel文件的名称
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCurrentFileNameEvent(CurrentFileName currentFile) {
        ownershipDataSheetName = currentFile.getFileName();
    }

    /**
     * 初始化ViewPager + TabLayout + Fragment
     */
    @Override
    public void getTitleNameAndCount(String[] titles) {
        mFragments.add(FragmentDish.newInstance(0));
        mFragments.add(FragmentDish.newInstance(1));
        mFragments.add(FragmentDish.newInstance(2));
        if (mFragments.size() > 0 && titles.length > 0) {
            mQueryDataAdapter = new QueryDataAdapter(getSupportFragmentManager(), mFragments, titles);
            container.setAdapter(mQueryDataAdapter);
            container.setOffscreenPageLimit(3);
            tabs.setupWithViewPager(container);
            setupTabLayout(tabs);
        }
    }

    /**
     * 设置TabLayout的一些属性
     */
    private void setupTabLayout(TabLayout tabs) {
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(container);
    }
}
