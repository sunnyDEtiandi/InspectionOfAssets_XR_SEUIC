package com.xiangrong.yunyang.inspectionofassets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiangrong.yunyang.inspectionofassets.R;

import java.util.List;

/**
 * 作者    yunyang
 * 时间    2019/1/21 10:20
 * 文件    InspectionOfAssets
 * 描述   主页面的RecyclerView的适配器
 */
public class IndexRecyAdapter extends RecyclerView.Adapter<IndexRecyAdapter.IndexViewHolder> {

    private List<String> mTextString;
    private List<Integer> mImageDrawable;
    private Context mContext;

    private LineOnClickListener mLineOnClickListener;

    public void setLineOnClickListener(LineOnClickListener lineOnClickListener) {
        mLineOnClickListener = lineOnClickListener;
    }

    public IndexRecyAdapter(Context context, List<String> textString, List<Integer> imageDrawable) {
        mContext = context;
        mTextString = textString;
        mImageDrawable = imageDrawable;
    }

    @NonNull
    @Override
    public IndexViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recy_index_view, viewGroup, false);
        final IndexViewHolder holder = new IndexViewHolder(view);
        holder.mLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                if (mLineOnClickListener != null) {
                    mLineOnClickListener.onLineItemOnClick(position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IndexViewHolder indexViewHolder, int i) {
        indexViewHolder.mImage.setImageResource(mImageDrawable.get(i));
        indexViewHolder.mText.setText(mTextString.get(i));
    }

    @Override
    public int getItemCount() {
        return mTextString.size();
    }

    static class IndexViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat mLine;
        AppCompatImageView mImage;
        AppCompatTextView mText;

        public IndexViewHolder(@NonNull View itemView) {
            super(itemView);
            mLine = (LinearLayoutCompat) itemView.findViewById(R.id.item_recy_index_line);
            mImage = (AppCompatImageView) itemView.findViewById(R.id.item_recy_index_image);
            mText = (AppCompatTextView) itemView.findViewById(R.id.item_recy_index_text);
        }
    }

    public interface LineOnClickListener {
        void onLineItemOnClick(int position);
    }
}

