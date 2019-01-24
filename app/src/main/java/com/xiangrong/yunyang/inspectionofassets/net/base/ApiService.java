package com.xiangrong.yunyang.inspectionofassets.net.base;

import com.xiangrong.yunyang.inspectionofassets.entity.SystemInfo;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:23
 * 文件    InspectionOfAssets
 * 描述   网络Api接口
 */
public interface ApiService {

    /**
     * 接口Url
     */
    String HOST = "http://47.104.211.140:90/";
    String API_SERVER_URL = HOST + "SystemInfoService/returnInfo/";

    /**
     * 图片配置
     */
    String IMAGE_URL = HOST + "Images/";

    /**
     * 获取App信息
     */
    @POST("returnUrl")
    Observable<ResultResponse<SystemInfo>> getSystemInfo();

}
