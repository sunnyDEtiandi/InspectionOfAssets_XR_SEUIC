package com.xiangrong.yunyang.inspectionofassets.net.base;

import android.util.Log;

import com.xiangrong.yunyang.inspectionofassets.entity.ResultResponse;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:48
 * 文件    InspectionOfAssets
 * 描述   根据响应状态，返回响应数据
 */
public abstract class ObserverCallBack<T> extends BaseCallBack<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {

        if (response.status) {
            onSuccess((T) response.info);
        } else {
//            if (!"无数据".equals(response.message)) {
//                ToastUtils.show(response.message);
//            }
            onFailure(response);
        }
    }

    protected abstract void onSuccess(T response);

}

