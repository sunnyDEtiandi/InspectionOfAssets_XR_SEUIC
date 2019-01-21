package com.xiangrong.yunyang.inspectionofassets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelperActivity extends AppCompatActivity {

    @BindView(R.id.image_back_title_layout)
    AppCompatImageView imageBackTitleLayout;
    @BindView(R.id.title_back_title_layout)
    AppCompatTextView titleBackTitleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        ButterKnife.bind(this);
        titleBackTitleLayout.setText(getString(R.string.text_helper));
    }

    @OnClick(R.id.image_back_title_layout)
    public void onViewClicked() {
        finish();
    }
}
