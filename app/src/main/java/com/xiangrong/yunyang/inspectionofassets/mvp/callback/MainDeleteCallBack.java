package com.xiangrong.yunyang.inspectionofassets.mvp.callback;

/**
 * 作者    yunyang
 * 时间    2019/1/21 12:30
 * 文件    InspectionOfAssets
 * 描述   检查数据库中是否存在当前Excel表名
 */
public interface MainDeleteCallBack {

    // 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
    void ExcelDataToDb(String select_text_string);

}
