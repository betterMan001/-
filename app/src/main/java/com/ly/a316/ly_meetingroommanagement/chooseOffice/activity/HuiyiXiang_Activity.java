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
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Hui_Shiyong;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;

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
    @BindView(R.id.hui_txt_shijian)
    TextView hui_txt_shijian;
    @BindView(R.id.hui_dizhi)
    TextView hui_dizhi;
    @BindView(R.id.hui_xiang)
    TextView huiXiang;
    @BindView(R.id.hui_sure)
    Button huiSure;
    DeviceDaoImp deviceDaoImp;
    List<Hui_Device> list_huidevice = new ArrayList<>();
    OneHui_MyAdapter oneHui_myAdapter;
    String dates = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huiyi_xiang_);
        ButterKnife.bind(this);
        huiyiInformation = (HuiyiInformation) getIntent().getSerializableExtra("classs");
        if(getIntent().getStringExtra("dates")!=null){
            dates = getIntent().getStringExtra("dates");
        }
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
                if(dates != null){
                    deviceDaoImp.getOneHuiShiyong(huiyiInformation.getmRoomId(),dates);
                }
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
                sure_go();
                break;
        }
    }

    void sure_go(){
        String hui_where = hui_dizhi.getText().toString();
       OrderDetailMeetingActivity.start(this,ShijiandianClass.DATESTRING,ShijiandianClass.END_DIAN_TIME,0,hui_where,huiyiInformation.getmRoomId());
    }
    public void get_success(List<Hui_Device>  list_huidevicee) {
        huiyiType.setText("会议室类型：" + ShijiandianClass.HUIYI_Leixing);
        list_huidevice.addAll( list_huidevicee);
        oneHui_myAdapter.notifyDataSetChanged();
    }
    public void get_success_shi(Hui_Shiyong hui_shiyong){

        String dsafdsf= "";
        try {
            String[] strs;
            strs = hui_shiyong.getTime().split(",");
            if(strs.length >=2){
                dsafdsf+=strs[0].substring(2,13)+"  ";
                for(int i=1;i<strs.length - 1;i++){
                    dsafdsf+=strs[i].substring(1,12)+"  ";
                }
                dsafdsf = dsafdsf+strs[strs.length-1].substring(1,12);
            }else{
                dsafdsf+=strs[0].substring(2,13);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        hui_txt_shijian.setText(dsafdsf);
    }
}
