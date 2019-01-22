package com.xiangrong.yunyang.inspectionofassets.mvp.model;

import android.os.AsyncTask;

import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.querydata.QueryDataDishCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.DishContract;
import com.xiangrong.yunyang.inspectionofassets.utils.StrUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/22 9:12
 * 文件    InspectionOfAssets
 * 描述   DishFragment的数据类
 * 查询数据并更新UI（依据盘点结果和所属数据库列进行查询）
 */
public class DishModel implements DishContract.Model {

    private List<School> mDbToSchoolList;

    /**
     * 查询数据并更新UI（依据盘点结果和所属数据库列进行查询）
     */
    @Override
    public void getDishFragmentDetail(String ownershipDataSheetName, String inventoryResults, QueryDataDishCallBack queryDataDishCallBack) {
        mDbToSchoolList = new ArrayList<>();
        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                try {
                    mDbToSchoolList.clear();
                    if (!StrUtil.isEmpty(ownershipDataSheetName)) {
                        final int countDb = LitePal.count(School.class);
                        if (countDb > 0) {
                            if (params[0].equals("全部")) {
                                mDbToSchoolList = LitePal
                                        .where("ownershipDataSheet = ?", ownershipDataSheetName)
                                        .find(School.class);
                                mDbToSchoolList.remove(0);
                            } else {
                                mDbToSchoolList = LitePal
                                        .where(
                                                "ownershipDataSheet = ? and inventoryResults = ?",
                                                ownershipDataSheetName, params[0])
                                        .find(School.class);
                            }
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
                    queryDataDishCallBack.updateUIInitRecy(mDbToSchoolList);
                } else {
                    queryDataDishCallBack.updateFailure();
                }
            }
        }.execute(inventoryResults);
    }

}
