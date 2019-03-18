package com.ly.a316.ly_meetingroommanagement.endActivity.dao;

/**
 * 作者：余智强
 * 2019/3/12
 */
public interface YanChangDao {
    public void yanchang(String mId);
    void baoxiu(String phone,String roomId,String deviceId,String info);
    void getAllpeopleTel(String mid);//获取人的参会人的电话
    void endMeet(String id);//结束会议
}
