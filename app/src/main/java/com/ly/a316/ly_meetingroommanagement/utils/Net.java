package com.ly.a316.ly_meetingroommanagement.utils;

/*
Date:2018/12/4
Time:16:43
auther:xwd
*/
public class Net {
    //xwd
    public final static String FAIL="访问网络失败";//访问网络失败
    public final static String HEAD="http://47.101.215.241:8080/api/";//阿里云服务器
    public final static String LOGIN="User/iLogin";//登录
    public final static String CHANGE_PWD ="User/changePwd";//忘记密码
    public final static String REGISTER="User/register";//注册
    public final static String UPLOAD="User/uploadProfile";//上传文件，返回URL
    public final static String DEPT_INFO="dept/deptInfo";//获取所有部门数据
    public final static String DEPT_EMPLOYEE="dept/deptEmployee";
    //yzq
    public static String url = HEAD;
    public static String addSchedule = url + "/Schedule/addSchedule";//新建日程
    public static String getAllShebei = url + "/meetRoom/allDevice";//获取设备需求
    public static String getAllDiDian = url + "/meetRoom/allAddress";//获取所有地点
    public static String getAllType = url + "/roomType/allRoomType";//获取所有会议室类型
    public static String subbmit_meetroom = url + "/meetRoom/allMeetRoom";//发布会议
    public static String getOneHuiroom = url+"/meetRoom/roomDetail";//获取单个会议详情

}
