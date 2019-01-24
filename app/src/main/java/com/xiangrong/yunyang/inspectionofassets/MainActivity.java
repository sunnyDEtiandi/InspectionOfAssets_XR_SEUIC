package com.xiangrong.yunyang.inspectionofassets;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.xiangrong.yunyang.inspectionofassets.adapter.IndexRecyAdapter;
import com.xiangrong.yunyang.inspectionofassets.base.BaseMvpPresenterActivity;
import com.xiangrong.yunyang.inspectionofassets.entity.CurrentFileName;
import com.xiangrong.yunyang.inspectionofassets.entity.ExpandMessage;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.entity.SystemInfo;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.MainContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.presenter.MainPresenter;
import com.xiangrong.yunyang.inspectionofassets.net.base.ApiService;
import com.xiangrong.yunyang.inspectionofassets.net.base.ObserverCallBack;
import com.xiangrong.yunyang.inspectionofassets.net.base.RestClient;
import com.xiangrong.yunyang.inspectionofassets.net.base.ResultResponse;
import com.xiangrong.yunyang.inspectionofassets.service.UpdateService;
import com.xiangrong.yunyang.inspectionofassets.utils.FileUtil;
import com.xiangrong.yunyang.inspectionofassets.utils.StrUtil;
import com.xiangrong.yunyang.inspectionofassets.view.popup.SlideFromBottomPopup;
import com.zhl.userguideview.MeasureUtil;
import com.zhl.userguideview.UserGuideView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseMvpPresenterActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_inventory_title)
    AppCompatTextView tvInventoryTitle;
    @BindView(R.id.select_text_db_file_excel)
    AppCompatTextView selectTextDbFileExcel;
    @BindView(R.id.index_recy_btn_position)
    RecyclerView indexRecyBtnPosition;
    @BindView(R.id.guideView)
    UserGuideView guideView;
    // UserGuideView的tipTextView
    private View tipTextView;

    private IndexRecyAdapter mIndexRecyAdapter;
    private List<String> mRecyTextString;
    private List<Integer> mRecyImageDrawable;

    private List<School> excelDataToDb;

    // 首页的PoPup
    private SlideFromBottomPopup mSlideFromBottomPopup;

    // 当前选中数据表
    private File currentFile;

    // 特定文件夹
    private File mFileDir;

    private Handler mHandler = new Handler();

    // 双击退出
    private long firsTime = 0;

    // 自动更新
    private AlertDialog dlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 创建特定文件夹以存放Excel表
        mFileDir = FileUtil.createDir("XR");
        // 初始化数据库
        LitePal.getDatabase();
        mRecyTextString = new ArrayList<>();
        mRecyImageDrawable = new ArrayList<>();
        excelDataToDb = new ArrayList<>();
        mSlideFromBottomPopup =
                new SlideFromBottomPopup(MainActivity.this, mFileDir.getAbsolutePath());
        initRecy();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initUserGuideView();
            }
        }, 500);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecy() {
        mPresenter.updateRecy();
        mIndexRecyAdapter = new IndexRecyAdapter(this, mRecyTextString, mRecyImageDrawable);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        indexRecyBtnPosition.setLayoutManager(gridLayoutManager);
        indexRecyBtnPosition.setAdapter(mIndexRecyAdapter);
        mIndexRecyAdapter.setLineOnClickListener(new IndexRecyAdapter.LineOnClickListener() {
            @Override
            public void onLineItemOnClick(int position) {
                switch (position) {
                    // 导入
                    case 0:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        checkDbImportExcel();
                        break;
                    // 导出
                    case 1:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        checkDbExportExcel();
                        break;
                    // 盘点
                    case 2:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intentScan = new Intent(MainActivity.this, ScanActivity.class);
                        startActivity(intentScan);
                        break;
                    // 查询
                    case 3:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intentQueryData = new Intent(MainActivity.this, QueryDataActivity.class);
                        startActivity(intentQueryData);
                        break;
                    // 移除
                    case 4:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (currentFile != null) {
                            checkDbDeleteExcel();
                        } else {
                            Toast.makeText(MainActivity.this, "请选中要从本地数据库中删除之前导入的Excel文件名称", Toast.LENGTH_LONG).show();
                        }
                        break;
                    // 帮助
                    case 5:
                        Intent intentHelper = new Intent(MainActivity.this, HelperActivity.class);
                        startActivity(intentHelper);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 删除
     */
    private void checkDbDeleteExcel() {
        mPresenter.checkExcelCountDelete(currentFile.getName());
    }

    /**
     * 导出
     */
    private void checkDbExportExcel() {
        mPresenter.exportExcel(currentFile.getName(), MainActivity.this);
        mPresenter.deleteExcel(currentFile.getName(), false, MainActivity.this);
    }

    /**
     * 导入
     */
    private void checkDbImportExcel() {
        mPresenter.checkNameFromDb(currentFile.getName());
    }

    /**
     * 初始化引导语
     */
    private void initUserGuideView() {
        tipTextView = LayoutInflater.from(this).inflate(R.layout.custom_index_tipview, null);
        guideView.setHighLightView(selectTextDbFileExcel);
        guideView.setStatusBarHeight(MeasureUtil.getStatuBarHeight(this));
        guideView.setTipView(tipTextView, 800, 200);
    }

    /**
     * 双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondtime = System.currentTimeMillis();
            if (secondtime - firsTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次返回键退出",
                        Toast.LENGTH_SHORT).show();
                firsTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * select_text_db_file_excel 控件的点击事件
     */
    @OnClick(R.id.select_text_db_file_excel)
    public void onViewClicked() {
        // 申请权限
        MainActivityPermissionsDispatcher.initFileDirWithCheck(MainActivity.this);
    }

    /**
     * EventBus的注册
     */
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * EventBus的解注册
     */
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 点击底部弹出框中某一项，将Item项名称（也就是Excel表名）返回给MainActivity，让
     * selectTextDbFileExcel 控件进行修改文字，选中那张表。以及当前选中的文件。
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ExpandMessage expandMessage) {
        final String fileName = expandMessage.getFileName();
        selectTextDbFileExcel.setText(getString(R.string.text_task, fileName));
        EventBus
                .getDefault()
                .postSticky(new CurrentFileName(fileName));
        for (File file :
                expandMessage.getSubFileVector()) {
            if (file.getName().equals(fileName)) {
                currentFile = file;
            }
        }
    }

    /**
     * 更新RecyclerView的列表数据
     */
    @Override
    public void updateRecyData(List<String> recyTextData, List<Integer> recyDrawData) {
        mRecyTextString.clear();
        mRecyImageDrawable.clear();
        mRecyTextString.addAll(recyTextData);
        mRecyImageDrawable.addAll(recyDrawData);
    }

    /**
     * 检查导入的数据库是否存在，如果存在删除，重新导入（覆盖效果）
     */
    @Override
    public void checkExcelNameFromDb(int count) {
        if (count > 0) {
            new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("本地数据库中已经存在 "
                            + StrUtil.getFileNameNoEx(currentFile.getName())
                            + " Excel表，是否覆盖导入？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            if (currentFile != null) {
                                mPresenter.deleteExcel(currentFile.getName(), false, MainActivity.this);
                                mPresenter.importExcel(currentFile);
                            } else {
                                Toast.makeText(MainActivity.this, "请选中要从本地数据库中覆盖之前导入的Excel文件名称", Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            mPresenter.importExcel(currentFile);
        }
    }

    /**
     * 导入完成
     */
    @Override
    public void importExcelComplete(List<School> list, String currentFileName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                excelDataToDb.clear();
                excelDataToDb.addAll(list);
                selectTextDbFileExcel.setText(getString(R.string.text_task, currentFileName));
            }
        });
    }

    /**
     * 操作成功
     */
    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 操作失败
     */
    @Override
    public void onFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除本地数据库后，修改首页 selectTextDbFileExcel 文字
     */
    @Override
    public void deleteExcel(String select_text_string) {
        selectTextDbFileExcel.setText(select_text_string);
    }

    /**
     * 导出本地数据库后，修改首页 selectTextDbFileExcel 文字
     */
    @Override
    public void exportExcel(String select_text_string) {
        selectTextDbFileExcel.setText(select_text_string);
    }

    /**
     * 检查本地数据库，如果有数据，弹出Dialog，是否从本地数据库中移除当前Excel表格数据
     */
    @Override
    public void checkExcelCountDelete(int count) {
        if (count > 0) {
            new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("是否从本地数据库中移除当前Excel表格数据")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            mPresenter.deleteExcel(currentFile.getName(), true, MainActivity.this);
                            Toast.makeText(MainActivity.this, "从本地数据库中删除 " + currentFile.getName() + " 成功", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            Toast.makeText(MainActivity.this, "本地数据库中无当前Excel表格数据", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 自动更新
     * 获取APP最新版本信息
     */
    private void getNewVersion() {
        RestClient
                .getRestClient()
                .getSystemInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverCallBack<SystemInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    protected void onSuccess(SystemInfo response) {
                        if (response != null) {
                            if (response.getVersionCode() > getLocalVersion(MainActivity.this)) {
                                if (response.getAndroidInstallPath().endsWith(".apk")) {
                                    getData(response.getVersionName(), response.getUpdateContent(),
                                            response.getAndroidInstallPath());
                                }
                            }
                        }
                    }

                });
    }

    /**
     * 有新版本，显示更新弹窗
     */
    private void getData(String versionName, String msg, final String path) {
        dlg = new AlertDialog.Builder(this, R.style.dialog).create();
        dlg.show();
        dlg.setCancelable(true);
        Window window = dlg.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_updataversion);
        TextView tvmsg = (TextView) window.findViewById(R.id.updataversion_msg);
        TextView tvcode = (TextView) window.findViewById(R.id.updataversion_title);
        tvcode.setText(getString(R.string.update_text, versionName));
        tvmsg.setText(msg);
        Button button = window.findViewById(R.id.update_cancel);
        Button linearLayout = window.findViewById(R.id.update_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
                UpdateService
                        .Builder
//                        .create(ApiService.HOST + "XRData/" + path)
                        .create(path)
                        .setStoreDir("XRData/app/")  //APK文件夹
                        .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                        .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                        .build(MainActivity.this);
            }
        });
    }

    /**
     * 获取本地软件版本号
     */
    public int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (dlg.isShowing()) {
                dlg.dismiss();
            }
        } catch (NullPointerException e) {
        }
        dlg = null;
    }

    /**
     * Android6.0权限机制
     */
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void initFileDir() {
        // 创建特定文件夹以存放Excel表
        mFileDir = FileUtil.createDir("XR");
        // 自动更新
        getNewVersion();
        // 显示底部弹出框供用户选择Excel表
        mSlideFromBottomPopup.newPopupBottomShow();
    }

    /**
     * 权限申请结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 如果用户未申请权限，再次点击 selectTextDbFileExcel 控件的时候，（使用此功能）告诉用户为什么
     * 要申请此权限，才能使用该功能。
     */
    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void whyReadWritePer(final PermissionRequest request) {
        new QMUIDialog.MessageDialogBuilder(MainActivity.this)
                .setMessage("使用此App需要读写手机存储和启用照相机权限，下一步将继续请求权限")
                .addAction("下一步", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        // 继续执行请求
                        request.proceed();
                        dialog.dismiss();
                    }
                })
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        // 取消执行请求
                        request.cancel();
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
