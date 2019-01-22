package com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata;

import com.xiangrong.yunyang.inspectionofassets.entity.School;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/22 8:55
 * 文件    InspectionOfAssets
 * 描述   查询数据并更新UI（依据盘点结果和所属数据库列进行查询）
 */
public interface QueryDataDishCallBack {

    void updateUIInitRecy(List<School> mDbToSchoolList);

}
