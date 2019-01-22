package com.xiangrong.yunyang.inspectionofassets.mvp.model;

import android.content.Context;
import android.os.AsyncTask;

import com.xiangrong.yunyang.inspectionofassets.R;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainCheckCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainDeleteCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainExportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainImportCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.callback.main.MainRecyCallBack;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.MainContract;
import com.xiangrong.yunyang.inspectionofassets.utils.ExcelUtils;
import com.xiangrong.yunyang.inspectionofassets.utils.FileUtil;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:52
 * 文件    InspectionOfAssets
 * 描述   MainActivity的数据类
 */
public class MainModel implements MainContract.Model {

    private String[] columnTitle = {"资产编号", "资产名称", "资产分类", "国标分类", "实有数量", "实有原值", "实有累计折旧", "盘点结果", "使用状况", "产品序列号",
            "账面数量", "账面价值", "账面累计折旧", "账面净值", "取得方式", "规格型号", "计量单位", "取得日期", "财务入账日期", "价值类型", "存放地点", "使用部门", "使用人",
            "原资产编号", "备注"};

    // RecyclerView
    private List<String> recyTextData;
    private List<Integer> recyDrawData;

    private List<School> mList;

    /**
     * RecyclerView的数据
     */
    @Override
    public void recyInitData(MainRecyCallBack mainRecyCallBack) {
        mainRecyCallBack.recyData(initRecyTextData(), initRecyDrawData());
    }

    /**
     * 图标资源
     */
    private List<Integer> initRecyDrawData() {
        recyDrawData = new ArrayList<>();

        recyDrawData.add(R.drawable.line_import);
        recyDrawData.add(R.drawable.line_export);
        recyDrawData.add(R.drawable.line_scan);
        recyDrawData.add(R.drawable.line_query);
        recyDrawData.add(R.drawable.line_delete);
        recyDrawData.add(R.drawable.line_help);

        return recyDrawData;
    }

    /**
     * 文字资源
     */
    private List<String> initRecyTextData() {
        recyTextData = new ArrayList<>();

        recyTextData.add("导入");
        recyTextData.add("导出");
        recyTextData.add("盘点");
        recyTextData.add("查询");
        recyTextData.add("移除");
        recyTextData.add("帮助");

        return recyTextData;
    }

