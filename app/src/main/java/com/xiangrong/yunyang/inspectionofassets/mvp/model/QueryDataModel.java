package com.xiangrong.yunyang.inspectionofassets.mvp.model;

import android.os.AsyncTask;

import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.QueryDataContract;

import org.litepal.LitePal;

/**
 * 作者    yunyang
 * 时间    2019/1/21 16:50
 * 文件    InspectionOfAssets
 * 描述   QueryDataActivity的数据类
 */
public class QueryDataModel implements QueryDataContract.Model {

    private String[] mTitles;
    private int[] mInts;

    /**
     * 根据inventoryResults（盘点结果）去数据库中查找对应数据
     */
    @Override
    public void getTitleNameAndCount(String ownershipDataSheetName, QueryDataCallBack queryDataCallBack) {
        mTitles = new String[3];
        mInts = new int[3];
        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                try {
                    if (LitePal.count(School.class) <= 0) {
                        mTitles[0] = "全部";
                        mTitles[1] = "盘亏";
                        mTitles[2] = "无盈亏";
                        return 0;
                    } else {
                        // 全部
                        final int count = LitePal
                                .where("ownershipDataSheet = ?", ownershipDataSheetName)
                                .count(School.class);
                        // 减去行1
                        mInts[0] = count - 1;
                        // 盘亏
                        mInts[1] = LitePal
                                .where("inventoryResults = ? and ownershipDataSheet = ?", "盘亏", ownershipDataSheetName)
                                .count(School.class);
                        // 无盈亏
                        mInts[2] = LitePal
                                .where("inventoryResults = ? and ownershipDataSheet = ?", "无盈亏", ownershipDataSheetName)
                                .count(School.class);
                        mTitles[0] = "全部（" + mInts[0] + "）";
                        mTitles[1] = "盘亏（" + mInts[1] + "）";
                        mTitles[2] = "无盈亏（" + mInts[2] + "）";
                        return 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mTitles[0] = "全部";
                    mTitles[1] = "盘亏";
                    mTitles[2] = "无盈亏";
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                queryDataCallBack.updateUIFragment(mTitles);
            }
        }.execute();
    }

}
