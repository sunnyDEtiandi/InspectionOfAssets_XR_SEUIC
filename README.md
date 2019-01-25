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

##### 2019/01/24——星期四

添加自动更新功能

- 完成网络框架——Retrofit2 + RxJava2 + OkHttp3
- 完成自动更新逻辑
- 完成权限添加——安装app的实现类，其中Android8.0安装时需要检测和申请app可安装未知来源权限允许
- 完成Android7.0实现自动更新需要文件共享FileProvider的使用

全面屏适配（包括刘海屏）。

华为刘海适配 增加这个属性之后，系统就不会对应用进行下移处理。

取消混淆优化

* Zipalign优化。
* 移除无用的resource文件。

##### 2019/01/25——星期五

优化修改混淆配置文件

- Zipalign优化。
- 移除无用的resource文件。

由于之前加上混淆之后，网络请求访问不通，一直在查找原因，昨天发现，原来是在net.base包下还有entity实体类未被混淆。（因为之前已经在混淆文件中加上了混淆具体entity包下的实体类）最后把net.base包下的ResultResponse.java和ResultResponseWeb.java实体类放到混淆的具体的entity包下。最后网络请求访问成功。

Android屏幕适配，以宽为基准进行适配
