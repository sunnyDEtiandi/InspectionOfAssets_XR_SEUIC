package com.xiangrong.yunyang.inspectionofassets.service;

/**
 * 作者    yunyang
 * 时间    2019/1/23 15:04
 * 文件    InspectionOfAssets
 * 描述   更新App的监听回调
 */
public interface UpdateProgressListener {

    /**
     * download start
     */
    void start();

    /**
     * update download progress
     *
     * @param progress
     */
    void update(int progress);

    /**
     * download success
     */
    void success();

    /**
     * download error
     */
    void error();

}
