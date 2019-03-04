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
    public final static String DEPT_EMPLOYEE="dept/deptEmployee";//获取对应部门的员工信息
    public final static String UNLOCK_ROOM="meetRoom/unlockRoom";//解锁选定会议室
    public final static String BOOK_MEET_ROOM="meetRoom/bookMeetRoom";//预订会议室接口
    public final static String OPTIN="Meeting/optIn";
    public final static String SELECT_MEETING_ByS_EmployeeId="Meeting/selectMeetingBySEmployeeId";//根据职工编号获取会议
    public final static String ATTENDERS="Meeting/attenders";//参会人列表
    public final static String ADD_ATTENDER="Meeting/addAttender";//添加参会人
    public final static String MEET_DETAIL="Meeting/meetDetail";//会议详情
    public final static String SIGN_INCASE="Meeting/signInCase";//签到情况
    public final static String ADMIN_JPUSH="jpush/adminJpush";//管理员发送的通知
    public final static String USER_JPUSH="jpush/userJpush";//用户发的通知
    //yzq
    public static String url = HEAD;
    public static String addSchedule = url + "/Schedule/addSchedule";//新建日程
    public static String getAllShebei = url + "/meetRoom/allDevice";//获取设备需求
    public static String getAllDiDian = url + "/meetRoom/allAddress";//获取所有地点
    public static String getAllType = url + "/roomType/allRoomType";//获取所有会议室类型
    public static String subbmit_meetroom = url + "/meetRoom/allMeetRoom";//发布会议
    public static String getOneHuiroom = url+"/meetRoom/roomDetail";//获取单个会议详情
    public static String transFile = url+"/User/uploadProfile";//上传文件
    public static String schedule_face = url+"/User/facerRgister";//上传人脸信息

}
