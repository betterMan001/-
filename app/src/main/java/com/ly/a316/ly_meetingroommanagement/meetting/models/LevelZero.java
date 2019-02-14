package com.ly.a316.ly_meetingroommanagement.meetting.models;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MulitemAdapter;

import java.util.List;

/*
Date:2019/1/17
Time:20:24
auther:xwd
*/
public class LevelZero extends AbstractExpandableItem<LevelOne> implements MultiItemEntity {
public String departmentId;
public String departmentName;
public List<LevelOne> list;
    public LevelZero(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    @Override
    public List<LevelOne> getSubItems() {
       return list;
    }

    @Override
    public void setSubItems(List<LevelOne> list) {
        this.list=list;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MulitemAdapter.TYPE_LEVEL_0;
    }
}
