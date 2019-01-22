package com.ly.a316.ly_meetingroommanagement.meetting.services;

/*
Date:2019/1/19
Time:16:25
auther:xwd
*/
public interface DeptService {
    //获得所有部门的数据
     void getAllDepartemnt();
     void getAllEmployeeByDepartmentId(String departmentId);
}
