package com.ly.a316.ly_meetingroommanagement.chooseOffice.dao;

/**
 * 作者：余智强
 * 2019/1/22
 */
public interface deviceDao {
    void getAllDevice();//获取设备
    void getAllDiDian();//获取所有地点
    void getAllType();//获取所类型
    void subbmitHuiyi(int whooer);//发布会议
    void getOneHuiroom(String roomid, String type_id);  //和取单个会议室信息
    void getAllDevice_inEndActivity();
    void getOneHuiShiyong(String roomId,String dates);//获取某间会议室的使用情况
}
