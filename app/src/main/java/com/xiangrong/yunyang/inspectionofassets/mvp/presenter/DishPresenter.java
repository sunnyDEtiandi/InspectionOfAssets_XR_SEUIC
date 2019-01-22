package com.xiangrong.yunyang.inspectionofassets.mvp.presenter;

import com.xiangrong.yunyang.inspectionofassets.base.BasePresenter;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataDishCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.DishContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.model.DishModel;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/22 9:12
 * 文件    InspectionOfAssets
 * 描述   DishFragment的控制类
 */
public class DishPresenter extends BasePresenter<DishContract.Model, DishContract.View> implements DishContract.Presenter {

    @Override
    protected DishContract.Model createModule() {
        return new DishModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void getDishFragmentDetail(String ownershipDataSheetName, String inventoryResults) {
        getView().showLoading();
        getModule().getDishFragmentDetail(ownershipDataSheetName, inventoryResults, new QueryDataDishCallBack() {
            @Override
            public void updateUIInitRecy(List<School> mDbToSchoolList) {
                getView().getDishFragmentDetail(mDbToSchoolList);
                getView().dismissLoading();
            }

            @Override
            public void updateFailure() {
                getView().dismissLoading();
            }
        });
    }
}
