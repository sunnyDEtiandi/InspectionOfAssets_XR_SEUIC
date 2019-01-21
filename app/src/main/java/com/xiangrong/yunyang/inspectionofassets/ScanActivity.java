package com.xiangrong.yunyang.inspectionofassets;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.seuic.scanner.Scanner;
import com.seuic.scanner.ScannerFactory;
import com.xiangrong.yunyang.inspectionofassets.base.BaseMvpPresenterActivity;
import com.xiangrong.yunyang.inspectionofassets.entity.BarCode;
import com.xiangrong.yunyang.inspectionofassets.entity.School;
import com.xiangrong.yunyang.inspectionofassets.mvp.contract.ScanContract;
import com.xiangrong.yunyang.inspectionofassets.mvp.presenter.ScanPresenter;
import com.xiangrong.yunyang.inspectionofassets.service.ScannerService;
import com.xiangrong.yunyang.inspectionofassets.utils.KeyBoardUtils;
import com.xiangrong.yunyang.inspectionofassets.utils.StrUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends BaseMvpPresenterActivity<ScanPresenter> implements ScanContract.View {

    @BindView(R.id.title_back_title_layout)
    AppCompatTextView titleBackTitleLayout;
    @BindView(R.id.image_back_title_layout)
    AppCompatImageView imageBackTitleLayout;
    @BindView(R.id.edit_text_string)
    AppCompatEditText editTextString;
    @BindView(R.id.btn_scan)
    AppCompatButton btnScan;

    @BindView(R.id.text_string_zero)
    AppCompatTextView textStringZero;
    @BindView(R.id.text_string_one)
    AppCompatTextView textStringOne;
    @BindView(R.id.text_string_two)
    AppCompatTextView textStringTwo;
    @BindView(R.id.text_string_ten_ten)
    AppCompatTextView textStringTenTen;
    @BindView(R.id.text_string_nine_nine)
    AppCompatTextView textStringNineNine;
    @BindView(R.id.text_string_zero_zero_zero)
    AppCompatTextView textStringZeroZeroZero;
    @BindView(R.id.text_string_zero_zero)
    AppCompatTextView textStringZeroZero;
    @BindView(R.id.text_string_seven_seven)
    AppCompatTextView textStringSevenSeven;
    @BindView(R.id.text_string_ten)
    AppCompatTextView textStringTen;
    @BindView(R.id.text_string_two_two_two)
    AppCompatTextView textStringTwoTwoTwo;
    @BindView(R.id.text_string_four)
    AppCompatTextView textStringFour;
    @BindView(R.id.item_name_include_layout)
    LinearLayoutCompat itemNameIncludeLayout;

    private ScanReceiver mScanReceiver;
    private IntentFilter mIntentFilter;
    private Scanner mScanner;
    private SoundPool spPool;
    private int mIntMusic;                  //声明一个变量 || 可以理解成用来储存歌曲的变量

    private boolean mQueryNumberTrue = false;

    private List<School> mSchoolOnlyOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initView();
        initServiceOrBroadCast();
        initEvent();
    }

    private void initView() {
        titleBackTitleLayout.setText(getString(R.string.text_inventory));
        itemNameIncludeLayout.setVisibility(View.INVISIBLE);
        mSchoolOnlyOne = new ArrayList<>();
    }

    private void initServiceOrBroadCast() {
        // 启动服务
        Intent intent = new Intent(this, ScannerService.class);
        startService(intent);
        // 广播
        mScanReceiver = new ScanReceiver();
        mIntentFilter = new IntentFilter(ScannerService.ACTION);
        mScanner = ScannerFactory.getScanner(this);
        spPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
    }

    private void initEvent() {
        editTextString.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    String string = editTextString.getText().toString().trim();
                    if (TextUtils.isEmpty(string)) {
                        Toast.makeText(ScanActivity.this, "请打开激光头扫描或者输入资产编号进行盘点", Toast.LENGTH_SHORT).show();
                    } else {
                        findDbFromBarCode(string);
                    }
                }
                return false;
            }
        });
    }

    /**
     * 依据资产编码进行数据库查询
     */
    private void findDbFromBarCode(String barCodeText) {
        mPresenter.assetsCodeFindDb(barCodeText);
    }

    private void drawUIFromData() {
        textStringZero.setText(mSchoolOnlyOne.get(0).getAssetNumber());
        textStringOne.setText(mSchoolOnlyOne.get(0).getAssetName());
        textStringTwo.setText(mSchoolOnlyOne.get(0).getAssetClassification());
//        mTextViewThree.setText(mSchoolOnlyOne.get(0).getNationalStandardClassification());
        textStringFour.setText(mSchoolOnlyOne.get(0).getActualNumberOf());
//        mTextViewFive.setText(mSchoolOnlyOne.get(0).getActualValue());
//        mTextViewSix.setText(mSchoolOnlyOne.get(0).getActualAccumulatedDepreciation());
//        mTextViewSeven.setText(mSchoolOnlyOne.get(0).getInventoryResults());
//        mTextViewEight.setText(mSchoolOnlyOne.get(0).getUseStatus());
//        mTextViewNine.setText(mSchoolOnlyOne.get(0).getSerialNumber());
        textStringTen.setText(mSchoolOnlyOne.get(0).getPhysicalCountQuantity());
        textStringZeroZero.setText(mSchoolOnlyOne.get(0).getBookValue());
//        mTextViewOneOne.setText(mSchoolOnlyOne.get(0).getBookDepreciation());
//        mTextViewTwoTwo.setText(mSchoolOnlyOne.get(0).getNetBookValue());
//        mTextViewThreeThree.setText(mSchoolOnlyOne.get(0).getGainingMethod());
//        mTextViewFourFour.setText(mSchoolOnlyOne.get(0).getSpecificationsAndModels());
//        mTextViewFiveFive.setText(mSchoolOnlyOne.get(0).getUnitOfMeasurement());
//        mTextViewSixSix.setText(mSchoolOnlyOne.get(0).getDateOfAcquisition());
        textStringSevenSeven.setText(mSchoolOnlyOne.get(0).getDateOfFinancialEntry());
//        mTextViewEightEight.setText(mSchoolOnlyOne.get(0).getTypeOfValue());
        textStringNineNine.setText(mSchoolOnlyOne.get(0).getStoragePlace());
        textStringTenTen.setText(mSchoolOnlyOne.get(0).getUserDepartment());
        textStringZeroZeroZero.setText(mSchoolOnlyOne.get(0).getUser());
//        mTextViewOneOneOne.setText(mSchoolOnlyOne.get(0).getOriginalAssetNumber());
        textStringTwoTwoTwo.setText(mSchoolOnlyOne.get(0).getRemark());
    }

    @Override
    protected ScanPresenter createPresenter() {
        return new ScanPresenter();
    }

    @OnClick({R.id.image_back_title_layout, R.id.btn_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back_title_layout:
                finish();
                break;
            case R.id.btn_scan:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        // validate
        String string = editTextString.getText().toString().trim();
        if (TextUtils.isEmpty(string)) {
            Toast.makeText(this, "请打开激光头扫描或者输入资产编号进行盘点", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        // 根据资产编号进行查询数据库中所对应的数据
        findDbFromBarCode(string);
        // 关闭软键盘
        KeyBoardUtils.closeKeybord(editTextString, ScanActivity.this);
    }

    @Override
    public void inventorySuccess() {
        mPresenter.updateDbFromPhy();
    }

    @Override
    public void inventoryFailure() {
        itemNameIncludeLayout.setVisibility(View.INVISIBLE);
        Toast.makeText(ScanActivity.this, "数据盘点失败，可能因为没有此盘点资产项", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDbFromPhy(List<School> list, int tag) {
        mSchoolOnlyOne.clear();
        mSchoolOnlyOne.addAll(list);
        // 绘制UI界面
        itemNameIncludeLayout.setVisibility(View.VISIBLE);
        drawUIFromData();
        if (tag == 1) {
            // 绘制UI界面
            itemNameIncludeLayout.setVisibility(View.VISIBLE);
            drawUIFromData();
            Toast.makeText(ScanActivity.this, "数据盘点成功", Toast.LENGTH_SHORT).show();
        } else if (tag == 3) {
            new QMUIDialog.MessageDialogBuilder(ScanActivity.this)
                    .setTitle("提示")
                    .setMessage("该资产已经盘点了，请勿重复盘点。")
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else if (tag == 2) {
            final QMUIDialog.EditTextDialogBuilder builder
                    = new QMUIDialog.EditTextDialogBuilder(ScanActivity.this);
            builder.setTitle("提示：账面数量为 " + StrUtil.isNullOrEmptyAndSub(mSchoolOnlyOne.get(0).getPhysicalCountQuantity()))
                    .setPlaceholder("请您输入资产实有数量")
                    .addAction("确定", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            CharSequence text = builder.getEditText().getText();
                            if (text != null && text.length() > 0) {
                                // EditText中输入的实有数量actualNumberOfCount
                                final int actualNumberOfCount = Integer.parseInt(StrUtil.isNullOrEmptyAndSub(text.toString()));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final School school = mSchoolOnlyOne.get(0);
                                        // 实有数量
                                        String actualNumberOf = school.getActualNumberOf();
                                        // 账面数量
                                        String physicalCountQuantity = school.getPhysicalCountQuantity();
                                        // String数据类型转换为int数据类型，取掉小数点|判空操作
                                        actualNumberOf = StrUtil.isNullOrEmptyAndSub(actualNumberOf);
                                        physicalCountQuantity = StrUtil.isNullOrEmptyAndSub(physicalCountQuantity);

                                        // 账面数量 > 1,且实有数量小于账面数量_累加到数据库中
                                        if ((Integer.parseInt(actualNumberOf) + actualNumberOfCount) < Integer.parseInt(physicalCountQuantity)) {
                                            school.setActualNumberOf(String.valueOf(Integer.parseInt(actualNumberOf) + actualNumberOfCount));
                                            school.setInventoryResults("盘盈");
                                            school.save();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    itemNameIncludeLayout.setVisibility(View.VISIBLE);
                                                    drawUIFromData();
                                                }
                                            });
                                            // 账面数量 > 1,且实有数量等于账面数量
                                        } else if ((Integer.parseInt(actualNumberOf) + actualNumberOfCount) == Integer.parseInt(physicalCountQuantity)) {
                                            school.setActualNumberOf(String.valueOf(Integer.parseInt(actualNumberOf) + actualNumberOfCount));
                                            school.setInventoryResults("无盈亏");
                                            school.save();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    itemNameIncludeLayout.setVisibility(View.VISIBLE);
                                                    drawUIFromData();
                                                }
                                            });
                                        } else if ((Integer.parseInt(actualNumberOf) + actualNumberOfCount) > Integer.parseInt(physicalCountQuantity)) {
                                            // 账面数量 > 1,且实有数量大于账面数量（弹出无盈亏Dialog）
                                            final String finalPhysicalCountQuantity = physicalCountQuantity;
                                            final String finalActualNumberOf = actualNumberOf;

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    new QMUIDialog.MessageDialogBuilder(ScanActivity.this)
                                                            .setTitle("提示")
                                                            .setMessage("是否确定无盈亏？")
                                                            .addAction("取消", new QMUIDialogAction.ActionListener() {
                                                                @Override
                                                                public void onClick(final QMUIDialog dialog, int index) {
                                                                    new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            // 输入的 + 数据库中原存在的实有数量_累加
                                                                            school.setActualNumberOf(String.valueOf(Integer.parseInt(finalActualNumberOf) + actualNumberOfCount));
                                                                            school.setInventoryResults("盘亏");
                                                                            school.save();
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    dialog.dismiss();
                                                                                    itemNameIncludeLayout.setVisibility(View.VISIBLE);
                                                                                    drawUIFromData();
                                                                                }
                                                                            });
                                                                        }
                                                                    }).start();
                                                                }
                                                            })
                                                            .addAction("确定", new QMUIDialogAction.ActionListener() {
                                                                @Override
                                                                public void onClick(final QMUIDialog dialog, int index) {
                                                                    new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            // 无盈亏——账面数量==实有数量
                                                                            school.setActualNumberOf(finalPhysicalCountQuantity);
                                                                            school.setInventoryResults("无盈亏");
                                                                            school.save();
                                                                            runOnUiThread(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    dialog.dismiss();
                                                                                    itemNameIncludeLayout.setVisibility(View.VISIBLE);
                                                                                    drawUIFromData();
                                                                                }
                                                                            });
                                                                        }
                                                                    }).start();
                                                                }
                                                            }).show();
                                                }
                                            });
                                        }
                                    }
                                }).start();

                                itemNameIncludeLayout.setVisibility(View.VISIBLE);
                                drawUIFromData();
                                Toast.makeText(ScanActivity.this, "数据盘点成功", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(ScanActivity.this, "请填入资产实有数量", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).create(R.style.QMUI_Dialog).show();
        } else {
            itemNameIncludeLayout.setVisibility(View.INVISIBLE);
            Toast.makeText(ScanActivity.this, "数据盘点失败，可能因为没有此盘点资产项", Toast.LENGTH_SHORT).show();
        }
    }

    public class ScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String barCode;
            String barCodeType;
            int length;
            if (bundle != null) {
                barCode = bundle.getString(ScannerService.BAR_CODE);
                barCodeType = bundle.getString(ScannerService.CODE_TYPE);
                length = bundle.getInt(ScannerService.LENGTH);
                switch (length) {
                    case 12:
                        mIntMusic = spPool.load(ScanActivity.this, R.raw.pass, 1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
                        spPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                                spPool.play(mIntMusic, 1, 1, 0, 0, 1);//开启音频,(对音频文件播放的设置 例如左右声道等
                            }
                        });
                        break;
                    default:
                        mIntMusic = spPool.load(ScanActivity.this, R.raw.erro, 1);//所要加载的music文件 ,(第2个参数即为资源文件，第3个为音乐的优先级), 其中raw是res文件夹里的 ,较低版本的android可能没有,需要手动创建,并在'R'文件中声明
                        spPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                                spPool.play(mIntMusic, 1, 1, 0, 0, 1);//开启音频,(对音频文件播放的设置 例如左右声道等
                            }
                        });
                        break;
                }
                EventBus
                        .getDefault()
                        .post(new BarCode(barCode, barCodeType, length));
            }
        }
    }

    // 注册
    @Override
    protected void onResume() {
        super.onResume();
        //ScannerService.MyService(this);
        // Register the receiver
        registerReceiver(mScanReceiver, mIntentFilter);
    }

    //卸载
    @Override
    protected void onPause() {
        // Unregister the receiver
        unregisterReceiver(mScanReceiver);
        mScanner.stopVideo();
        mScanner.stopScan();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, ScannerService.class);
        this.stopService(intent);
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BarCode barCode) {
        editTextString.setText(barCode.getBarCodeText());
        Log.e("云阳", "条码："
                + barCode.getBarCodeText() + "\n类型："
                + barCode.getBarCodeType() + "\n长度："
                + barCode.getBarCodeLength() + "\n\n");
        findDbFromBarCode(barCode.getBarCodeText());
    }

}
