package com.xiangrong.yunyang.inspectionofassets.mvp.presenter;

import com.xiangrong.yunyang.inspectionofassets.base.BasePresenter;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.QueryDataContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.model.QueryDataModel;

/**
 * 作者    yunyang
 * 时间    2019/1/21 16:50
 * 文件    InspectionOfAssets
 * 描述   QueryDataActivity的控制类
 */
public class QueryDataPresenter extends BasePresenter<QueryDataContract.Model, QueryDataContract.View> implements QueryDataContract.Presenter {
    @Override
    protected QueryDataContract.Model createModule() {
        return new QueryDataModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void getTitleNameAndCount(String ownershipDataSheetName) {
        getModule().getTitleNameAndCount(ownershipDataSheetName, new QueryDataCallBack() {
            @Override
            public void updateUIFragment(String[] titles) {
                getView().getTitleNameAndCount(titles);
            }
        });
    }
}
