package com.xiangrong.yunyang.inspectionofassets.mvp.contract;

import com.xiangrong.yunyang.inspectionofassets.base.IBaseModel;
import com.xiangrong.yunyang.inspectionofassets.base.IBaseView;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataDishCallBack;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/22 9:12
 * 文件    InspectionOfAssets
 * 描述   DishFragment的契约类
 */
public interface DishContract {

    interface Model extends IBaseModel {

        // 盘碎片查询全部，盘亏，无盈亏的详情
        void getDishFragmentDetail(String ownershipDataSheetName, String inventoryResults, QueryDataDishCallBack queryDataDishCallBack);

    }

    interface View extends IBaseView {

        void getDishFragmentDetail(List<School> mDbToSchoolList);

    }

    interface Presenter {

        void getDishFragmentDetail(String ownershipDataSheetName, String inventoryResults);

    }

}
