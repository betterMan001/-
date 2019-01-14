package com.ly.a316.ly_meetingroommanagement.main.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.login.activities.WelcomeActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    Unbinder unbinder;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fr_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.my_exit_ll)
    public void onViewClicked() {
        loginOut();
    }
    private void loginOut(){
        //1.清空本地存储的数据
        MyApplication.editor.remove("token");
        MyApplication.editor.commit();
        //2.退出云信账号
        NIMClient.getService(AuthService.class).logout();
        //3.跳转至欢迎界面
        Intent intent = new Intent(MyApplication.getContext(), WelcomeActivity.class);
        //4.将loginActivity放入新的task
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //5.跳转
        MyApplication.getContext().startActivity(intent);
    }
}
