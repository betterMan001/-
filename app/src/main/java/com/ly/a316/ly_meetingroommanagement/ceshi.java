package com.ly.a316.ly_meetingroommanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.activites.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ceshi extends BaseActivity {

    @BindView(R.id.ceshi_toolBar)
    Toolbar ceshiToolBar;
    @BindView(R.id.ceshi_textview)
    TextView ceshiTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi);
        ButterKnife.bind(this);
        setSupportActionBar(ceshiToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }


    @OnClick({R.id.ceshi_textview })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ceshi_textview:
                Toast.makeText(ceshi.this,"我被点击了",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            Toast.makeText(ceshi.this," 点击了",Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
