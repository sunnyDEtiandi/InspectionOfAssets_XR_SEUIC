package com.xiangrong.yunyang.inspectionofassets.mvp.model;

import android.os.AsyncTask;

import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanFindCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.scan.ScanUpdateUI;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.ScanContract;
import com.xiangrong.yunyang.inspectionofassets.utils.StrUtil;

import org.litepal.LitePal;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:25
 * 文件    InspectionOfAssets
 * 描述   ScanActivity（盘点）的数据类
 */
public class ScanModel implements ScanContract.Model {

    private List<School> mSchool;

    private boolean mQueryNumberTrue = false;

    @Override
    public void assetsCodeFindDb(String barCodeText, ScanFindCallBack scanFindCallBack) {
        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                try {
                    mSchool = LitePal
                            .findAll(School.class);
                    for (School school :
                            mSchool) {
                        if (school.getAssetNumber().equals(params[0])) {
                            mQueryNumberTrue = true;
                            mSchool = LitePal
                                    .where("assetNumber = ?", params[0])
                                    .find(School.class);
                            return 1;
                        }
                    }
                    return 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    if (mQueryNumberTrue) {
                        mQueryNumberTrue = false;
                        scanFindCallBack.inventorySuccess();
                    } else {
                        scanFindCallBack.inventoryFailure();
                    }
                } else {
                    scanFindCallBack.inventoryFailure();
                }
            }
        }.execute(barCodeText);
    }

    @Override
    public void updateDbFromPhy(ScanUpdateUI scanUpdateUI) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                try {
                    /*
                            账面数量只供参考，
                            如果是1，那就仅有一套（不弹输入框），
                            如果是>1，那就一个标签对应若干套（弹输入框）。

                            参考账面数量，去输入（盘点数量）实有数量。
                            根据实际情况输入到实有数量，如果实有数量小于等于账面数量，那么就直接更新数据库。
                            如果输入实有数量大于账面数量，那么就弹框（是否确定无盈亏（因为输入实有数量
                        大于账面数量））。
                            如果第一次输入实有数量小于账面数量，第二次输入实有数量的时候就累加。
                        然后做判断是否小于等于账面数量。
                             */
                    final School school = mSchool.get(0);
                    // 实有数量
                    String actualNumberOf = school.getActualNumberOf();
                    // 账面数量
                    String physicalCountQuantity = school.getPhysicalCountQuantity();

                    // String数据类型转换为int数据类型，取掉小数点|判空操作
                    actualNumberOf = StrUtil.isNullOrEmptyAndSub(actualNumberOf);
                    physicalCountQuantity = StrUtil.isNullOrEmptyAndSub(physicalCountQuantity);

                    int actualNumberOfInt = Integer.parseInt(actualNumberOf);
                    int physicalCountQuantityInt = Integer.parseInt(physicalCountQuantity);

                    // 如果账面数量等于1，（不弹输入框）仅有一套
                    if (physicalCountQuantityInt == 1) {
                        if (physicalCountQuantityInt == actualNumberOfInt) {
                            // 如果实有数量和账面数量一样，提醒用户这个资产已经盘过
                            return 3;
                        }
                        if (actualNumberOfInt == 0 && actualNumberOfInt < physicalCountQuantityInt) {
                            school.setActualNumberOf(String.valueOf(actualNumberOfInt + 1));
                            school.setInventoryResults("无盈亏");
                            school.save();
                            return 1;
                        }
                    } else {
                        return 2;
                    }
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                scanUpdateUI.inventoryUpdateUI(mSchool, integer);
            }
        }.execute();
    }
}
