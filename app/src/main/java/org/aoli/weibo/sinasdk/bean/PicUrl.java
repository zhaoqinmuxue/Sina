package org.aoli.weibo.sinasdk.bean;

import java.io.Serializable;

//多图时的多图链接
public class PicUrl implements Serializable {
    private String thumbnail_pic;

    private String bmiddle_pic;

    private String origin_pic;

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
        this.bmiddle_pic = thumbnail_pic.replaceFirst("thumbnail","bmiddle");
        this.origin_pic = thumbnail_pic.replaceFirst("thumbnail","large");
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public String getOrigin_pic() {
        return origin_pic;
    }
}
