package com.xfhy.okhttptest.test;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xfhy on 2019/7/18 23:18
 * Description :
 */
public class Dictionary implements Serializable {


    /**
     * sid : 3457
     * tts : http://news.iciba.com/admin/tts/2019-07-18-day.mp3
     * content : Towering genius disdains a beaten path. It seeks regions hitherto unexplored.
     * note : 卓越的天才不屑走旁人走过的路。他寻找迄今未开拓的地区。
     * love : 1710
     * translation : 小编的话：天才之所以称为天才是因为他们勇于探索，独辟蹊径。
     * picture : http://cdn.iciba.com/news/word/20190718.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20190718b.jpg
     * caption : 词霸每日一句
     * dateline : 2019-07-18
     * s_pv : 0
     * sp_pv : 0
     * tags : [{"id":null,"name":null}]
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2019-07-18.jpg
     */

    @JSONField(name = "sid")
    public String sid;
    @JSONField(name = "tts")
    public String tts;
    @JSONField(name = "content")
    public String content;
    @JSONField(name = "note")
    public String note;
    @JSONField(name = "love")
    public String love;
    @JSONField(name = "translation")
    public String translation;
    @JSONField(name = "picture")
    public String picture;
    @JSONField(name = "picture2")
    public String picture2;
    @JSONField(name = "caption")
    public String caption;
    @JSONField(name = "dateline")
    public String dateline;
    @JSONField(name = "s_pv")
    public String sPv;
    @JSONField(name = "sp_pv")
    public String spPv;
    @JSONField(name = "fenxiang_img")
    public String fenxiangImg;
    @JSONField(name = "tags")
    public List<TagsBean> tags;

    public static class TagsBean implements Serializable {
        @JSONField(name = "id")
        public Object id;
        @JSONField(name = "name")
        public Object name;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "sid='" + sid + '\'' +
                ", tts='" + tts + '\'' +
                ", content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", love='" + love + '\'' +
                ", translation='" + translation + '\'' +
                ", picture='" + picture + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", caption='" + caption + '\'' +
                ", dateline='" + dateline + '\'' +
                ", sPv='" + sPv + '\'' +
                ", spPv='" + spPv + '\'' +
                ", fenxiangImg='" + fenxiangImg + '\'' +
                ", tags=" + tags +
                '}';
    }
}
