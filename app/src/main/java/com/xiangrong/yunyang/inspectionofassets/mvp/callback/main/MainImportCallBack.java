package com.xiangrong.yunyang.inspectionofassets.mvp.callback.main;

import com.xiangrong.yunyang.inspectionofassets.entity.School;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 12:18
 * 文件    InspectionOfAssets
 * 描述   将Excel表格数据导入到数据库中
 */
public interface MainImportCallBack {

    // 导入——List<School>
    void importDataToDb(List<School> list);

    // 导入——成功
    void importSuccess();

    // 导入——失败
    void importFailure();

}
