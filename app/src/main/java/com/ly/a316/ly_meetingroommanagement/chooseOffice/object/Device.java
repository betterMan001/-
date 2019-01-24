package com.ly.a316.ly_meetingroommanagement.chooseOffice.object;

import java.io.Serializable;

/**
 * 作者：余智强
 * 2019/1/22
 */
public class Device  implements Serializable{
    int dId;
    String dName;
    String dDate;
    String dType;
    String choose = "1";
    String didian;



    public Device(int dId, String dName, String dDate, String dType) {
        this.dId = dId;
        this.dName = dName;
        this.dDate = dDate;
        this.dType = dType;
        this.choose = "1";
    }

    public Device(String dName) {
        this.dName = dName;
        this.choose = "1";
    }
    public Device(){

    }

    public String getDidian() {
        return didian;
    }

    public void setDidian(String didian) {
        this.didian = didian;
    }

    public int getdId() {
        return dId;
    }

    public void setdId(int dId) {
        this.dId = dId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}
