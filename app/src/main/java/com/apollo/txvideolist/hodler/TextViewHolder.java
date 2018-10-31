package com.apollo.txvideolist.hodler;

import android.view.View;
import android.widget.TextView;

import com.apollo.txvideolist.R;
import com.apollo.txvideolist.model.TextItem;

public class TextViewHolder extends BaseViewHolder<TextItem> {
    TextView mTextView;

    public TextViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.text_view);
    }

    @Override
    public void onBind(int position, TextItem textItem) {
        mTextView.setText(String.format("%s - %s", textItem.getText(), position));
    }
}
