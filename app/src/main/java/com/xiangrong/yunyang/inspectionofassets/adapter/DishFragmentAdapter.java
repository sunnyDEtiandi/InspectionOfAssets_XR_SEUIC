package com.xiangrong.yunyang.inspectionofassets.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silencedut.expandablelayout.ExpandableLayout;
import com.xiangrong.yunyang.inspectionofassets.R;
import com.xiangrong.yunyang.inspectionofassets.entity.School;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    yunyang
 * 时间    2019/1/21 17:08
 * 文件    InspectionOfAssets
 * 描述   DishFragment的适配器
 */
public class DishFragmentAdapter extends RecyclerView.Adapter<DishFragmentAdapter.DishHolder> {

    private LayoutInflater mInflater;
    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();
    private List<School> mSchoolList;

    public DishFragmentAdapter(Context context, List<School> dbToSchoolList) {
        this.mInflater = LayoutInflater.from(context);
        mSchoolList = dbToSchoolList;
    }

    @NonNull
    @Override
    public DishHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = mInflater.inflate(R.layout.item_recy_fragment_dish, viewGroup, false);
        return new DishHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull DishHolder holder, int position) {
        holder.textStringNumber.setText(mSchoolList.get(position).getAssetNumber());
        holder.textStringName.setText(mSchoolList.get(position).getAssetName());
        holder.textStringDate.setText(mSchoolList.get(position).getDateOfFinancialEntry());
        holder.textStringMoney.setText(mSchoolList.get(position).getNetBookValue());
        holder.textStringZero.setText(mSchoolList.get(position).getAssetNumber());
        holder.textStringOne.setText(mSchoolList.get(position).getAssetName());
        holder.textStringTwo.setText(mSchoolList.get(position).getAssetClassification());
        holder.textStringFour.setText(mSchoolList.get(position).getActualNumberOf());
        holder.textStringTen.setText(mSchoolList.get(position).getPhysicalCountQuantity());
        holder.textStringZeroZero.setText(mSchoolList.get(position).getBookValue());
        holder.textStringSevenSeven.setText(mSchoolList.get(position).getDateOfFinancialEntry());
        holder.textStringNineNine.setText(mSchoolList.get(position).getStoragePlace());
        holder.textStringTenTen.setText(mSchoolList.get(position).getUserDepartment());
        holder.textStringZeroZeroZero.setText(mSchoolList.get(position).getUser());
        holder.textStringTwoTwoTwo.setText(mSchoolList.get(position).getRemark());
        holder.updateItem(position);
    }

    @Override
    public int getItemCount() {
        return mSchoolList.size();
    }

    class DishHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_string_number)
        AppCompatTextView textStringNumber;
        @BindView(R.id.text_string_name)
        AppCompatTextView textStringName;
        @BindView(R.id.text_string_date)
        AppCompatTextView textStringDate;
        @BindView(R.id.text_string_money)
        AppCompatTextView textStringMoney;
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
        @BindView(R.id.expandable_layout)
        ExpandableLayout expandableLayout;

        private DishHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void updateItem(final int position) {
            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
            expandableLayout.setExpand(mExpandedPositionSet.contains(position));

        }

    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        } else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }

    public void setDataNotify(List<School> schools) {
        mSchoolList.clear();
        mSchoolList.addAll(schools);
        notifyDataSetChanged();
    }

}
