package com.xiangrong.yunyang.inspectionofassets.mvp.contract;

import com.xiangrong.yunyang.inspectionofassets.base.IBaseModel;
import com.xiangrong.yunyang.inspectionofassets.base.IBaseView;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanFindCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanUpdateUI;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:25
 * 文件    InspectionOfAssets
 * 描述   ScanActivity（盘点）的契约类
 */
public interface ScanContract {

    interface Model extends IBaseModel {

        // 依据资产编码进行数据库查询
        void assetsCodeFindDb(String barCodeText, ScanFindCallBack scanFindCallBack);

        // 找到数据（依据资产编码），进行更新数据
        void updateDbFromPhy(ScanUpdateUI scanUpdateUI);

    }

    interface View extends IBaseView {

        // 盘点成功
        void inventorySuccess();

        // 盘点失败
        void inventoryFailure();

        // 更新数据，并重新绘制UI界面
        void updateDbFromPhy(List<School> list, int tag);

    }

    interface Presenter {

        void assetsCodeFindDb(String barCodeText);

        void updateDbFromPhy();

    }

}
