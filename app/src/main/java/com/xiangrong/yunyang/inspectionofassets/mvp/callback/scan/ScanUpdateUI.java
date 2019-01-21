package com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan;

import com.xiangrong.yunyang.inspectionofassets.entity.School;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 16:17
 * 文件    InspectionOfAssets
 * 描述   找到数据（依据资产编码），进行更新数据
 */
public interface ScanUpdateUI {

    // 盘点
    void inventoryUpdateUI(List<School> list, int tag);

}
