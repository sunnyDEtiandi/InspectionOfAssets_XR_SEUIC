package com.xiangrong.yunyang.inspectionofassets.base;

/**
 * 作者    yunyang
 * 文件    MVPDemo
 * 描述   引入泛型概念，让调用者自己去定义具体想要接收的数据类型
 */
public interface IBaseCallback<T> {
    /**
     * 数据请求成功
     *
     * @param data 请求到的数据
     */
    void onSuccess(T data);

    /**
     * 使用网络API接口请求方法时，虽然已经请求成功但是由于{@code msg}的云因无法正常返回数据
     *
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 请求数据失败，只在请求网络API接口请求方法时，出现无法联网、缺少权限，内存泄漏等原因导致无法连接
     * 到请求数据源。
     */
    void onError();

    /**
     * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做处理，通常做网络请求
     * 时可以在此处隐藏“正在加载”的等待控件
     */
    void onComplete();

}
