package com.xiangrong.yunyang.inspectionofassets.mvp.callback.main;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:52
 * 文件    InspectionOfAssets
 * 描述   MainActivity的RecyclerView数据接口回调
 */
public interface MainRecyCallBack {

    // RecyclerView的数据
    void recyData(List<String> recyTextData, List<Integer> recyDrawData);

}
