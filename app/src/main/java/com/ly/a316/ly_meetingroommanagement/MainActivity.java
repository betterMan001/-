package com.ly.a316.ly_meetingroommanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.Fragment.Fr_calendar;
import com.ly.a316.ly_meetingroommanagement.Fragment.Fr_mine;
import com.ly.a316.ly_meetingroommanagement.Fragment.Fr_work;
import com.ly.a316.ly_meetingroommanagement.Utils.PopupMenuUtil;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *  描述：主活动
 *  作者：余智强
 *  创建时间：2018 12/4 13：27
*/
public class MainActivity extends FragmentActivity {
    @BindView(R.id.ac_main_txt_mine)
    TextView ac_main_txt_mine;

    @BindView(R.id.ac_main_txt_schedule)
    TextView ac_main_txt_schedule;

    @BindView(R.id.ac_iv_img)
    ImageView ac_iv_img;

    Fragment fr_calendar, fr_mine;
    private FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        ac_main_txt_schedule.performClick();
    }

    @OnClick({R.id.ac_main_txt_schedule, R.id.ac_iv_img, R.id.ac_main_txt_mine})
    void clickEvent(View view) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllfragment(fTransaction);
        switch (view.getId()) {
            case R.id.ac_main_txt_schedule:
                setSelected();
                ac_main_txt_schedule.setSelected(true);
                if (fr_calendar == null) {
                    fr_calendar = new Fr_calendar();
                    fTransaction.add(R.id.ac_main_frameLayout, fr_calendar);
                } else {
                    fTransaction.show(fr_calendar);
                }
                fTransaction.commit();
                break;
            case R.id.ac_main_txt_mine:
                setSelected();
                ac_main_txt_mine.setSelected(true);
                if (fr_mine == null) {
                    fr_mine = new Fr_mine();
                    fTransaction.add(R.id.ac_main_frameLayout, fr_mine);
                } else {
                    fTransaction.show(fr_mine);
                }
                fTransaction.commit();
                break;

            case R.id.ac_iv_img:
            PopupMenuUtil.getInstance()._show(this, ac_iv_img);
                break;
        }

    }

    //充值所有文本的选中状态
    private void setSelected() {
        ac_main_txt_schedule.setSelected(false);

        ac_main_txt_mine.setSelected(false);
    }

    //隐藏所有fragemnt
    private void hideAllfragment(FragmentTransaction fragmentTransaction) {
        if (fr_calendar != null) {
            fragmentTransaction.hide(fr_calendar);
        }

        if (fr_mine != null) {
            fragmentTransaction.hide(fr_mine);
        }
    }

}
