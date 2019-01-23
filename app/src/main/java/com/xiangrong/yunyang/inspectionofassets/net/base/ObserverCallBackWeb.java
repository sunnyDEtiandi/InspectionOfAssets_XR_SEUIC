package com.xiangrong.yunyang.inspectionofassets.net.base;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:49
 * 文件    InspectionOfAssets
 * 描述   Web_根据响应码，返回相应内容体
 */
public abstract class ObserverCallBackWeb<T> extends BaseCallBack<ResultResponseWeb<T>> {
    @Override
    public void onNext(ResultResponseWeb response) {

        if (response.code == 1) {
            onSuccess((T) response.content);
        } else {
//            if (!"无数据".equals(response.message)) {
//                ToastUtils.show(response.message);
//            }
            onFailure(response);
        }
    }

    protected abstract void onSuccess(T response);

}

