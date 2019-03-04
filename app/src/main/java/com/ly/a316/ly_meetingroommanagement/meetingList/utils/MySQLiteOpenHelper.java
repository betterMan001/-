package com.ly.a316.ly_meetingroommanagement.meetingList.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;

/*
Date:2019/2/26
Time:10:19
auther:xwd
*/
public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    public static final String CREATE_MEETINGLIST="create table meetingList("
            +"id integer primary key autoincrement,mId varchar(200),name varchar(200))";
    private Context mContext;
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
     db.execSQL(CREATE_MEETINGLIST);
        Toast.makeText(mContext,"Create succeeded!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
