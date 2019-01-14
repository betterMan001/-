package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.Adapter.MettingPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.MettingPeople;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitationPeoActivity extends BaseActivity {
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private int mEditMode = MYLIVE_MODE_CHECK;
    @BindView(R.id.btn_edit)
    TextView btnEdit;//编辑
    @BindView(R.id.invi_recyclerview)
    RecyclerView inviRecyclerview;
    private boolean isSelectAll = false;
    @BindView(R.id.tv_select_num)
    TextView tvSelectNum;//已经选择的人数
    @BindView(R.id.btn_sure)
    Button btnSure;//提交按钮
    @BindView(R.id.select_all)
    TextView selectAll;//选择全部人
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout ll_mycollection_bottom_dialog;
    int chooseNum = 0;
    MettingPeopleAdapter mettingPeopleAdapter;
    List<MettingPeople> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_peo);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        inviRecyclerview.setLayoutManager(linearLayoutManager);

        MettingPeople mettingPeople = new MettingPeople("张一", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张二", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张三", "25254254", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张思", "15424", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张无", "12325424", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张六", "12254654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张七", "12362554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张八", "12325454", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张九", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张十", "1236525", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张搜", "12365254", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("愿景撒", "123652554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张家港", "12253654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("刘水泵", "122523654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("胡思", "123652554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("胡三", "123652254", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张一", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张二", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张三", "25254254", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张思", "15424", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张无", "12325424", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张六", "12254654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张七", "12362554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张八", "12325454", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张九", "123654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张十", "1236525", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张搜", "12365254", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("愿景撒", "123652554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("张家港", "12253654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("刘水泵", "122523654", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("胡思", "123652554", "1");
        list.add(mettingPeople);
        mettingPeople = new MettingPeople("胡三", "123652254", "1");
        list.add(mettingPeople);

        mettingPeopleAdapter = new MettingPeopleAdapter(this, list);
        inviRecyclerview.setAdapter(mettingPeopleAdapter);

        mettingPeopleAdapter.setOnItemClick(new MettingPeopleAdapter.OnItemClickk() {
            @Override
            public void onItemclick(MettingPeopleAdapter.MyViewHolder viewHolder, int position) {
                String a = viewHolder.pan.getText().toString();
                if (a.equals("1")) {
                    chooseNum++;
                    tvSelectNum.setText(chooseNum + "");
                    viewHolder.check_box.setImageResource(R.mipmap.ic_checked);
                    viewHolder.pan.setText("0");
                    list.get(position).setChoose("0");
                } else if (a.equals("0")) {
                    chooseNum--;
                    tvSelectNum.setText(chooseNum + "");
                    viewHolder.check_box.setImageResource(R.mipmap.ic_uncheck);
                    viewHolder.pan.setText("1");
                    list.get(position).setChoose("0");
                }
                setBtnBackground(chooseNum);

            }
        });
    }

    @OnClick({R.id.btn_edit, R.id.btn_sure, R.id.select_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
                if (mEditMode == MYLIVE_MODE_EDIT) {
                    btnEdit.setText("取消");
                    ll_mycollection_bottom_dialog.setVisibility(View.VISIBLE);
                } else {
                    btnEdit.setText("编辑");
                    ll_mycollection_bottom_dialog.setVisibility(View.GONE);
                }
                mettingPeopleAdapter.setEditMode(mEditMode);
                break;
            case R.id.btn_sure:
                subbmit();
                break;
            case R.id.select_all:
                selectAll();
                break;
        }
    }

    void selectAll() {
        int numm;
        if (mettingPeopleAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = list.size(); i < j; i++) {
                list.get(i).setChoose("0");
            }
            btnSure.setEnabled(true);
            selectAll.setText("取消全选");
            isSelectAll = true;
            numm = list.size();
        } else {
            for (int i = 0, j = list.size(); i < j; i++) {
                list.get(i).setChoose("1");
            }
            btnSure.setEnabled(false);
            selectAll.setText("全选");
            isSelectAll = false;
            numm = 0;
            chooseNum=0;
        }
        mettingPeopleAdapter.notifyDataSetChanged();
        setBtnBackground(numm);
        tvSelectNum.setText(String.valueOf(numm));
    }

    void subbmit() {
        final List<MettingPeople> mlst = new ArrayList<MettingPeople>();
        MettingPeople mettingPeople;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getChoose().equals("0")) {
                mettingPeople = new MettingPeople(list.get(i).getName(), list.get(i).getPeotel(), list.get(i).getChoose());
                mlst.add(mettingPeople);
            }
        }
        Log.i("zjc", mlst.size() + "");
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;
        if (isSelectAll) {
            msg.setText("你邀请了" + list.size() + "人，是否确实人选？");
        } else {
            msg.setText("你邀请了" + chooseNum + "人，是否确实人选？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(InvitationPeoActivity.this, DetailsMettingActivity.class);
                intent1.putExtra("qqqq", (Serializable) mlst);
                InvitationPeoActivity.this.setResult(123, intent1);
                builder.dismiss();
                finish();
            }
        });

    }

    private void setBtnBackground(int size) {
        if (size != 0) {
            btnSure.setBackgroundResource(R.drawable.button_shape);
            btnSure.setEnabled(true);
            btnSure.setTextColor(Color.WHITE);
        } else {
            btnSure.setBackgroundResource(R.drawable.button_noclickable_shape);
            btnSure.setEnabled(false);
            btnSure.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }
}
