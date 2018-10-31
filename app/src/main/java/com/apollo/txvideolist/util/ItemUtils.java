package com.apollo.txvideolist.util;

import com.apollo.txvideolist.model.BaseItem;
import com.apollo.txvideolist.model.PicItem;
import com.apollo.txvideolist.model.TextItem;
import com.apollo.txvideolist.model.VideoItem;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    private static final String VIDEO_URL1 = "http://video.jiecao.fm/11/24/6/%E9%85%92%E9%A9%BE.mp4";
    private static final String VIDEO_URL2 = "http://video.jiecao.fm/11/23/6/%E7%8B%97.mp4";
    private static final String VIDEO_URL3 = "http://video.jiecao.fm/11/24/xin/-%2024%20-%20.mp4";
    private static final String VIDEO_URL4 = "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4";


    private static final String PIC_URL1 = "http://img10.3lian.com/sc6/show02/67/27/03.jpg";
    private static final String PIC_URL2 = "http://img10.3lian.com/sc6/show02/67/27/04.jpg";
    private static final String PIC_URL3 = "http://img10.3lian.com/sc6/show02/67/27/01.jpg";
    private static final String PIC_URL4 = "http://img10.3lian.com/sc6/show02/67/27/02.jpg";

    public static List<BaseItem> generateMockData() {
        List<BaseItem> list = new ArrayList<>();

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL1));
        list.add(new VideoItem(VIDEO_URL4, PIC_URL4));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL3, PIC_URL3));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL2, PIC_URL2));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL4));
        list.add(new VideoItem(VIDEO_URL1, PIC_URL1));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL1));
        list.add(new VideoItem(VIDEO_URL4, PIC_URL4));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL3, PIC_URL3));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL2, PIC_URL2));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL4));
        list.add(new VideoItem(VIDEO_URL1, PIC_URL1));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL1));
        list.add(new VideoItem(VIDEO_URL4, PIC_URL4));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL3, PIC_URL3));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL2, PIC_URL2));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL4));
        list.add(new VideoItem(VIDEO_URL1, PIC_URL1));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL1));
        list.add(new VideoItem(VIDEO_URL4, PIC_URL4));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL2));
        list.add(new VideoItem(VIDEO_URL3, PIC_URL3));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL3));
        list.add(new VideoItem(VIDEO_URL2, PIC_URL2));

//        list.add(new TextItem("TextItem"));
//        list.add(new PicItem(PIC_URL4));
        list.add(new VideoItem(VIDEO_URL1, PIC_URL1));

        return list;
    }
}
