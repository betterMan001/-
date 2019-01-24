package com.ly.a316.ly_meetingroommanagement.chooseOffice.object;

import java.io.Serializable;

/**
 * 作者：余智强
 * 2019/1/23
 */
public class DeviceType implements Serializable{
    int id;
    String name;
    String classify;
    String url;
    String choose = "1";

    public DeviceType() {

    }

    public DeviceType(String name) {
        this.name = name;
    }

    public DeviceType(int id, String name, String classify, String url) {
        this.id = id;
        this.name = name;
        this.classify = classify;
        this.url = url;
        this.choose = "1";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}
