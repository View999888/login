package com.wkx.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wkx.bean.IndexCommondEntity;
import com.wkx.bean.LiveBean;
import com.wkx.bean.VipListBean;
import com.wkx.bean.VipPlayerBean;
import com.wkx.design.BannerLayout;
import com.wkx.dragondemo.R;
import com.wkx.utils.ext.StringUtils;
import com.wkx.utils.newAdd.GlideUtil;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class VipAdapter extends RecyclerView.Adapter<VipAdapter.ViewHolder> {

    private Activity mContext;
    private List<String> bannerData;//banner
    private List<VipPlayerBean.LiveBeanX.LiveBean> mLiveBeans ;//zhibo
    private List<VipListBean.ListBean> data ;//liebiao


    public VipAdapter(Activity pContext, List<String> pBannerData, List<VipPlayerBean.LiveBeanX.LiveBean> pLiveBeans, List<VipListBean.ListBean> pData) {
        mContext = pContext;
        bannerData = pBannerData;
        mLiveBeans = pLiveBeans;
        data = pData;
    }

    private final int BANNER = 1, LIVE = 2, GRID = 3;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId = R.layout.bottom_list_item_member;
        if (viewType == BANNER) {
            layoutId = R.layout.item_homepage_ad;
        } else if (viewType == LIVE) {
            layoutId = R.layout.live_recycler_item;
        } else {//recleview，
            layoutId = R.layout.item_bottom_member_list;
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false), viewType);
    }


    @Override
    public int getItemViewType(int position) {
        int type = GRID;
        if (position == 0) type = BANNER;
        else if (mLiveBeans != null && mLiveBeans.size() != 0 && position == 1) type = LIVE;
        else {
            type = GRID;
        }
        return type;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == BANNER) {
            holder.banner.attachActivity(mContext);
            if (bannerData.size() != 0) holder.banner.setViewUrls(bannerData);
        } else if (itemViewType == LIVE) {
            LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(manager);
            VipLiveAdapter vipLiveAdapter = new VipLiveAdapter(mContext, mLiveBeans);
            holder.recyclerView.setAdapter(vipLiveAdapter);
        } else {
            if (data.size() == 0) return;
            holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            VipBottomAdapter vipBottomAdapter = new VipBottomAdapter(mContext, data);
            holder.mRecyclerView.setAdapter(vipBottomAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return mLiveBeans != null && mLiveBeans.size() != 0 ? mLiveBeans.size() + 2 : mLiveBeans.size() + 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        BannerLayout banner;

        RecyclerView recyclerView;

        RecyclerView mRecyclerView;

        public ViewHolder(@NonNull View itemView, int type) {
            super(itemView);
            if (type == BANNER) {
                banner = itemView.findViewById(R.id.banner);
            } else if (type == LIVE) {
                recyclerView = itemView.findViewById(R.id.recyclerView);
            } else {
                mRecyclerView = itemView.findViewById(R.id.member_bottom_recycler);
            }
        }
    }

}
