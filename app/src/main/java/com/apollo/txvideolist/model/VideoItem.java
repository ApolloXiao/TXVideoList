package com.apollo.txvideolist.model;

public class VideoItem extends BaseItem {
    private String mVideoUrl;
    private String mCoverUrl;
    private int seek;
    private int itemPosition=-1;

    public VideoItem(String videoUrl, String coverUrl) {
        super(BaseItem.VIEW_TYPE_VIDEO);
        mVideoUrl = videoUrl;
        mCoverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public int getSeek() {
        return seek;
    }

    public void setSeek(int seek) {
        this.seek = seek;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public int getItemPosition() {
        return itemPosition;
    }
}
