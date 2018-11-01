package com.apollo.txvideolist.video;

import com.tencent.rtmp.TXVodPlayer;

public class TXVideoManager {

    private static TXVideoManager instance;
    private TXVodPlayer txVodPlayer;
    private int playPosition = -1;
    private VideoListener videoListener;

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }

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

    public void setTxVodPlayer(TXVodPlayer txVodPlayer) {
        this.txVodPlayer = txVodPlayer;
    }

    public int getPlayPosition() {
        return playPosition;
    }

    public void setPlayPosition(int playPosition) {
        this.playPosition = playPosition;
    }

    public void pause() {
        if (txVodPlayer != null) {
            txVodPlayer.pause();
            if (videoListener!=null){
                videoListener.showCover();
            }
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
    }

    public boolean isPlaying() {
        return txVodPlayer != null && txVodPlayer.isPlaying();
    }

    public interface VideoListener {
        void showCover();
    }
}
