package com.xiangrong.yunyang.inspectionofassets.mvp.presenter;

import com.xiangrong.yunyang.inspectionofassets.base.BasePresenter;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanFindCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanUpdateUI;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.ScanContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.model.ScanModel;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:25
 * 文件    InspectionOfAssets
 * 描述   ScanActivity（盘点）的控制类
 */
public class ScanPresenter extends BasePresenter<ScanContract.Model, ScanContract.View> implements ScanContract.Presenter {
    @Override
    protected ScanContract.Model createModule() {
        return new ScanModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void assetsCodeFindDb(String barCodeText) {
        getView().showLoading();
        getModule().assetsCodeFindDb(barCodeText, new ScanFindCallBack() {
            @Override
            public void inventorySuccess() {
                getView().inventorySuccess();
                getView().dismissLoading();
            }

            @Override
            public void inventoryFailure() {
                getView().inventoryFailure();
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void updateDbFromPhy() {
        getModule().updateDbFromPhy(new ScanUpdateUI() {
            @Override
            public void inventoryUpdateUI(List<School> list, int tag) {
                getView().updateDbFromPhy(list, tag);
            }
        });
    }
}
