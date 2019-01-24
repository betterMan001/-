package com.ly.a316.ly_meetingroommanagement.chooseOffice.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.Adapter.OneHui_MyAdapter;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Hui_Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuiyiXiang_Activity extends AppCompatActivity {
    HuiyiInformation huiyiInformation;
    @BindView(R.id.hui_image)
    ImageView huiImage;
    @BindView(R.id.hui_where)
    TextView huiWhere;
    @BindView(R.id.hui_rongliang)
    TextView huiRongliang;
    @BindView(R.id.huiyi_type)
    TextView huiyiType;
    @BindView(R.id.hui_recycleview_she)
    RecyclerView huiRecycleviewShe;
    @BindView(R.id.hui_dizhi)
    TextView hui_dizhi;
    @BindView(R.id.hui_xiang)
    TextView huiXiang;
    @BindView(R.id.hui_sure)
    Button huiSure;
    DeviceDaoImp deviceDaoImp;
    List<Hui_Device> list_huidevice = new ArrayList<>();
    OneHui_MyAdapter oneHui_myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huiyi_xiang_);
        ButterKnife.bind(this);
        huiyiInformation = (HuiyiInformation) getIntent().getSerializableExtra("classs");
        Log.i("zjc", huiyiInformation.getmAddress());
        initview();
        initdata();
    }

    void initview() {
        deviceDaoImp = new DeviceDaoImp(this);
        Glide.with(this).load(huiyiInformation.getmImageUrl()).into(huiImage);
        huiRongliang.setText("容量： " + huiyiInformation.getmNumber());
        huiWhere.setText(huiyiInformation.getmName());
        hui_dizhi.setText(huiyiInformation.getmAddress());
        huiXiang.setText(huiyiInformation.getmDetail());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                deviceDaoImp.getOneHuiroom(huiyiInformation.getmRoomId(), huiyiInformation.getmType());
            }
        });
        thread.start();
    }

    void initdata() {
          oneHui_myAdapter = new OneHui_MyAdapter(this,list_huidevice);
        huiRecycleviewShe.setAdapter(oneHui_myAdapter);
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局
        huiRecycleviewShe.setLayoutManager(ms);
    }


    @OnClick({R.id.hui_quxiao, R.id.hui_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hui_quxiao:
                finish();
                break;
            case R.id.hui_sure:
                break;
        }
    }

    public void get_success(List<Hui_Device>  list_huidevicee) {
        huiyiType.setText("会议室类型：" + ShijiandianClass.HUIYI_Leixing);
        list_huidevice.addAll( list_huidevicee);
        oneHui_myAdapter.notifyDataSetChanged();
    }
}
