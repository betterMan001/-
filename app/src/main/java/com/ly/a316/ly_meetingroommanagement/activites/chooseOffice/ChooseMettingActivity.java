package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ly.a316.ly_meetingroommanagement.Adapter.choosemettingAdapter;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseMettingActivity extends AppCompatActivity {

    @BindView(R.id.choose_metting_recy)
    RecyclerView chooseMettingRecy;
    private choosemettingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_metting);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


    }


}
