package com.xiangrong.yunyang.inspectionofassets.entity;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:54
 * 文件    InspectionOfAssets
 * 描述   App信息
 */
public class SystemInfo {
    /**
     * 版本ID
     */
    private String versionId;
    /**
     * 更新内容
     */
    private String updateContent;
    /**
     * 安装包路径
     */
    private String androidInstallPath;
    /**
     * 最新版本号
     */
    private int versionCode;
    /**
     * 版本名称
     */
    private String versionName;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getAndroidInstallPath() {
        return androidInstallPath;
    }

    public void setAndroidInstallPath(String androidInstallPath) {
        this.androidInstallPath = androidInstallPath;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
