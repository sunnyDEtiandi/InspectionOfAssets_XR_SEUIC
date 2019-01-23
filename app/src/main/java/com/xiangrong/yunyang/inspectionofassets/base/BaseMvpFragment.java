package com.xiangrong.yunyang.inspectionofassets.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者    yunyang
 * 时间    2019/1/18 14:41
 * 文件    BaseMvp
 * 描述   单个Presenter
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    protected P mPresenter;

    public abstract int getContentViewId();

    protected abstract void initAllMembersView(Bundle savedInstanceState);

    protected Context mContext;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 创建present
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        mRootView = inflater.inflate(getContentViewId(), container, false);
        initAllMembersView(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为 true，表示 当activity configuration change 的时候，fragment 实例不会被重新创建
        setRetainInstance(true);
        this.mContext = getActivity();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseMvpPresenterActivity) mContext).showLoading();
    }

    @Override
    public void dismissLoading() {
        checkActivityAttached();
        ((BaseMvpPresenterActivity) mContext).dismissLoading();
    }

    @Override
    public void onEmpty(Object tag) {
        checkActivityAttached();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        checkActivityAttached();
        ((BaseMvpPresenterActivity) mContext).onError(tag, errorMsg);
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

}
