package com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:56
 * 文件    InspectionOfAssets
 * 描述   依据资产编码进行数据库查询，进行盘点
 */
public interface ScanFindCallBack {

    // 盘点成功
    void inventorySuccess();

    // 盘点失败
    void inventoryFailure();

}
