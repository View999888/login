package com.wkx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wkx.bean.LiveBean;
import com.wkx.design.RoundImage;
import com.wkx.dragondemo.R;
import com.wkx.liveutils.ZLImageLoader;
import com.wkx.utils.newAdd.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {
    Context context;
    List<LiveBean> mLiveBeans = new ArrayList<>();

    public LiveAdapter(Context pContext) {
        context = pContext;
    }

    public void setLiveBeans(List<LiveBean> pLiveBeans) {
        mLiveBeans.addAll(pLiveBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.live_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiveBean liveBean = mLiveBeans.get(position);

        //直播课程名称
        holder.tvLiveName.setText(liveBean.getActivityName());
        holder.name.setText(liveBean.getActivityName());

        ZLImageLoader.getInstance().displayImage(context, liveBean.getTeacher_photo(), holder.ivTeacherAvatar);
        //根据此条件是否直播
        int is_liveing = liveBean.getIs_liveing();

        if (is_liveing == 0) {
            holder.llLiving.setVisibility(View.VISIBLE);
            ZLImageLoader.getInstance().displayImage(context, ZLImageLoader.getUriFromResource(context, R.drawable.play), holder.ivLiving);
        } else {
            holder.llLiving.setVisibility(View.GONE);
            holder.tvLiveTime.setText(TimeUtil.formatLiveTime(Long.valueOf(liveBean.getStartTime())));
        }

    }

    @Override
    public int getItemCount() {
        return mLiveBeans.size();
    }

    static
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.iv_teacher_avatar)
        RoundImage ivTeacherAvatar;
        @BindView(R.id.tv_live_name)
        TextView tvLiveName;
        @BindView(R.id.iv_living)
        ImageView ivLiving;
        @BindView(R.id.ll_living)
        LinearLayout llLiving;
        @BindView(R.id.tv_live_time)
        TextView tvLiveTime;
        @BindView(R.id.ll_live_pre)
        LinearLayout llLivePre;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
