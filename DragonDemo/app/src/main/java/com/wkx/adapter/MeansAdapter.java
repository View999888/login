package com.wkx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkx.bean.DataBean;
import com.wkx.dragondemo.R;
import com.wkx.utils.newAdd.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeansAdapter extends RecyclerView.Adapter<MeansAdapter.ViewHolder> {
    Context context;
    List<DataBean> mResultBeans = new ArrayList<>();

    public MeansAdapter(Context pContext) {
        context = pContext;
    }

    public void setResultBeans(List<DataBean> pResultBeans) {
        mResultBeans.addAll(pResultBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ess_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataBean dataBean = mResultBeans.get(position);
        Glide.with(context).load(dataBean.getPic()).into(holder.ivPhoto);
        holder.listOneTitle.setText(dataBean.getTitle());
        holder.tvBrowseNum.setText(dataBean.getView_num() + "人浏览");
        holder.tvCommentNum.setText(dataBean.getReply_num() + "人跟帖");
        holder.tvAuthorAndTime.setText(TimeUtil.formatLiveTime(Long.valueOf(dataBean.getCreate_time())));
    }

    @Override
    public int getItemCount() {
        return mResultBeans.size();
    }

    static
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_one_title)
        TextView listOneTitle;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.cv_photo)
        CardView cvPhoto;
        @BindView(R.id.tv_browse_num)
        TextView tvBrowseNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_author_and_time)
        TextView tvAuthorAndTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
