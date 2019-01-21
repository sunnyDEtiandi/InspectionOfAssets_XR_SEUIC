package com.xiangrong.yunyang.inspectionofassets.utils;

import android.content.Context;
import android.widget.Toast;

import com.xiangrong.yunyang.inspectionofassets.entity.School;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:32
 * 文件    InspectionOfAssets
 * 描述   Excel操作类
 */
public class ExcelUtils {

    public static WritableFont arial14font = null;
    public static WritableCellFormat arial14format = null;

    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;

    public static WritableFont arial10fonobg = null;
    public static WritableCellFormat arial10formatnobg = null;
//	public static WritableFont arial12font = null;
//	public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14,
                    WritableFont.NO_BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.NONE);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.NO_BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.NONE);
            arial10format.setBackground(jxl.format.Colour.RED);

            arial10fonobg = new WritableFont(WritableFont.ARIAL, 10,
                    WritableFont.NO_BOLD);
            arial10formatnobg = new WritableCellFormat(arial10fonobg);
            arial10formatnobg.setAlignment(jxl.format.Alignment.CENTRE);
            arial10formatnobg.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.NONE);

//			arial12font = new WritableFont(WritableFont.ARIAL, 12);
//			arial12format = new WritableCellFormat(arial12font);
//			arial12format.setBorder(jxl.format.Border.ALL,
//					jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {

            e.printStackTrace();
        }
    }

    public static void initExcel(String fileName, String[] colName, String titles) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("资产盘点", 0);
            sheet.getSettings().setDefaultColumnWidth(20);
            //sheet.addCell((WritableCell) new Label(0, 0, fileName,arial14format));
            String[] str = titles.split("~");
            sheet.addCell(new Label(0, 0, str[0].toString(), arial10formatnobg));
            sheet.addCell(new Label(1, 0, str[1].toString(), arial10formatnobg));
            sheet.addCell(new Label(2, 0, str[2].toString(), arial10formatnobg));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 1, colName[col], arial10formatnobg));
            }
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void initExcel2016(String filePath, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("页签1", 0);
            sheet.getSettings().setDefaultColumnWidth(20);
            sheet.addCell((WritableCell) new Label(0, 0, filePath,
                    arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10formatnobg));
            }
            sheet.addCell(new Label(0, 1, "1", arial10formatnobg));
            sheet.addCell(new Label(1, 1, "2", arial10formatnobg));
            sheet.addCell(new Label(2, 1, "3", arial10formatnobg));
            sheet.addCell(new Label(3, 1, "4", arial10formatnobg));
            sheet.addCell(new Label(4, 1, "16", arial10formatnobg));
            sheet.addCell(new Label(5, 1, "17", arial10formatnobg));
            sheet.addCell(new Label(6, 1, "18", arial10formatnobg));
            sheet.addCell(new Label(7, 1, "20", arial10formatnobg));
            sheet.addCell(new Label(8, 1, "21", arial10formatnobg));
            sheet.addCell(new Label(9, 1, "7", arial10formatnobg));
            sheet.addCell(new Label(10, 1, "12", arial10formatnobg));
            sheet.addCell(new Label(11, 1, "13", arial10formatnobg));
            sheet.addCell(new Label(12, 1, "14", arial10formatnobg));
            sheet.addCell(new Label(13, 1, "15", arial10formatnobg));
            sheet.addCell(new Label(14, 1, "5", arial10formatnobg));
            sheet.addCell(new Label(15, 1, "6", arial10formatnobg));
            sheet.addCell(new Label(16, 1, "8", arial10formatnobg));
            sheet.addCell(new Label(17, 1, "9", arial10formatnobg));
            sheet.addCell(new Label(18, 1, "10", arial10formatnobg));
            sheet.addCell(new Label(19, 1, "11", arial10formatnobg));
            sheet.addCell(new Label(20, 1, "22", arial10formatnobg));
            sheet.addCell(new Label(21, 1, "23", arial10formatnobg));
            sheet.addCell(new Label(22, 1, "24", arial10formatnobg));
            sheet.addCell(new Label(23, 1, "25", arial10formatnobg));
            sheet.addCell(new Label(24, 1, "26", arial10formatnobg));
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(List<T> objList,
                                               String fileName, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(fileName),
                        workbook);
                //WritableSheet sheet = writebook.createSheet("资产盘点", 0);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
						/*if(i==10)
						{
							int tp = Integer.parseInt(list.get(5))- Integer.parseInt(list.get(3));
							if(tp==0)
							{
							sheet.addCell(new Label(i, j + 1, "无盈亏",arial10formatnobg));
							}
							else if(tp>0)
							{
								sheet.addCell(new Label(i, j + 1, "盘亏",arial10formatnobg));
								}
							else if(tp<0)
							{
								sheet.addCell(new Label(i, j + 1, "盘盈",arial10formatnobg));
							}
						}
						else
						{*/
                        sheet.addCell(new Label(i, j + 1, list.get(i), arial10formatnobg));
                        /*}*/
                    }
                }
                writebook.write();
