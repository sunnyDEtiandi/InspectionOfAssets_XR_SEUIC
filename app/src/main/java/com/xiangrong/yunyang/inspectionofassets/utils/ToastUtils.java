package com.xiangrong.yunyang.inspectionofassets.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xiangrong.yunyang.inspectionofassets.app.App;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:47
 * 文件    InspectionOfAssets
 * 描述   Toast工具类
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void show(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void showLong(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public static void toastOk(Context context, String str) {
        final QMUITipDialog tipDialog;
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(str)
                .create();
        tipDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);
    }

    /**
     * 取消Toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}

