package com.ly.a316.ly_meetingroommanagement.popPage.object;

/**
 * 作者：余智强
 * 2019/3/3
 */
public class HuiyiClass {
    String initiator_id;//发起人id
    String conference_name;//会议名字
    String conference_time;//会议开始时间
    String conference_where;//会议地点
    String conference_img;
    String conference_id;//会议室id

    public HuiyiClass(String initiator_id,String conference_id, String conference_name, String conference_time, String conference_where,String conference_img) {
        this.initiator_id = initiator_id;
        this.conference_id = conference_id;
        this.conference_name = conference_name;
        this.conference_time = conference_time;
        this.conference_where = conference_where;
        this.conference_img = conference_img;
    }

    public String getInitiator_id() {
        return initiator_id;
    }

    public void setInitiator_id(String initiator_id) {
        this.initiator_id = initiator_id;
    }

    public String getConference_name() {
        return conference_name;
    }

    public void setConference_name(String conference_name) {
        this.conference_name = conference_name;
    }

    public String getConference_time() {
        return conference_time;
    }

    public void setConference_time(String conference_time) {
        this.conference_time = conference_time;
    }

    public String getConference_where() {
        return conference_where;
    }

    public void setConference_where(String conference_where) {
        this.conference_where = conference_where;
    }

    public String getConference_img() {
        return conference_img;
    }

    public void setConference_img(String conference_img) {
        this.conference_img = conference_img;
    }

    public String getConference_id() {
        return conference_id;
    }

    public void setConference_id(String conference_id) {
        this.conference_id = conference_id;
    }
}
