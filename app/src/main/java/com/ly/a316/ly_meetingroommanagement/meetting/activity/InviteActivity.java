package com.ly.a316.ly_meetingroommanagement.meetting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MulitemAdapter;
import com.ly.a316.ly_meetingroommanagement.meetting.classes.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.classes.LevelZero;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {
    ArrayList<MultiItemEntity> res;
    List<LevelZero> lv0 = new ArrayList<>();
    @BindView(R.id.invite_rv)
    RecyclerView inviteRv;
    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,InviteActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        addData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        inviteRv.setAdapter(new MulitemAdapter(res));
        inviteRv.setLayoutManager(manager);
    }
    private void addData(){
        res = new ArrayList<>();
        lv0.clear();
        lv0.add(new LevelZero("","人事部"));
        lv0.add(new LevelZero("","财务部"));
        for(int i = 0;i<5;i++){
            LevelOne lv1 = new LevelOne("","","徐天宁","总经理");
            lv0.get(0).addSubItem(lv1);
        }
        for(int i = 0;i<5;i++){
            LevelOne lv1 = new LevelOne("","","赵天天","研发总监");
            lv0.get(1).addSubItem(lv1);
        }
        for (int j = 0; j < lv0.size(); j++) {
            res.add(lv0.get(j));
        }
    }
}
