package com.xiangrong.yunyang.inspectionofassets.mvp.presenter;

import android.content.Context;

import com.xiangrong.yunyang.inspectionofassets.base.BasePresenter;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainCheckCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainDeleteCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainExportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainImportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainRecyCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.MainContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.model.MainModel;

import java.io.File;
import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:52
 * 文件    InspectionOfAssets
 * 描述   MainActivity的控制类
 */
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {
    @Override
    protected MainContract.Model createModule() {
        return new MainModel();
    }

    @Override
    public void start() {

    }

    /**
     * 更新RecyclerView数据
     */
    @Override
    public void updateRecy() {
        if (!isViewAttached()) {
            // 如果没有View引用就不加载数据
            return;
        }
        // 显示正在加载进度条
        getView().showLoading();
        // 请求数据
        getModule().recyInitData(new MainRecyCallBack() {
            @Override
            public void recyData(List<String> recyTextData, List<Integer> recyDrawData) {
                getView().updateRecyData(recyTextData, recyDrawData);
            }
        });
        // 隐藏正在加载进度条
        getView().dismissLoading();
    }

    /**
     * 检查数据库中是否存在当前Excel表名
     */
    @Override
    public void checkNameFromDb(String currentFileName) {
        if (!isViewAttached()) {
            return;
        }
        // 请求数据
        getModule().checkExcelNameFromDb(currentFileName, new MainCheckCallBack() {
            @Override
            public void checkDataToDb(int count) {
                getView().checkExcelNameFromDb(count);
            }
        });
    }

    /**
     * 导入
     */
    @Override
    public void importExcel(File currentFile) {
        if (!isViewAttached()) {
            // 如果没有View引用就不加载数据
            return;
        }
        getView().showLoading();
        getModule().importExcelToDb(currentFile, new MainImportCallBack() {
            @Override
            public void importDataToDb(List<School> list, String currentFileName) {
                getView().importExcelComplete(list, currentFileName);
                getView().dismissLoading();
            }

            @Override
            public void importSuccess() {
                getView().onSuccess();
                getView().dismissLoading();
            }

            @Override
            public void importFailure() {
                getView().onFailure();
                getView().dismissLoading();
            }
        });
    }

    /**
     * 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
     */
    @Override
    public void deleteExcel(String currentFileName, boolean flag, Context context) {
        if (!isViewAttached()) {
            return;
        }
        if (flag) {
            getView().showLoading();
        }
        getModule().deleteExcel(currentFileName, context, new MainDeleteCallBack() {
            @Override
            public void ExcelDataToDb(String select_text_string) {
                getView().deleteExcel(select_text_string);
                if (flag) {
                    getView().dismissLoading();
                }
            }
        });
    }

    /**
     * 导出——从本地数据库中导出数据（是依据“所属数据表”列和实有数据大于0进行导出）
     */
    @Override
    public void exportExcel(String currentFileName, Context context) {
        if (!isViewAttached()) {
            return;
        }
        getView().showLoading();
        getModule().exportExcel(currentFileName, context, new MainExportCallBack() {
            @Override
            public void exportDataToDb(String select_text_string) {
                getView().exportExcel(select_text_string);
                getView().dismissLoading();
            }

            @Override
            public void exportSuccess() {
                getView().onSuccess();
                getView().dismissLoading();
            }

            @Override
            public void exportFailure() {
                getView().onFailure();
                getView().dismissLoading();
            }
        });
    }

    @Override
    public void checkExcelCountDelete(String currentFileName) {
        if (!isViewAttached()) {
            return;
        }
        // 请求数据
        getModule().checkExcelCountDelete(currentFileName, new MainCheckCallBack() {
            @Override
            public void checkDataToDb(int count) {
                getView().checkExcelCountDelete(count);
            }
        });
    }
}
