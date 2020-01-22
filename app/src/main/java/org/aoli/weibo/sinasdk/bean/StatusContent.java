package org.aoli.weibo.sinasdk.bean;

//一条微博
public class StatusContent {
    private String created_at;//发布时间
    private long id;//微博id
    private String text;//微博内容
    private String source;//发送设备
    private boolean favorited;//是否已收藏
    private PicUrl[] pic_urls;//多图链接
    private Geo geo;//位置信息
    private WeiBoUser user;//作者信息
    private StatusContent retweeted_status;//转发的博文，内容为status，如果不是转发，则没有此字段
    private int reposts_count;//转发数
    private int comments_count;//评论数
    private int attitudes_count;//点赞数

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public PicUrl[] getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(PicUrl[] pic_urls) {
        this.pic_urls = pic_urls;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public StatusContent getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(StatusContent retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public WeiBoUser getUser() {
        return user;
    }

    public void setUser(WeiBoUser user) {
        this.user = user;
    }
}
