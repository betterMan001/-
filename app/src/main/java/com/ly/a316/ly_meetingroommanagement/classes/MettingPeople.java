package com.ly.a316.ly_meetingroommanagement.classes;

import java.io.Serializable;

/**
 * 作者：余智强
 * 2018/12/11
 */
public class MettingPeople implements Serializable {
    private String name;//人的名字
    private String peotel;//人的电话
    private String choose;//是否选中

    public MettingPeople(String name, String peotel, String choose) {
        this.name = name;
        this.peotel = peotel;
        this.choose = choose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeotel() {
        return peotel;
    }

    public void setPeotel(String peotel) {
        this.peotel = peotel;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}
