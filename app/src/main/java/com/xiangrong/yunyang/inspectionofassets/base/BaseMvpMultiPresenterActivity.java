package com.xiangrong.yunyang.inspectionofassets.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/18 14:35
 * 文件    BaseMvp
 * 描述   多个Presenter
 */
public abstract class BaseMvpMultiPresenterActivity extends AppCompatActivity implements IBaseView {

    protected List<BasePresenter> mPLimPresenters;

    private ProgressDialog loadingDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPLimPresenters = new ArrayList<>();
        // 加入Presenter
        addPresenter();
        Log.e("神奇", "mPLimPresenters.size = " + mPLimPresenters.size());
        if (mPLimPresenters.size() > 0) {
            for (BasePresenter presenter :
                    mPLimPresenters) {
                presenter.attachView(this);
            }
        }
        initLoadDialog();
        Log.e("神奇", "mPLimPresenters.size = " + mPLimPresenters.size());
    }

    private void initLoadDialog() {
        loadingDialog = new ProgressDialog(BaseMvpMultiPresenterActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setMessage("正在加载中...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPLimPresenters.size() > 0) {
            for (BasePresenter presenter :
                    mPLimPresenters) {
                presenter.detachView();
            }
            mPLimPresenters = null;
        }
    }

    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onEmpty(Object tag) {

    }

    @Override
    public void onError(Object tag, String errorMsg) {

    }

    @Override
    public Context getContext() {
        return BaseMvpMultiPresenterActivity.this;
    }

    /**
     * 加入Presenter
     */
    protected abstract void addPresenter();

}
