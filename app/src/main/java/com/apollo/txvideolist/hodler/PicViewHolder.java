package com.apollo.txvideolist.hodler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apollo.txvideolist.R;
import com.apollo.txvideolist.model.PicItem;
import com.bumptech.glide.Glide;

public class PicViewHolder extends BaseViewHolder<PicItem> {
    ImageView mImageView;

    TextView mTextView;

    public PicViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.pic_image_view);
        mTextView = itemView.findViewById(R.id.pic_text_view);
    }

    @Override
    public void onBind(int position, PicItem picItem) {
        Glide.with(itemView.getContext())
                .load(picItem.getCoverUrl())
                .into(mImageView);

        mTextView.setText(String.format("PicItem %s", position));
    }
}
