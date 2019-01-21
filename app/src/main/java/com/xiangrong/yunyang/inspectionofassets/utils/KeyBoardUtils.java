package com.xiangrong.yunyang.inspectionofassets.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:15
 * 文件    InspectionOfAssets
 * 描述   软键盘的打开关闭
 */
public class KeyBoardUtils {

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(AppCompatEditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(AppCompatEditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}
