package com.xiangrong.yunyang.inspectionofassets.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者    yunyang
 * 时间    2019/1/18 12:17
 * 文件    BaseMvp
 * 描述   单个Presenter
 */
public abstract class BaseMvpPresenterActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected P mPresenter;

    private ProgressDialog loadingDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建present
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initLoadDialog();
    }

    private void initLoadDialog() {
        loadingDialog = new ProgressDialog(BaseMvpPresenterActivity.this);
        loadingDialog.setCancelable(false);
        loadingDialog.setMessage("正在加载中...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
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
        return BaseMvpPresenterActivity.this;
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();
}
