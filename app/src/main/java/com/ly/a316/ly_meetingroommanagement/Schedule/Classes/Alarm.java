package com.ly.a316.ly_meetingroommanagement.Schedule.Classes;

/**
 * 作者：余智强
 * 2018/12/26
 */
public class Alarm {
    String name;
    boolean sign;

    public Alarm(String name, boolean sign) {
        this.name = name;
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }
}
