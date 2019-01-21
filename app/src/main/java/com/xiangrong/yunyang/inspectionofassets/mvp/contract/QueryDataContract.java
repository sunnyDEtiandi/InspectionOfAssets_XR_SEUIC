package com.xiangrong.yunyang.inspectionofassets.mvp.contract;

import com.xiangrong.yunyang.inspectionofassets.base.IBaseModel;
import com.xiangrong.yunyang.inspectionofassets.base.IBaseView;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataCallBack;

/**
 * 作者    yunyang
 * 时间    2019/1/21 16:50
 * 文件    InspectionOfAssets
 * 描述   QueryDataActivity的契约类
 */
public interface QueryDataContract {

    interface Model extends IBaseModel {
        // 获取全部，盘亏，无盈亏的数量
        void getTitleNameAndCount(String ownershipDataSheetName, QueryDataCallBack queryDataCallBack);
    }

    interface View extends IBaseView {
        void getTitleNameAndCount(String[] titles);
    }

    interface Presenter {
        void getTitleNameAndCount(String ownershipDataSheetName);
    }
}
