package com.xiangrong.yunyang.inspectionofassets.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.seuic.scanner.DecodeInfo;
import com.seuic.scanner.DecodeInfoCallBack;
import com.seuic.scanner.Scanner;
import com.seuic.scanner.ScannerFactory;
import com.seuic.scanner.ScannerKey;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:28
 * 文件    InspectionOfAssets
 * 描述   ScannerService
 */
public class ScannerService extends Service implements DecodeInfoCallBack {

    Scanner scanner;
    /* private static MainActivity mcontext = null;*/
    private boolean mScanRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        /*ScannerFactor 扫描工厂类，主要用于获取扫描头资源*/
        /*Scanner 扫描主要实现类，定义了一系列扫描操作方法*/
        /*获取扫描类实现*/
        scanner = ScannerFactory.getScanner(this);
        /*打开扫描头*/
        scanner.open();
        /*设置扫描条码回调，包含条码的数据、长度、类型*/
        scanner.setDecodeInfoCallBack(this);
        /*scanner.setStatusCallBack(this);*/
        //启用扫描头
        scanner.enable();
        mScanRunning = true;
        new Thread(runnable).start();
    }

    ;

    Runnable runnable = new Runnable() {
        /*ScannerKey  扫描按键监听类*/
        @Override
        public void run() {
            /*打开扫描键监听，返回 1：打开成功 -1: 打开失败 */
            int ret1 = ScannerKey.open();
            if (ret1 > -1) {
                while (mScanRunning) {
                    /* 获取按键事件 返回： 1：扫描键按下,0 ：扫描键抬起 , -1：获取失败 ：*/
                    int ret = ScannerKey.getKeyEvent();
                    if (ret > -1) {
                        switch (ret) {
                            case ScannerKey.KEY_DOWN:
                                if (scanner != null && mScanRunning) {
                                    scanner.startScan();
                                }
                                break;
                            case ScannerKey.KEY_UP:
                                if (scanner != null && mScanRunning) {
                                    scanner.stopScan();
                                }
                                break;
                        }
                    }
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mScanRunning = false;
        ScannerKey.close();
        scanner.setDecodeInfoCallBack(null);
        scanner.setVideoCallBack(null);
        scanner.close();
        scanner = null;
        super.onDestroy();
    }

    public static final String BAR_CODE = "barcode";
    public static final String CODE_TYPE = "codetype";
    public static final String LENGTH = "length";

    // this is a custom broadcast receiver action
//    public static final String ACTION = "seuic.android.scanner.scannertestreciever";
    public static final String ACTION = "xiangrong.yunyang.scanservice";

    /*设置用户扫描结束后的回调函数，该函数是获取包括条码据、 长度类型的信息。
     * 在设置之前确保实现了DecodeInfoCallBack 中的 onDecodeComplete方法
     * DecodeInfo 扫描数据类
     * */
    @Override
    public void onDecodeComplete(DecodeInfo info) {
        Intent intent = new Intent(ACTION);
        Bundle bundle = new Bundle();
        bundle.putString(BAR_CODE, info.barcode);
        bundle.putString(CODE_TYPE, info.codetype);
        bundle.putInt(LENGTH, info.length);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

}

