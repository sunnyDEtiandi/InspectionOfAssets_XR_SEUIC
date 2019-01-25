package com.xiangrong.yunyang.inspectionofassets.entity;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:50
 * 文件    InspectionOfAssets
 * 描述   接口返回数据
 */
public class ResultResponse<T> {
    public int code;
    public boolean status;
    public String message;
    public T info;

    public ResultResponse(int code, boolean more, String _message, T result) {
        code = code;
        status = more;
        message = _message;
        info = result;
    }

}
