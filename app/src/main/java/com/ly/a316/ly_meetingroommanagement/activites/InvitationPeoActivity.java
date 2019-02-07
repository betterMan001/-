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

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment.TimeDianFragment;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.DeviceType;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MettingPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;


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
    @BindView(R.id.biaoti)
    TextView biaoti;
    int chooseNum = 0;
    MettingPeopleAdapter mettingPeopleAdapter;
    List<Device> list = new ArrayList<>();
    List<DeviceType> list_type = new ArrayList<>();
    DeviceDaoImp deviceDaoImp = new DeviceDaoImp(this);
    String whoo;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.white).init();
        setContentView(R.layout.activity_invitation_peo);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        inviRecyclerview.setLayoutManager(linearLayoutManager);

        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.show();
        Intent intent = getIntent();
        whoo = intent.getStringExtra("who");
        if (whoo.equals("1")) {
            biaoti.setText("设备列表");
        } else if (whoo.equals("2")) {
            biaoti.setText("会议室列表");
        } else if (whoo.equals("3")) {
            biaoti.setText("会议类型");
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (whoo.equals("1")) {
                    deviceDaoImp.getAllDevice();
                } else if (whoo.equals("2")) {
                    deviceDaoImp.getAllDiDian();
                } else if (whoo.equals("3")) {
                    deviceDaoImp.getAllType();
                }
            }
        });
        thread.start();
        if (whoo.equals("3")) {
            mettingPeopleAdapter = new MettingPeopleAdapter(this, list_type, "3");
        } else {
            mettingPeopleAdapter = new MettingPeopleAdapter(this, list);
        }
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
                    if (whoo.equals("3")) {
                        list_type.get(position).setChoose("0");
                    } else {
                        list.get(position).setChoose("0");
                    }
                } else if (a.equals("0")) {
                    chooseNum--;
                    tvSelectNum.setText(chooseNum + "");
                    viewHolder.check_box.setImageResource(R.mipmap.ic_uncheck);
                    viewHolder.pan.setText("1");

                    if (whoo.equals("3")) {
                        list_type.get(position).setChoose("1");
                    } else {
                        list.get(position).setChoose("1");
                    }
                }
                setBtnBackground(chooseNum);
            }
        });
    }

    public void call_success_back(List<Device> list_Device) {
        loadingDialog.dismiss();
        list.clear();
        list.addAll(list_Device);
        mettingPeopleAdapter.notifyDataSetChanged();
    }

    public void call_success_dedian(List<Device> list_Device) {
        loadingDialog.dismiss();
        list.clear();
        list.addAll(list_Device);
        mettingPeopleAdapter.notifyDataSetChanged();
    }

    public void call_success_type(List<DeviceType> list_typee) {
        loadingDialog.dismiss();
        list_type.clear();
        list_type.addAll(list_typee);
        mettingPeopleAdapter.notifyDataSetChanged();
    }
    public void call_fail(){
        loadingDialog.dismiss();
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
            if (whoo.equals("3")) {
                for (int i = 0, j = list_type.size(); i < j; i++) {
                    list_type.get(i).setChoose("0");
                }
                numm = list_type.size();
            } else {
                for (int i = 0, j = list.size(); i < j; i++) {
                    list.get(i).setChoose("0");
                }
                numm = list.size();
            }
            btnSure.setEnabled(true);
            selectAll.setText("取消全选");
            isSelectAll = true;

        } else {
            if (whoo.equals("3")) {
                for (int i = 0, j = list_type.size(); i < j; i++) {
                    list_type.get(i).setChoose("1");
                }
                numm = 0;
            } else {
                for (int i = 0, j = list.size(); i < j; i++) {
                    list.get(i).setChoose("1");
                }
                numm = 0;
            }

            btnSure.setEnabled(false);
            selectAll.setText("全选");
            isSelectAll = false;

            chooseNum = 0;
        }
        mettingPeopleAdapter.notifyDataSetChanged();
        setBtnBackground(numm);
        tvSelectNum.setText(String.valueOf(numm));
    }

    String devicename;

    void subbmit() {
        String de_name = null;
        final List<Device> mlst = new ArrayList<Device>();
        final List<DeviceType> list_typee = new ArrayList<>();
        Device device;
        DeviceType deviceType;
        if (whoo.equals("3")) {
            for (int i = 0; i < list_type.size(); i++) {
                if (list_type.get(i).getChoose().equals("0")) {
                    deviceType = new DeviceType(list_type.get(i).getName());
                    if(devicename == null){
                        devicename = list_type.get(i).getId() + ",";
                    }else{
                        devicename += list_type.get(i).getId() + ",";
                    }
                    de_name += list_type.get(i).getName() + ",";
                    list_typee.add(deviceType);
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getChoose().equals("0")) {
                    device = new Device(list.get(i).getdName());
                    if(devicename == null){
                        devicename = list.get(i).getdId() + ",";
                    }else {
                        devicename += list.get(i).getdId() + ",";
                    }
                    de_name += list.get(i).getdName() + ",";
                    mlst.add(device);
                }
            }
        }
        if (whoo.equals("3")) {
            ShijiandianClass.HUIYI_LEIXING = devicename;
        } else if(whoo.equals("1")){
            ShijiandianClass.SHEBEI = devicename;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        final TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;
        if (isSelectAll) {
            if (whoo.equals("1")) {
                msg.setText("你选了" + chooseNum + "设备，是否确定选择此设备？");
            } else if (whoo.equals("2")) {
                msg.setText("你选了" + chooseNum + "地点，是否确定选择此地点？");
            } else {
                msg.setText("你选择了" + chooseNum + "会议类型，是否确实此类型？");
            }
        } else {

            if (whoo.equals("1")) {
                msg.setText("你选了" + de_name + "设备，是否确定选择此设备？");
            } else if (whoo.equals("2")) {
                msg.setText("你选了" + de_name + "地点，是否确定选择此地点？");
            } else {
                msg.setText("你选择了" + de_name + "会议类型，是否确实此类型？");
            }

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
                Intent intent1 = new Intent(InvitationPeoActivity.this, TimeDianFragment.class);
                if (whoo.equals("1")) {
                    //设备
                    String dsadsa = "";
                    for (int i = 0; i < mlst.size() - 1; i++) {
                        dsadsa += mlst.get(i).getdName().toString() + ",";
                    }
                    dsadsa += mlst.get(mlst.size() - 1).getdName().toString();
                    intent1.putExtra("qqqq", dsadsa);

                    InvitationPeoActivity.this.setResult(123, intent1);
                } else if (whoo.equals("2")) {
                    //地点
                    String dsadsa = "";
                    for (int i = 0; i < mlst.size() - 1; i++) {
                        dsadsa += mlst.get(i).getdName().toString() + ",";
                    }
                    dsadsa += mlst.get(mlst.size() - 1).getdName().toString();
                    intent1.putExtra("qqqq", dsadsa);
                    InvitationPeoActivity.this.setResult(124, intent1);
                } else if (whoo.equals("3")) {
                    String dsadsa = "";
                    for (int i = 0; i < list_typee.size() - 1; i++) {
                        dsadsa += list_typee.get(i).getName().toString() + ",";
                    }
                    dsadsa += list_typee.get(list_typee.size() - 1).getName().toString();
                    intent1.putExtra("qqqq", dsadsa);
                    InvitationPeoActivity.this.setResult(150, intent1);
                }
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
