package com.xiangrong.yunyang.inspectionofassets;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.xiangrong.yunyang.inspectionofassets.adapter.IndexRecyAdapter;
import com.xiangrong.yunyang.inspectionofassets.base.BaseMvpPresenterActivity;
import com.xiangrong.yunyang.inspectionofassets.entity.CurrentFileName;
import com.xiangrong.yunyang.inspectionofassets.entity.ExpandMessage;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.MainContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.presenter.MainPresenter;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        // 初始化数据库
        LitePal.getDatabase();
        mRecyTextString = new ArrayList<>();
        mRecyImageDrawable = new ArrayList<>();
        excelDataToDb = new ArrayList<>();
        initFileDir();
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
//                        Intent intentScan = new Intent(MainActivity.this, ScanActivity.class);
//                        startActivity(intentScan);
                        break;
                    // 查询
                    case 3:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        Intent intentQueryData = new Intent(MainActivity.this, QueryDataActivity.class);
//                        startActivity(intentQueryData);
                        break;
                    // 移除
                    case 4:
                        if (selectTextDbFileExcel.getText().toString().trim().equals(getString(R.string.text_select))) {
                            Toast.makeText(MainActivity.this, R.string.text_select, Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        if (currentFile != null) {
//                            checkDbDeleteExcel();
//                        } else {
//                            Toast.makeText(MainActivity.this, "请选中要从本地数据库中删除之前导入的Excel文件名称", Toast.LENGTH_LONG).show();
//                        }
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

    private void checkDbExportExcel() {
        mPresenter.exportExcel(currentFile.getName(), MainActivity.this);
        mPresenter.deleteExcel(currentFile.getName(), false, MainActivity.this);
    }

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
     * 创建特定文件夹以存放Excel表
     */
    private void initFileDir() {
        mFileDir = FileUtil.createDir("XR");
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

    @OnClick(R.id.select_text_db_file_excel)
    public void onViewClicked() {
        mSlideFromBottomPopup.newPopupBottomShow();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

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

    @Override
    public void updateRecyData(List<String> recyTextData, List<Integer> recyDrawData) {
        mRecyTextString.clear();
        mRecyImageDrawable.clear();
        mRecyTextString.addAll(recyTextData);
        mRecyImageDrawable.addAll(recyDrawData);
    }

    @Override
    public void checkExcelNameFromDb(int count) {
        Log.e("神奇奇迹", "count = " + count);
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

    @Override
    public void importExcelComplete(List<School> list) {
        excelDataToDb.clear();
        excelDataToDb.addAll(list);
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteExcel(String select_text_string) {
        selectTextDbFileExcel.setText(select_text_string);
    }

    @Override
    public void exportExcel(String select_text_string) {
        selectTextDbFileExcel.setText(select_text_string);
    }

}
