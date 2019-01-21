package com.xiangrong.yunyang.inspectionofassets.entity;

/**
 * 作者    yunyang
 * 时间    2019/1/21 15:30
 * 文件    InspectionOfAssets
 * 描述   条码
 */
public class BarCode {

    // 条码
    private String barCodeText;
    // 类型
    private String barCodeType;
    // 长度
    private int barCodeLength;

    public BarCode(String barCodetext, String barCodeType, int barCodeLength) {
        this.barCodeText = barCodetext;
        this.barCodeType = barCodeType;
        this.barCodeLength = barCodeLength;
    }

    public String getBarCodeText() {
        return barCodeText;
    }

    public void setBarCodeText(String barCodeText) {
        this.barCodeText = barCodeText;
    }

    public String getBarCodeType() {
        return barCodeType;
    }

    public void setBarCodeType(String barCodeType) {
        this.barCodeType = barCodeType;
    }

    public int getBarCodeLength() {
        return barCodeLength;
    }

    public void setBarCodeLength(int barCodeLength) {
        this.barCodeLength = barCodeLength;
    }

}
