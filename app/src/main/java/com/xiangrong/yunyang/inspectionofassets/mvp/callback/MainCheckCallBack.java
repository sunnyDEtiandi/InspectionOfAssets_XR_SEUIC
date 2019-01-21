package com.xiangrong.yunyang.inspectionofassets.mvp.callback;

/**
 * 作者    yunyang
 * 时间    2019/1/21 12:30
 * 文件    InspectionOfAssets
 * 描述   检查数据库中是否存在当前Excel表名
 */
public interface MainCheckCallBack {

    // 检查数据库中是否存在当前Excel表名（查看数据量是否大于0）
    void checkDataToDb(int count);

}
