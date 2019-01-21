package com.xiangrong.yunyang.inspectionofassets.mvp.contract;

import android.content.Context;

import com.xiangrong.yunyang.inspectionofassets.base.IBaseModel;
import com.xiangrong.yunyang.inspectionofassets.base.IBaseView;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.MainCheckCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.MainDeleteCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.MainExportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.MainImportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.MainRecyCallBack;

import java.io.File;
import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:52
 * 文件    InspectionOfAssets
 * 描述   MainActivity的契约类
 */
public interface MainContract {

    interface Model extends IBaseModel {

        // RecyclerView的数据集
        void recyInitData(MainRecyCallBack mainRecyCallBack);

        // 检查数据库中是否存在当前Excel表名
        void checkExcelNameFromDb(String currentFileName, MainCheckCallBack checkCallBack);

        // 导入——Excel表格导入到本地数据库中
        void importExcelToDb(File currentFile, MainImportCallBack importCallBack);

        // 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
        void deleteExcel(String currentFileName, Context context, MainDeleteCallBack deleteCallBack);

        // 导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
        void exportExcel(String currentFileName, Context context, MainExportCallBack exportCallBack);

    }

    interface View extends IBaseView {

        // 更新RecyclerView的数据集
        void updateRecyData(List<String> recyTextData, List<Integer> recyDrawData);

        // 检查——返回数据库中数据量
        void checkExcelNameFromDb(int count);

        // 导入——Excel表格导入到本地数据库中
        void importExcelComplete(List<School> list);

        // 操作成功
        void onSuccess();

        // 操作失败
        void onFailure();

        // 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
        void deleteExcel(String select_text_string);

        // 导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
        void exportExcel(String select_text_string);

    }

    interface Presenter {

        // 更新RecyclerView
        void updateRecy();

        // 检查数据库中是否存在当前Excel表名
        void checkNameFromDb(String currentFileName);

        // 导入——Excel表格导入到本地数据库中
        void importExcel(File currentFile);

        // 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
        void deleteExcel(String currentFileName, boolean flag, Context context);

        // 导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
        void exportExcel(String currentFileName, Context context);

    }

}