    /**
     * 检查数据库中是否存在当前Excel表名
     */
    @Override
    public void checkExcelNameFromDb(String currentFileName, MainCheckCallBack checkCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                final int count = LitePal
                        .where("ownershipDataSheet = ?", currentFileName)
                        .count(School.class);
                return count;
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                checkCallBack.checkDataToDb(aVoid);
            }
        }.execute();
    }

    /**
     * 导入数据
     */
    @Override
    public void importExcelToDb(File currentFile, MainImportCallBack importCallBack) {
        mList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
            /*
                Android 读取Assets 下的Excel文件
                在我们做项目过程中，项目内测时，需要白名单控制，白名单实现的一种方案就是把
            用户的登录名电话号码作为白名单，如果不在白名单内则提示无法登录。此时，
            我们的白名单用户列表保存在Excel文件中，我们可以把Excel文件保存到Assets下进行读取。
             */
//            InputStream is = context.getAssets().open("陕西老年大学_1.xls");
//            Workbook book = Workbook.getWorkbook(is);

                    Workbook book = Workbook.getWorkbook(currentFile);
                    // 获取表页数
                    final int bookPage = book.getNumberOfSheets();
                    // 获得第一个工作表对象
                    Sheet sheet = book.getSheet(0);
                    int Rows = sheet.getRows();
                    School schoolData = null;
                    for (int i = 1; i < Rows; ++i) {
                        String assetNumber = (sheet.getCell(0, i)).getContents();
                        String assetName = (sheet.getCell(1, i)).getContents();
                        String assetClassification = (sheet.getCell(2, i)).getContents();
                        String nationalStandardClassification = (sheet.getCell(3, i)).getContents();
                        String actualNumberOf = (sheet.getCell(4, i)).getContents();
                        String actualValue = (sheet.getCell(5, i)).getContents();
                        String actualAccumulatedDepreciation = (sheet.getCell(6, i)).getContents();
                        String inventoryResults = (sheet.getCell(7, i)).getContents();
                        String useStatus = (sheet.getCell(8, i)).getContents();
                        String serialNumber = (sheet.getCell(9, i)).getContents();
                        String physicalCountQuantity = (sheet.getCell(10, i)).getContents();
                        String bookValue = (sheet.getCell(11, i)).getContents();
                        String bookDepreciation = (sheet.getCell(12, i)).getContents();
                        String netBookValue = (sheet.getCell(13, i)).getContents();
                        String gainingMethod = (sheet.getCell(14, i)).getContents();
                        String specificationsAndModels = (sheet.getCell(15, i)).getContents();
                        String unitOfMeasurement = (sheet.getCell(16, i)).getContents();
                        String dateOfAcquisition = (sheet.getCell(17, i)).getContents();
                        String dateOfFinancialEntry = (sheet.getCell(18, i)).getContents();
                        String typeOfValue = (sheet.getCell(19, i)).getContents();
                        String storagePlace = (sheet.getCell(20, i)).getContents();
                        String userDepartment = (sheet.getCell(21, i)).getContents();
                        String user = (sheet.getCell(22, i)).getContents();
                        String originalAssetNumber = (sheet.getCell(23, i)).getContents();
                        String remark = (sheet.getCell(24, i)).getContents();
                /*
                    25是盘点数量
                    导入的时候把实有数量给盘点数量
                    导出的时候把盘点数量给实有数量
                 */
                        // 导入Db——这里先直接赋值为0
                        String inventoryData = "0";
                        // 26是所属数据表
//                String ownershipDataSheet = "康复所";
                        String ownershipDataSheet = "";
                        ownershipDataSheet = currentFile.getName();
                        schoolData = new School(assetNumber, assetName, assetClassification,
                                nationalStandardClassification, actualNumberOf, actualValue,
                                actualAccumulatedDepreciation, inventoryResults, useStatus,
                                serialNumber, physicalCountQuantity, bookValue,
                                bookDepreciation, netBookValue, gainingMethod, specificationsAndModels,
                                unitOfMeasurement, dateOfAcquisition, dateOfFinancialEntry,
                                typeOfValue, storagePlace, userDepartment, user, originalAssetNumber
                                , remark, inventoryData, ownershipDataSheet);
                        mList.add(schoolData);
                    }
                    book.close();
                    saveDB(mList);
                    importCallBack.importSuccess();
                } catch (Exception e) {
                    importCallBack.importFailure();
                }
            }
        }).start();
    }

    /**
     * 删除——从本地数据库中删除数据（是依据“所属数据表”列进行删除）
     */
    @Override
    public void deleteExcel(String currentFileName, Context context, MainDeleteCallBack deleteCallBack) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    LitePal
                            .deleteAll(
                                    School.class, "ownershipDataSheet = ?",
                                    currentFileName);
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    deleteCallBack.ExcelDataToDb(context.getString(R.string.text_select));
                }
            }
        }.execute();
    }

    /**
     * 导出——从本地数据库中导出数据（是依据“所属数据表”列和实有数据大于0进行导出）
     */
    @Override
    public void exportExcel(String currentFileName, Context context, MainExportCallBack exportCallBack) {
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    // 创建Excel表格
                    ExcelUtils
                            .initExcel2016(FileUtil
                                    .createFile(currentFileName)
                                    .getAbsolutePath(), columnTitle);
                    mList.clear();
                    /*
                        编写导出LitePal语句，导出Excel表格依据26列（所属数据表进行导出）
                     */
                    mList = LitePal
                            .where("ownershipDataSheet = ? and actualNumberOf > 0", currentFileName)
                            .find(School.class);
                    ExcelUtils.writeSchoolListToExcel(mList, FileUtil
                            .createFile(currentFileName)
                            .getAbsolutePath(), context);
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    exportCallBack.exportSuccess();
                } else {
                    exportCallBack.exportFailure();
                }
            }
        }.execute();
    }

    /**
     * 数据保存到数据库中并且去空行
     */
    private void saveDB(List<School> mSchoolListToDb) {
        if (mSchoolListToDb != null) {
            LitePal.saveAll(mSchoolListToDb);
            // 去空行
            LitePal.deleteAll(School.class, "assetNumber = ?", "");
        }
    }

}
