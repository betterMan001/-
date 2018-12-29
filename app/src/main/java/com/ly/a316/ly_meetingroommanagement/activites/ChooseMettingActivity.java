package com.ly.a316.ly_meetingroommanagement.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.Adapter.choosemettingAdapter;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.PreviewRecycleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseMettingActivity extends AppCompatActivity {
    private PreviewRecycleView mRecyclerView;
    private choosemettingAdapter mAdapter;
    private List<Integer> mDatas;
    private ImageView mImg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_metting);
        mImg = (ImageView) findViewById(R.id.choose_mettingpicture);

        initData();

        mRecyclerView = (PreviewRecycleView) findViewById(R.id.id_recyclerview_horizontal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new choosemettingAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnItemScrollChangeListener(new PreviewRecycleView.OnItemScrollChangeListener()
        {
            @Override
            public void onChange(View view, int position)
            {

                mImg.setImageResource(mDatas.get( position%mDatas.size()));
            };
        });

        mAdapter.setOnItemClickLitener(new choosemettingAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT)
                        .show();
                mImg.setImageResource(mDatas.get(position));
            }
        });

    }

    /*

    初始化数据
     */
    private void initData() {
        mDatas=new ArrayList<Integer>(
                Arrays.asList(
                        R.drawable.bb_ground,R.drawable.bg,R.drawable.ali,
                        R.drawable.alii,R.drawable.aliiii,R.drawable.ali
                )
        );
    }
}
