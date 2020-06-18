package com.wkx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wkx.bean.VipPlayerBean;
import com.wkx.design.RoundImage;
import com.wkx.dragondemo.R;
import com.wkx.utils.newAdd.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipLiveAdapter extends RecyclerView.Adapter<VipLiveAdapter.ViewHolder> {
    private Context mContext;
    List<VipPlayerBean.LiveBeanX.LiveBean> pLiveData=new ArrayList<>();

    public VipLiveAdapter(Context pContext, List<VipPlayerBean.LiveBeanX.LiveBean> pPLiveData) {
        mContext = pContext;
        pLiveData = pPLiveData;
    }

    @NonNull
    @Override
    public VipLiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VipLiveAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.live_vip_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VipLiveAdapter.ViewHolder holder, int position) {
        VipPlayerBean.LiveBeanX.LiveBean liveBean = pLiveData.get(position);
        GlideUtil.loadImage(holder.roundPhoto,liveBean.getCoverImgUrl());
        holder.title.setText(liveBean.getActivityName());
        if (!TextUtils.isEmpty(liveBean.getStart_date()))holder.time.setText(liveBean.getStart_date());
    }

    @Override
    public int getItemCount() {
        return pLiveData != null ? pLiveData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.round_photo)
        RoundImage roundPhoto;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
