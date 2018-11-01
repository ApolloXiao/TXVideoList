package com.apollo.txvideolist.video;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;

public class TXListVideoView extends FrameLayout implements ITXVodPlayListener,TXVideoManager.VideoListener {
    private static String TAG = "TXListVideoView";
    private TXCloudVideoView txCloudVideoView;
    private TXVodPlayer txVodPlayer;
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
        txCloudVideoView = (TXCloudVideoView) findViewById(R.id.txCloudVideoView);
        coverImageView = (ImageView) findViewById(R.id.cover);
        playButton = (ImageView) findViewById(R.id.play);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        playButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TXVideoManager.getInstance().setPlayPosition(videoItem.getItemPosition());
                startPlay();
            }
        });

        txVodPlayer = new TXVodPlayer(getContext());
        TXVodPlayConfig mVodPlayConfig = new TXVodPlayConfig();
        mVodPlayConfig.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
        mVodPlayConfig.setMaxCacheItems(5);
        txVodPlayer.setConfig(mVodPlayConfig);
        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        txVodPlayer.setVodListener(this);
        txVodPlayer.enableHardwareDecode(true);
        txVodPlayer.setAutoPlay(false);
        txVodPlayer.setPlayerView(txCloudVideoView);

        initPhoneListener();
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
        if (txVodPlayer != null && videoItem != null && !TextUtils.isEmpty(videoItem.getVideoUrl())) {
            if (TXVideoManager.getInstance().isPlaying()){
                TXVideoManager.getInstance().pause();
            }
            if (videoItem.getSeek()>0){
                txVodPlayer.seek(videoItem.getSeek());
            }else{
                txVodPlayer.startPlay(videoItem.getVideoUrl());
            }
            TXVideoManager.getInstance().setTxVodPlayer(txVodPlayer);
            TXVideoManager.getInstance().setVideoListener(this);
            coverImageView.setVisibility(GONE);
            playButton.setVisibility(GONE);
        }
    }

    public void pause() {
        if (txVodPlayer != null) {
            txVodPlayer.pause();
            videoItem.setSeek((int) txVodPlayer.getCurrentPlaybackTime());
            coverImageView.setVisibility(VISIBLE);
            playButton.setVisibility(VISIBLE);
        }

    }

    public void resume(){
        if (txVodPlayer != null) {
            txVodPlayer.resume();
            coverImageView.setVisibility(GONE);
            playButton.setVisibility(GONE);
        }
    }

    @Override
    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
        if (event != TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString("EVT_MSG");
            TXCLog.d(TAG, playEventLog);
        }
        if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {

        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            // 视频I帧到达，开始播放

        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (this.txVodPlayer == txVodPlayer){
                resume();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            progressBar.setVisibility(VISIBLE);

        } else if (event == TXLiveConstants.PLAY_EVT_VOD_LOADING_END) {
            progressBar.setVisibility(GONE);
            if (this.txVodPlayer == txVodPlayer){
                resume();
            }
        } else if (event < 0) {
            String desc = null;
            switch (event) {
                case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                    desc = "获取加速拉流地址失败";
                    break;
                case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
                    desc = "文件不存在";
                    break;
                case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
                    desc = "h265解码失败";
                    break;
                case TXLiveConstants.PLAY_ERR_HLS_KEY:
                    desc = "HLS解密key获取失败";
                    break;
                case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
                    desc = "获取点播文件信息失败";
                    break;
            }
            if (desc != null) {
                TXLog.i(TAG, desc);
            }
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }

    @Override
    public void showCover() {
        coverImageView.setVisibility(VISIBLE);
        playButton.setVisibility(VISIBLE);
    }

    private void initPhoneListener() {
        if (mPhoneListener == null)
            mPhoneListener = new TXPhoneStateListener(txVodPlayer);
        TelephonyManager tm = (TelephonyManager) getContext().getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * ==========================================来电监听==========================================
     */
    private PhoneStateListener mPhoneListener = null;

    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXVodPlayer> mPlayer;

        public TXPhoneStateListener(TXVodPlayer player) {
            mPlayer = new WeakReference<TXVodPlayer>(player);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXVodPlayer player = mPlayer.get();
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (player != null) player.setMute(true);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (player != null) player.setMute(true);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (player != null) player.setMute(false);
                    break;
            }
        }
    }
}
