package com.xiangrong.yunyang.inspectionofassets.net.base;

import com.xiangrong.yunyang.inspectionofassets.entity.ResultResponse;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:48
 * 文件    InspectionOfAssets
 * 描述   Login，根据响应状态，返回响应数据和响应码
 */
public abstract class ObserverCallBackLogin<T> extends BaseCallBack<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {

        if (response.status) {
            onSuccess((T) response.info, response.code);
        } else {
            onFailure(response);
        }
    }

    protected abstract void onSuccess(T response, int code);

}
