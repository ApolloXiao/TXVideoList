package com.apollo.txvideolist.hodler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apollo.txvideolist.adapter.MyAdapter;
import com.apollo.txvideolist.model.BaseItem;

public abstract class BaseViewHolder<T extends BaseItem> extends RecyclerView.ViewHolder  {
    public BaseViewHolder(View itemView) {
        super(itemView);

    }

    public abstract void onBind(int position, T iItem);
}
