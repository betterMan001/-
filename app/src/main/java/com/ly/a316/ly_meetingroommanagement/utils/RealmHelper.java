package com.ly.a316.ly_meetingroommanagement.utils;

import com.ly.a316.ly_meetingroommanagement.classes.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 作者：余智强
 * 2018/12/29
 */
public class RealmHelper {
    private Realm mRealm;
    private SimpleDateFormat sdf1;
    private volatile static RealmHelper realmHelperInstance;


    private RealmHelper() {
        String pat1 = "yyyy-MM-dd" ;
        sdf1 = new SimpleDateFormat(pat1) ; // 实例化模板对象
        mRealm = Realm.getDefaultInstance();
    }

    public static RealmHelper getRealmHelperInstance() {
        if (realmHelperInstance == null) {
            synchronized (RealmHelper.class) {
                if (realmHelperInstance == null) {
                    realmHelperInstance = new RealmHelper();
                }
            }
        }
        return realmHelperInstance;
    }

    /**
     * query （根据age查）
     */
    public List<EventModel> queryEventByDay(int year, int month, int day) throws ParseException {
        RealmResults<EventModel> eventModels = mRealm.where(EventModel.class)
                .equalTo("year", year)
                .equalTo("month", month)
                .equalTo("day", day)
                .findAll();
        return mRealm.copyFromRealm(eventModels);
    }
}
