package com.ly.a316.ly_meetingroommanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：余智强
 * 2019/5/1
 */
public class PreferencesService {
    private Context context;

    public PreferencesService(Context context) {
        this.context = context;
    }
    public void save(String feceinfo,String name) {
        //取得SharePreferences对象,通过上下文环境得到
        SharedPreferences preferences = context.getSharedPreferences("faces", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();//得到编辑器对象
        editor.putString("faseinfo", feceinfo);
        editor.putString("name", name);
        editor.commit();//把内存中的数据提交到文件中
    }
    public Map<String,String> getPreferences(){
        Map<String,String> params = new HashMap<String,String>();
        //取得SharePreferences对象,通过上下文环境得到,"gao"是之前保存好的数据名称，注意不带后缀名
        SharedPreferences preferences = context.getSharedPreferences("faces", Context.MODE_PRIVATE);
        params.put("faseinfo", preferences.getString("faseinfo","0"));
        params.put("name", preferences.getString("name","0"));
        return params;
    }
}
