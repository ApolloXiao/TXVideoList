package com.apollo.txvideolist.video;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class TXVideoManager implements ITXVodPlayListener {
    private static String TAG = "TXVideoManager";
    private static TXVideoManager instance;
    private TXVodPlayer txVodPlayer;
    private int playPosition = -1;
    private TXListVideoView txListVideoView;

    private TXVideoManager() {

    }

    public static TXVideoManager getInstance() {
        if (instance == null) {
            synchronized (TXVideoManager.class) {
                if (instance == null) {
                    instance = new TXVideoManager();
                }
            }
        }
        return instance;
    }

    public TXVideoManager initVideo(Context context) {
        if (txVodPlayer != null) {
            txVodPlayer.stopPlay(false);
            txVodPlayer = null;
        }
        txVodPlayer = new TXVodPlayer(context.getApplicationContext());
        TXVodPlayConfig mVodPlayConfig = new TXVodPlayConfig();
        mVodPlayConfig.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/txcache");
        mVodPlayConfig.setMaxCacheItems(5);
        txVodPlayer.setConfig(mVodPlayConfig);
        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        txVodPlayer.setVodListener(this);
        txVodPlayer.enableHardwareDecode(true);
        txVodPlayer.setAutoPlay(false);
        return this;
    }

    public TXVideoManager setPlayerView(TXCloudVideoView txCloudVideoView) {
        if (txListVideoView != null && txCloudVideoView!=null &&
                txListVideoView.getTxCloudVideoView() != txCloudVideoView) {
            txListVideoView.showCover();
        }
        if (txVodPlayer != null) {
            txVodPlayer.setPlayerView(txCloudVideoView);
        }
        return this;
    }

    public int getPlayPosition() {
        return playPosition;
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public TXVideoManager setTxListVideoView(TXListVideoView txListVideoView) {
        this.txListVideoView = txListVideoView;
        return this;
    }

    public void startPlay(String url) {
        if (txVodPlayer != null) {
            txVodPlayer.startPlay(url);
        }
    }

    public void seek(int seek) {
        if (txVodPlayer != null) {
            txVodPlayer.seek(seek);
        }
    }

    public void pause() {
        if (txVodPlayer != null) {
            txVodPlayer.pause();
        }
    }

    public void resume() {
        if (txVodPlayer != null) {
            txVodPlayer.resume();

        }
    }

    public void stop() {
        if (txVodPlayer != null) {
            txVodPlayer.setVodListener(null);
            txVodPlayer.stopPlay(false);
            txVodPlayer = null;
        }
        if (txListVideoView !=null){
            txListVideoView = null;
        }
    }

    public boolean isPlaying() {
        return txVodPlayer != null && txVodPlayer.isPlaying();
    }

    public int getCurrentPlaybackTime() {
        return txVodPlayer == null ? 0 : (int) txVodPlayer.getCurrentPlaybackTime();
    }

    @Override
    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle param) {
        if (event != TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            String playEventLog = "TXVodPlayer onPlayEvent event: " + event + ", " + param.getString("EVT_MSG");
            TXCLog.d(TAG, playEventLog);
        }
        if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            if (txListVideoView != null){
                txListVideoView.showCover();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            // 视频I帧到达，开始播放

        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            resume();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {

        } else if (event == TXLiveConstants.PLAY_EVT_VOD_LOADING_END) {
            resume();
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
}
