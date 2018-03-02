package com.jeez.guanpj.jreadhub.moduls.test.bean;

import java.util.List;

public class PostModel {

    /**
     * vendor : six.jie.c
     * count : 2000
     * page : 100
     * maxid : 1467006722
     * maxtime : 1467006722
     */

    private InfoBean info;
    /**
     * id : 19057777
     * type : 10
     * text : 好魔性的玩具！第一次看着有点儿恶心，越看越好玩！
     * user_id : 17658867
     * name : 520粉紅頑皮豹
     * screen_name : 520粉紅頑皮豹
     * profile_image : http://dimg.spriteapp.cn/profile/20160524181335.jpg
     * created_at : 2016-06-27 23:04:51
     * create_time : 2016-06-25 11:23:46
     * passtime : 2016-06-27 23:04:51
     * love : 569
     * hate : 75
     * comment : 28
     * repost : 73
     * bookmark : 81
     * bimageuri :
     * voiceuri :
     * voicetime : 0
     * voicelength : 0
     * status : 4
     * theme_id : 0
     * theme_name :
     * theme_type : 0
     * videouri :
     * videotime : 0
     * original_pid : 0
     * cache_version : 2
     * cai : 75
     * top_cmt : []
     * weixin_url : http://a.f.zk111.net/share/19057777.html?wx.qq.com&appname=
     * themes : []
     * image0 : http://ww4.sinaimg.cn/mw240/c1e8ffd5jw1f59r2q9xhpg207803we81.gif
     * image2 : http://ww4.sinaimg.cn/bmiddle/c1e8ffd5jw1f59r2q9xhpg207803we81.gif
     * image1 : http://ww4.sinaimg.cn/large/c1e8ffd5jw1f59r2q9xhpg207803we81.gif
     * cdn_img : http://dimg.spriteapp.cn/ugc/2016/06/25/576df942cc24c.gif
     * is_gif : 1
     * gifFistFrame : http://dimg.spriteapp.cn/ugc/2016/06/25/576df942cc24c_a_1.jpg
     * width : 260
     * height : 140
     * tag :
     * t : 1467039891
     * ding : 569
     * favourite : 81
     */

    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private String vendor;
        private int count;
        private int page;
        private String maxid;
        private String maxtime;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getMaxid() {
            return maxid;
        }

        public void setMaxid(String maxid) {
            this.maxid = maxid;
        }

        public String getMaxtime() {
            return maxtime;
        }

        public void setMaxtime(String maxtime) {
            this.maxtime = maxtime;
        }
    }

    public static class ListBean {
        private String id;
        private String type;
        private String text;
        private String user_id;
        private String name;
        private String screen_name;
        private String profile_image;
        private String created_at;
        private String create_time;
        private String passtime;
        private String love;
        private String hate;
        private String comment;
        private String repost;
        private String bookmark;
        private String bimageuri;
        private String voiceuri;
        private String voicetime;
        private String voicelength;
        private String status;
        private String theme_id;
        private String theme_name;
        private String theme_type;
        private String videouri;
        private String videotime;
        private String original_pid;
        private int cache_version;
        private String cai;
        private String weixin_url;
        private String image0;
        private String image2;
        private String image1;
        private String cdn_img;
        private String is_gif;
        private String gifFistFrame;
        private String width;
        private String height;
        private String tag;
        private int t;
        private String ding;
        private String favourite;
        private List<?> top_cmt;
        private List<?> themes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScreen_name() {
            return screen_name;
        }

        public void setScreen_name(String screen_name) {
            this.screen_name = screen_name;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPasstime() {
            return passtime;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getHate() {
            return hate;
        }

        public void setHate(String hate) {
            this.hate = hate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRepost() {
            return repost;
        }

        public void setRepost(String repost) {
            this.repost = repost;
        }

        public String getBookmark() {
            return bookmark;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public String getBimageuri() {
            return bimageuri;
        }

        public void setBimageuri(String bimageuri) {
            this.bimageuri = bimageuri;
        }

        public String getVoiceuri() {
            return voiceuri;
        }

        public void setVoiceuri(String voiceuri) {
            this.voiceuri = voiceuri;
        }

        public String getVoicetime() {
            return voicetime;
        }

        public void setVoicetime(String voicetime) {
            this.voicetime = voicetime;
        }

        public String getVoicelength() {
            return voicelength;
        }

        public void setVoicelength(String voicelength) {
            this.voicelength = voicelength;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(String theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public String getTheme_type() {
            return theme_type;
        }

        public void setTheme_type(String theme_type) {
            this.theme_type = theme_type;
        }

        public String getVideouri() {
            return videouri;
        }

        public void setVideouri(String videouri) {
            this.videouri = videouri;
        }

        public String getVideotime() {
            return videotime;
        }

        public void setVideotime(String videotime) {
            this.videotime = videotime;
        }

        public String getOriginal_pid() {
            return original_pid;
        }

        public void setOriginal_pid(String original_pid) {
            this.original_pid = original_pid;
        }

        public int getCache_version() {
            return cache_version;
        }

        public void setCache_version(int cache_version) {
            this.cache_version = cache_version;
        }

        public String getCai() {
            return cai;
        }

        public void setCai(String cai) {
            this.cai = cai;
        }

        public String getWeixin_url() {
            return weixin_url;
        }

        public void setWeixin_url(String weixin_url) {
            this.weixin_url = weixin_url;
        }

        public String getImage0() {
            return image0;
        }

        public void setImage0(String image0) {
            this.image0 = image0;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getCdn_img() {
            return cdn_img;
        }

        public void setCdn_img(String cdn_img) {
            this.cdn_img = cdn_img;
        }

        public String getIs_gif() {
            return is_gif;
        }

        public void setIs_gif(String is_gif) {
            this.is_gif = is_gif;
        }

        public String getGifFistFrame() {
            return gifFistFrame;
        }

        public void setGifFistFrame(String gifFistFrame) {
            this.gifFistFrame = gifFistFrame;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public String getDing() {
            return ding;
        }

        public void setDing(String ding) {
            this.ding = ding;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public List<?> getTop_cmt() {
            return top_cmt;
        }

        public void setTop_cmt(List<?> top_cmt) {
            this.top_cmt = top_cmt;
        }

        public List<?> getThemes() {
            return themes;
        }

        public void setThemes(List<?> themes) {
            this.themes = themes;
        }
    }
}
