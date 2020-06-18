package com.wkx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wkx.bean.LiveBean;
import com.wkx.design.RoundImage;
import com.wkx.dragondemo.R;
import com.wkx.utils.newAdd.GlideUtil;
import com.wkx.utils.newAdd.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LivesAdapter extends RecyclerView.Adapter<LivesAdapter.ViewHolder> {

    private List<LiveBean> liveData;
    private Context mContext;

    public LivesAdapter(List<LiveBean> pLiveData, Context pContext) {
        liveData = pLiveData;
        mContext = pContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.live_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiveBean liveBean = liveData.get(position);
        GlideUtil.loadImage(holder.roundPhoto,liveBean.getTeacher_photo());
        holder.title.setText(liveBean.getActivityName());
        if (!TextUtils.isEmpty(liveBean.getStartTime()))holder.time.setText(TimeUtil.formatLiveTime(Long.parseLong(liveBean.getStartTime())));
    }

    @Override
    public int getItemCount() {
        return liveData != null ? liveData.size() : 0;
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
