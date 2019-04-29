package com.xfhy.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2019/4/28 21:42
 * Description :
 */
public class StringDataAdapter extends RecyclerView.Adapter<StringDataAdapter.StringDataViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mStringList;

    public StringDataAdapter(Context context, List<String> stringList) {
        mLayoutInflater = LayoutInflater.from(context);
        mStringList = stringList;
    }

    @NonNull
    @Override
    public StringDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_string_data, parent, false);
        return new StringDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringDataViewHolder holder, int position) {
        holder.tvData.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    static class StringDataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData;

        public StringDataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data);
        }
    }

    public List<String> getStringList() {
        if (mStringList == null) {
            mStringList = new ArrayList<>();
        }
        return mStringList;
    }
}
