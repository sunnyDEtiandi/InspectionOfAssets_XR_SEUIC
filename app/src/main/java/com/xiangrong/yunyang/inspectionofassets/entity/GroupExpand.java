package com.xiangrong.yunyang.inspectionofassets.entity;

/**
 * 作者    yunyang
 * 时间    2019/1/21 11:49
 * 文件    InspectionOfAssets
 * 描述   ExpandableListView的Group的Entity
 */
public class GroupExpand {

    private String groupName;

    public GroupExpand(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