//                Toast.makeText(c, String.format("%s成功导出到巴枪根目录！", GlobalApplication.getInstance().getFileName()), Toast.LENGTH_SHORT).show();
                Toast.makeText(c, "writeObjListToExcel有需要导出的数据！", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            //Toast.makeText(c, String.format("%s成功导出到巴枪根目录！", GlobalApplication.getInstance().getFileName()), Toast.LENGTH_SHORT).show();
            Toast.makeText(c, "没有需要导出的数据！", Toast.LENGTH_SHORT).show();
        }
    }

    public static <T> void writeObjListToExcel2016(List<T> objList,
                                                   String filePath, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(filePath));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(filePath),
                        workbook);
                //WritableSheet sheet = writebook.createSheet("页签1", 0);
                WritableSheet sheet = writebook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
//                    for (int i = 0; i < list.size() - 1; i++) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 7) {
                            int tp = Integer.parseInt(list.get(10)) - Integer.parseInt(list.get(4));
                            if (tp == 0) {
                                sheet.addCell(new Label(i, j + 1, "无盈亏", arial10formatnobg));
                            } else if (tp > 0) {
                                sheet.addCell(new Label(i, j + 1, "盘亏", arial10formatnobg));
                            } else if (tp < 0) {
                                sheet.addCell(new Label(i, j + 1, "盘盈", arial10formatnobg));
                            }
                        } else {
                            sheet.addCell(new Label(i, j + 1, list.get(i), arial10formatnobg));
                        }
                    }
                }
                WritableSheet sheet2 = writebook.createSheet("hidesheet", 1);
                sheet2.setHidden(true);
                sheet2.addCell(new Label(0, 0, "盘盈", arial10formatnobg));
                sheet2.addCell(new Label(1, 0, "无盈亏", arial10formatnobg));
                sheet2.addCell(new Label(2, 0, "盘亏", arial10formatnobg));
                sheet2.addCell(new Label(0, 1, "盘盈", arial10formatnobg));
                sheet2.addCell(new Label(0, 2, "无盈亏", arial10formatnobg));
                sheet2.addCell(new Label(1, 2, "待报废", arial10formatnobg));
                sheet2.addCell(new Label(2, 2, "挂账", arial10formatnobg));
                sheet2.addCell(new Label(0, 3, "计入无形资产", arial10formatnobg));
                sheet2.addCell(new Label(1, 3, "计入固定资产", arial10formatnobg));
                sheet2.addCell(new Label(2, 3, "未入账", arial10formatnobg));
                sheet2.addCell(new Label(0, 7, "此页签用于级联下拉框，请勿删除或修改！", arial10formatnobg));

                WritableSheet sheet3 = writebook.createSheet("填写说明", 2);
                sheet3.addCell(new Label(0, 1, "资产分类：", arial10formatnobg));
                sheet3.addCell(new Label(1, 1, "、“土地使用权”、“非专利技术”、“商誉以及其他财产权利”、“其他”  进行选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 2, "取得方式：", arial10formatnobg));
                sheet3.addCell(new Label(1, 2, "按 “新购”、“调拨”、“接受捐赠”、“置换”、“盘盈”、“自行研制”、“其他” 选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 3, "价值类型：", arial10formatnobg));
                sheet3.addCell(new Label(1, 3, "按 “原值”、“暂估值”、“重置值”、“评估值”、“无价值”、“名义金额”  选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 4, "取得日期/财务入账日期：", arial10formatnobg));
                sheet3.addCell(new Label(1, 4, "日期格式按照“年-月-日”格式填写，如2015年1月2日应填写为：2015-01-02", arial10formatnobg));
                sheet3.addCell(new Label(0, 5, "盘点结果：", arial10formatnobg));
                sheet3.addCell(new Label(1, 5, "按 “盘盈”、“无盈亏”、“盘亏” 选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 6, "使用状况：", arial10formatnobg));
                sheet3.addCell(new Label(1, 6, "按“在用”、“出租出借”、“闲置”、“其他”选择填列，针对土地、房屋（除房屋附属设施）类资产只能按“存在出租出借”、“不存在出租出借”选择填列", arial10formatnobg));

                writebook.write();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
        }
    }

    public static <T> void writeSchoolListToExcel(List<School> objList,
                                                  String filePath, Context c) {
        if (objList != null && objList.size() > 0) {
            final int countDb = objList.size();
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(filePath));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(filePath),
                        workbook);
                //WritableSheet sheet = writebook.createSheet("页签1", 0);
                WritableSheet sheet = writebook.getSheet(0);

                for (int j = 1; j < countDb; j++) {
                    School school = objList.get(j);

                    String actualNumberOf = school.getActualNumberOf();
                    String physicalCountQuantity = school.getPhysicalCountQuantity();

                    // String数据类型转换为int数据类型，取掉小数点|判空操作
                    actualNumberOf = StrUtil.isNullOrEmptyAndSub(actualNumberOf);
                    physicalCountQuantity = StrUtil.isNullOrEmptyAndSub(physicalCountQuantity);

                    sheet.addCell(new Label(0, j + 1, school.getAssetNumber(), arial10formatnobg));
                    sheet.addCell(new Label(1, j + 1, school.getAssetName(), arial10formatnobg));
                    sheet.addCell(new Label(2, j + 1, school.getAssetClassification(), arial10formatnobg));
                    sheet.addCell(new Label(3, j + 1, school.getNationalStandardClassification(), arial10formatnobg));
                    sheet.addCell(new Label(4, j + 1, actualNumberOf, arial10formatnobg));
                    sheet.addCell(new Label(5, j + 1, school.getActualValue(), arial10formatnobg));
                    sheet.addCell(new Label(6, j + 1, school.getActualAccumulatedDepreciation(), arial10formatnobg));

                    int tp = Integer.parseInt(physicalCountQuantity) - Integer.parseInt(actualNumberOf);
                    if (tp == 0) {
                        sheet.addCell(new Label(7, j + 1, "无盈亏", arial10formatnobg));
                    } else if (tp > 0) {
                        sheet.addCell(new Label(7, j + 1, "盘亏", arial10formatnobg));
                    } else if (tp < 0) {
                        sheet.addCell(new Label(7, j + 1, "盘盈", arial10formatnobg));
                    }

                    sheet.addCell(new Label(8, j + 1, school.getUseStatus(), arial10formatnobg));
                    sheet.addCell(new Label(9, j + 1, school.getSerialNumber(), arial10formatnobg));
                    sheet.addCell(new Label(10, j + 1, physicalCountQuantity, arial10formatnobg));
                    sheet.addCell(new Label(11, j + 1, school.getBookValue(), arial10formatnobg));
                    sheet.addCell(new Label(12, j + 1, school.getBookDepreciation(), arial10formatnobg));
                    sheet.addCell(new Label(13, j + 1, school.getNetBookValue(), arial10formatnobg));
                    sheet.addCell(new Label(14, j + 1, school.getGainingMethod(), arial10formatnobg));
                    sheet.addCell(new Label(15, j + 1, school.getSpecificationsAndModels(), arial10formatnobg));
                    sheet.addCell(new Label(16, j + 1, school.getUnitOfMeasurement(), arial10formatnobg));
                    sheet.addCell(new Label(17, j + 1, school.getDateOfAcquisition(), arial10formatnobg));
                    sheet.addCell(new Label(18, j + 1, school.getDateOfFinancialEntry(), arial10formatnobg));
                    sheet.addCell(new Label(19, j + 1, school.getTypeOfValue(), arial10formatnobg));
                    sheet.addCell(new Label(20, j + 1, school.getStoragePlace(), arial10formatnobg));
                    sheet.addCell(new Label(21, j + 1, school.getUserDepartment(), arial10formatnobg));
                    sheet.addCell(new Label(22, j + 1, school.getUser(), arial10formatnobg));
                    sheet.addCell(new Label(23, j + 1, school.getOriginalAssetNumber(), arial10formatnobg));
                    sheet.addCell(new Label(24, j + 1, school.getRemark(), arial10formatnobg));
                }

                WritableSheet sheet2 = writebook.createSheet("hidesheet", 1);
                /*
                    隐藏表
                    表是隐藏的，一般都是表回到大系统的时候会有这个。
                    不能忽略，如果忽略，那么表回归大系统的时候就会报错。
                 */
                // 隐藏表
                sheet2.setHidden(true);
                sheet2.addCell(new Label(0, 0, "盘盈", arial10formatnobg));
                sheet2.addCell(new Label(1, 0, "无盈亏", arial10formatnobg));
                sheet2.addCell(new Label(2, 0, "盘亏", arial10formatnobg));
                sheet2.addCell(new Label(0, 1, "盘盈", arial10formatnobg));
                sheet2.addCell(new Label(0, 2, "无盈亏", arial10formatnobg));
                sheet2.addCell(new Label(1, 2, "待报废", arial10formatnobg));
                sheet2.addCell(new Label(2, 2, "挂账", arial10formatnobg));
                sheet2.addCell(new Label(0, 3, "计入无形资产", arial10formatnobg));
                sheet2.addCell(new Label(1, 3, "计入固定资产", arial10formatnobg));
                sheet2.addCell(new Label(2, 3, "未入账", arial10formatnobg));
                sheet2.addCell(new Label(0, 7, "此页签用于级联下拉框，请勿删除或修改！", arial10formatnobg));

                WritableSheet sheet3 = writebook.createSheet("填写说明", 2);
                sheet3.addCell(new Label(0, 1, "资产分类：", arial10formatnobg));
                sheet3.addCell(new Label(1, 1, "、“土地使用权”、“非专利技术”、“商誉以及其他财产权利”、“其他”  进行选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 2, "取得方式：", arial10formatnobg));
                sheet3.addCell(new Label(1, 2, "按 “新购”、“调拨”、“接受捐赠”、“置换”、“盘盈”、“自行研制”、“其他” 选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 3, "价值类型：", arial10formatnobg));
                sheet3.addCell(new Label(1, 3, "按 “原值”、“暂估值”、“重置值”、“评估值”、“无价值”、“名义金额”  选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 4, "取得日期/财务入账日期：", arial10formatnobg));
                sheet3.addCell(new Label(1, 4, "日期格式按照“年-月-日”格式填写，如2015年1月2日应填写为：2015-01-02", arial10formatnobg));
                sheet3.addCell(new Label(0, 5, "盘点结果：", arial10formatnobg));
                sheet3.addCell(new Label(1, 5, "按 “盘盈”、“无盈亏”、“盘亏” 选择填列", arial10formatnobg));
                sheet3.addCell(new Label(0, 6, "使用状况：", arial10formatnobg));
                sheet3.addCell(new Label(1, 6, "按“在用”、“出租出借”、“闲置”、“其他”选择填列，针对土地、房屋（除房屋附属设施）类资产只能按“存在出租出借”、“不存在出租出借”选择填列", arial10formatnobg));

                writebook.write();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
        }
    }

    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
                .substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}

