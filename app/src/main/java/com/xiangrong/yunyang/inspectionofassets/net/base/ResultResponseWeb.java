package com.xiangrong.yunyang.inspectionofassets.net.base;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:51
 * 文件    InspectionOfAssets
 * 描述   接口返回数据(web类型)
 */
public class ResultResponseWeb<T> {
    public int code;
    public String message;
    public T content;

    public ResultResponseWeb(int more, String _message, T result) {
        code = more;
        message = _message;
        content = result;
    }
}
