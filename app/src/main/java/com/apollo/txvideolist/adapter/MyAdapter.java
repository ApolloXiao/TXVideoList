package com.apollo.txvideolist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.apollo.txvideolist.hodler.BaseViewHolder;
import com.apollo.txvideolist.hodler.ViewHolderFactory;
import com.apollo.txvideolist.model.BaseItem;
import com.apollo.txvideolist.util.ItemUtils;
import com.apollo.txvideolist.visibility.items.ListItem;
import com.apollo.txvideolist.visibility.scroll.ItemsProvider;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder<? extends BaseItem>> implements ItemsProvider {
    private List<? extends BaseItem> mListItems;
    private RecyclerView mRecyclerView;


    public MyAdapter(RecyclerView recyclerView) {
        mListItems = ItemUtils.generateMockData();
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public BaseViewHolder<? extends BaseItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderFactory.buildViewHolder(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        BaseItem baseItem = mListItems.get(position);
//        holder.setMediaHandler(mMediaHandler);
        holder.onBind(position, baseItem);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mListItems.get(position).getViewType();
    }

    @Override
    public ListItem getListItem(int position) {
        RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof ListItem) {
            return (ListItem) holder;
        }
        return null;
    }

    @Override
    public int listItemSize() {
        return getItemCount();
    }

}
