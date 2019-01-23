package com.xiangrong.yunyang.inspectionofassets.net.base;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.xiangrong.yunyang.inspectionofassets.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:47
 * 文件    InspectionOfAssets
 * 描述   基类——接口返回错误信息提示
 */
public abstract class BaseCallBack<T> implements Observer<T> {
    private Handler mDelivery;

    public BaseCallBack() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onComplete() {
        mDelivery = null;
    }

    @Override
    public void onError(final Throwable e) {
        e.printStackTrace();
        Log.e("云阳", e.toString());
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (e instanceof SocketTimeoutException) {
//                    ToastUtils.show("网络连接超时");
                } else if (e instanceof SocketException) {
                    if (e instanceof ConnectException) {
//                        ToastUtils.show("网络未连接");
                    } else {
//                        ToastUtils.show("网络错误");
                    }
                }
                onError();
            }
        });
    }

    protected void onError() {
    }

    protected void onFailure(ResultResponse response) {
    }

    protected void onFailure(ResultResponseWeb response) {
    }
}
