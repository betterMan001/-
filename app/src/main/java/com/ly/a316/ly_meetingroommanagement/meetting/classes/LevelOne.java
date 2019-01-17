package com.ly.a316.ly_meetingroommanagement.meetting.classes;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MulitemAdapter;

/*
Date:2019/1/17
Time:20:25
auther:xwd
*/
public class LevelOne implements MultiItemEntity {
    public String eId;
    public String headURL;
    public String name;
    public String eJob;

    public String geteJob() {
        return eJob;
    }

    public void seteJob(String eJob) {
        this.eJob = eJob;
    }

    @Override
    public int getItemType() {
        return MulitemAdapter.TYPE_LEVEL_1;
    }

    public LevelOne(String eId, String headURL, String name, String eJob) {
        this.eId = eId;
        this.headURL = headURL;
        this.name = name;
        this.eJob = eJob;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
