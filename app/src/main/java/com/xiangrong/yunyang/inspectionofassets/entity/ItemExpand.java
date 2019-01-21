package com.xiangrong.yunyang.inspectionofassets.entity;

/**
 * 作者    yunyang
 * 时间    2019/1/21 11:49
 * 文件    InspectionOfAssets
 * 描述   ExpandableListView的Item的Entity
 */
public class ItemExpand {

    private String itemName;

    public ItemExpand(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
