package com.wkx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkx.bean.TestBean;
import com.wkx.dragondemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    Context context;
    List<TestBean.DatasBean> datasBeanList = new ArrayList<>();

    public TestAdapter(Context context) {
        this.context = context;
    }

    public void setDatasBeanList(List<TestBean.DatasBean> datasBeanList) {
        this.datasBeanList.addAll(datasBeanList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestBean.DatasBean datasBean = datasBeanList.get(position);
        Glide.with(context).load(datasBean.getThumbnail()).into(holder.testImage);
        holder.titleTest.setText(datasBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return datasBeanList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.test_image)
        ImageView testImage;
        @BindView(R.id.title_test)
        TextView titleTest;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
