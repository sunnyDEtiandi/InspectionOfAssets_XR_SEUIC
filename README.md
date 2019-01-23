# 资产清查App——智能工业级手机——小码哥——MVP架构
##### 2019/01/18——星期五

创建InspectionOfAssets资产清查MVP项目

完成项目框架搭建

##### 2019/01/21——星期一

完成项目框架测试，流程通顺。

完成选择本地还是手机SD卡存储中的Excel（数据）表。

完成帮助页面。

完成导入功能。

完成导出功能。

完成盘点功能（盘点页面）。

完成部分查询功能。

##### 2019/01/22——星期二

完成全部查询功能。

修改导出功能，
之前导出——从本地数据库中导出数据（是依据“所属数据表”列进行导出）
现在在原有基础上增加上实有数据列大于0。

完成删除功能。

DataConversion项目修改为Mvp架构InspectionOfAssets项目结束。

适配Android6.0权限机制。

适配Android8.0图标。

##### 2019/01/23——星期三

添加注释，使得逻辑一眼望穿。

增加BaseMvpFragment的onCreate方法，里面设置setRetainInstance为 true，表示 当activity configuration change 的时候，fragment 实例不会被重新创建。

代码混淆，减少无用代码。

Zipalign优化。

移除无用的resource文件。

添加自动更新功能。