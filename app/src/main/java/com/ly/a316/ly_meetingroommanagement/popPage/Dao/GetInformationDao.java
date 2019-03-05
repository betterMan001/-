package com.ly.a316.ly_meetingroommanagement.popPage.Dao;

/**
 * 作者：余智强
 * 2019/3/5
 */
public interface GetInformationDao {
    void getAllinformation(String mId);
    void send_liuyan(String phone,String mId,String comment);
    void get_AllLiuyan(String mId);
}
