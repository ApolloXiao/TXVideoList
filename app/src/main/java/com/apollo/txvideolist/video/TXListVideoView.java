package com.apollo.txvideolist.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.apollo.txvideolist.R;
import com.apollo.txvideolist.model.VideoItem;
import com.bumptech.glide.Glide;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class TXListVideoView extends FrameLayout {
    private TXCloudVideoView txCloudVideoView;
    private ImageView coverImageView;
    private ImageView playButton;
    private ProgressBar progressBar;

    private VideoItem videoItem;

    public TXListVideoView(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public TXListVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public TXListVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TXListVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initVideoView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_list_video_view, this);
        setBackgroundColor(Color.parseColor("#d8d8d8"));
        txCloudVideoView = findViewById(R.id.txCloudVideoView);
        coverImageView = findViewById(R.id.cover);
        playButton = findViewById(R.id.play);
        progressBar = findViewById(R.id.progressbar);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TXVideoManager.getInstance().setPlayPosition(videoItem.getItemPosition());
                startPlay();
            }
        });

    }

    public TXListVideoView setVideoItem(VideoItem videoItem) {
        this.videoItem = videoItem;
        return this;
    }

    public TXCloudVideoView getTxCloudVideoView() {
        return txCloudVideoView;
    }

    public void setCover() {
        Glide.with(getContext())
                .load(videoItem.getCoverUrl())
                .centerCrop()
                .into(coverImageView);
    }

    public void startPlay() {
        if (videoItem != null && !TextUtils.isEmpty(videoItem.getVideoUrl())) {
            TXVideoManager.getInstance()
                    .initVideo(getContext())
                    .setPlayerView(txCloudVideoView)
                    .setTxListVideoView(this)
                    .startPlay(videoItem.getVideoUrl());
            progressBar.setVisibility(GONE);
            coverImageView.setVisibility(GONE);
            playButton.setVisibility(GONE);
        }
    }

    public void pause() {
        TXVideoManager.getInstance().pause();
        videoItem.setSeek(TXVideoManager.getInstance().getCurrentPlaybackTime());
        coverImageView.setVisibility(VISIBLE);
        playButton.setVisibility(VISIBLE);
    }

    public void showCover() {
        coverImageView.setVisibility(VISIBLE);
        playButton.setVisibility(VISIBLE);
    }
}
