package com.xiangrong.yunyang.inspectionofassets.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:13
 * 文件    InspectionOfAssets
 * 描述   加载Dialog
 */
public class LoadingDialog extends ProgressDialog {
    public LoadingDialog(Context context, String str) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage(str);
    }
}
