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
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        titleBackTitleLayout.setText(getString(R.string.text_result));
        mFragments = new ArrayList<>();
    }

    @OnClick(R.id.image_back_title_layout)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabs.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        container.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected QueryDataPresenter createPresenter() {
        return new QueryDataPresenter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCurrentFileNameEvent(CurrentFileName currentFile) {
        ownershipDataSheetName = currentFile.getFileName();
    }

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

    private void setupTabLayout(TabLayout tabs) {
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(container);
    }
}
