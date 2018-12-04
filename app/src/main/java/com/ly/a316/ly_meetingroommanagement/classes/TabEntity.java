package com.ly.a316.ly_meetingroommanagement.classes;
/**
 *  描述：底部导航具体信息
 *  作者： 余智强
 *  创建时间：12/4 14：12
*/
public class TabEntity {
    private String text;//导航栏名字
    private int normalIconId;//没选中的样式
    private int selectIconId;//选中的样式
    private boolean isShowPoint;//是否显示小红点
    private int newsCount;//小红点显示的数目大小

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public boolean isShowPoint() {
        return isShowPoint;
    }

    public void setShowPoint(boolean showPoint) {
        isShowPoint = showPoint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNormalIconId() {
        return normalIconId;
    }

    public void setNormalIconId(int normalIconId) {
        this.normalIconId = normalIconId;
    }

    public int getSelectIconId() {
        return selectIconId;
    }

    public void setSelectIconId(int selectIconId) {
        this.selectIconId = selectIconId;
    }
}
