package com.ly.a316.ly_meetingroommanagement.meetingList.models;

/*
Date:2019/2/12
Time:20:09
auther:xwd
*/
public class Attendee {
    private String image;
    private String name;
    private String id;
    private boolean isSign=false;

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
