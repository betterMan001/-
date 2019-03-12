package com.ly.a316.ly_meetingroommanagement.popPage.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.popPage.Adapter.MyHui_Adapter;
import com.ly.a316.ly_meetingroommanagement.popPage.DaoImp.GetInformationDaoImp;
import com.ly.a316.ly_meetingroommanagement.popPage.customview.InputTextMsgDialog;
import com.ly.a316.ly_meetingroommanagement.popPage.object.Information_Hui;
import com.ly.a316.ly_meetingroommanagement.popPage.object.LiuYan_class;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Information_meet extends AppCompatActivity {
    String mID;
    @BindView(R.id.information_img_bg)
    SimpleDraweeView informationImgBg;//会议室的照片
    @BindView(R.id.information_txt_title)
    TextView informationTxtTitle;//会议主题
    @BindView(R.id.information_txt_time)
    TextView informationTxtTime;//会议开始时间
    @BindView(R.id.information_txt_where)
    TextView informationTxtWhere;//会议地点
    @BindView(R.id.information_txt_renshu)
    TextView informationTxtRenshu;//会议参会人数
    @BindView(R.id.information_txt_faqiren)
    TextView informationTxtFaqiren;//会议发起人
    @BindView(R.id.information_txt_jiluren)
    TextView information_txt_jiluren;
    @BindView(R.id.information_toolbar)
    Toolbar informationToolbar;

    GetInformationDaoImp getInformationDao;
    @BindView(R.id.information_recycle_jiluren)
    RecyclerView informationRecycleJiluren;
    MyHui_Adapter myHui_adapter;
    List<LiuYan_class> liuYan_classes_one;
    String img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_meet);
        ButterKnife.bind(this);
        informationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
        }
        mID = getIntent().getStringExtra("mId");
        img_url = getIntent().getStringExtra("img");
        Uri uri = Uri.parse(img_url);
        informationImgBg.setImageURI(uri);
        getInformationDao = new GetInformationDaoImp(this);
        getInformationDao.getAllinformation(mID);

        getInformationDao.get_AllLiuyan(mID);
        init();
    }

    void init() {
        inputTextMsgDialog = new InputTextMsgDialog(this, R.style.dialog_center);
        inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                //点击发送按钮后，回调此方法，msg为输入的值
                getInformationDao.send_liuyan("18248612936", mID, msg);
                Toast.makeText(Information_meet.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        liuYan_classes_one = new ArrayList<>();
        myHui_adapter = new MyHui_Adapter(this, liuYan_classes_one);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        informationRecycleJiluren.setLayoutManager(ms);
        informationRecycleJiluren.setAdapter(myHui_adapter);

    }

    public void success_back(Information_Hui information_hui) {
        informationTxtTitle.setText(information_hui.getTitle());
        informationTxtTime.setText(information_hui.getBegin());
        informationTxtWhere.setText(information_hui.getAddress());
        informationTxtRenshu.setText("应到:" + information_hui.getAll() + "人 确认参加：" + information_hui.getSure());
        informationTxtFaqiren.setText(information_hui.getSender());
        information_txt_jiluren.setText(information_hui.getRecord());
    }

    InputTextMsgDialog inputTextMsgDialog;

    public void liuyan(View view) {
        inputTextMsgDialog.show();
    }

    public void success_send() {
        Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
        getInformationDao.get_AllLiuyan(mID);
    }

    public void success_get(List<LiuYan_class> liuYan_classes) {
        liuYan_classes_one.clear();
        liuYan_classes_one.addAll(liuYan_classes);
        myHui_adapter.notifyDataSetChanged();
    }
}
