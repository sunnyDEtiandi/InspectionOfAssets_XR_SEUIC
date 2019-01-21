package com.xiangrong.yunyang.inspectionofassets.mvp.callback.main;

/**
 * 作者    yunyang
 * 时间    2019/1/21 12:30
 * 文件    InspectionOfAssets
 * 描述   导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
 */
public interface MainExportCallBack {

    // 导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
    void exportDataToDb(String select_text_string);

    // 导出——成功
    void exportSuccess();

    // 导出——失败
    void exportFailure();

}
