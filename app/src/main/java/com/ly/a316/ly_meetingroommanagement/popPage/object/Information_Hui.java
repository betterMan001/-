package com.ly.a316.ly_meetingroommanagement.popPage.object;

/**
 * 作者：余智强
 * 2019/3/5
 */
public class Information_Hui {
    String all;//全部参会人数
    String address;//会议地址
    String begin;//开始时间
    String sender;//发起会议人
    String sure;//确定参会人数
    String sign;//已签到的人
    String record;//会议记录人
    String title;//会议主题

    public Information_Hui(String all, String address, String begin, String sender, String sure, String sign, String record, String title) {
        this.all = all;
        this.address = address;
        this.begin = begin;
        this.sender = sender;
        this.sure = sure;
        this.sign = sign;
        this.record = record;
        this.title = title;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
