package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.object;

/**
 * 作者：余智强
 * 2019/2/28
 */
public class Item {
    private String title_name;
    private int imade_id;
    public Item() {

    }
    public Item(String title_name, int imade_id) {
        this.title_name = title_name;
        this.imade_id = imade_id;
    }

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public int getImade_id() {
        return imade_id;
    }

    public void setImade_id(int imade_id) {
        this.imade_id = imade_id;
    }
}
