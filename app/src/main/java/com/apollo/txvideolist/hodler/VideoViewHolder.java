package com.apollo.txvideolist.hodler;

import android.util.Log;
import android.view.View;

import com.apollo.txvideolist.R;
import com.apollo.txvideolist.model.VideoItem;
import com.apollo.txvideolist.video.TXListVideoView;
import com.apollo.txvideolist.video.TXVideoManager;
import com.apollo.txvideolist.visibility.items.ListItem;

public class VideoViewHolder extends BaseViewHolder<VideoItem> implements ListItem {
    private TXListVideoView txListVideoView;

    VideoViewHolder(View itemView) {
        super(itemView);
        txListVideoView = itemView.findViewById(R.id.videoView);
    }

    @Override
    public void onBind(int position, VideoItem videoItem) {
        videoItem.setItemPosition(position);
        txListVideoView.setVideoItem(videoItem).setCover();
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.d("11111111111", newActiveViewPosition + "");
        if (TXVideoManager.getInstance().getPlayPosition() != newActiveViewPosition){
            txListVideoView.startPlay();
            TXVideoManager.getInstance().setPlayPosition(newActiveViewPosition);
        }

    }

    @Override
    public void deactivate(View currentView, int position) {
        if (TXVideoManager.getInstance().getPlayPosition() == position) {
            txListVideoView.pause();
        }

    }
}