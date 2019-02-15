package com.ly.a316.ly_meetingroommanagement.endActivity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.endActivity.adaper.CommonFileAdapter;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileEntity;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnFileItemClickListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnUpdateDataListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.FileScannerTask;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.PickerManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommFileFragment extends Fragment implements FileScannerTask.FileScannerListener  {

    @BindView(R.id.rl_normal_file)
    RecyclerView rl_NormalFile;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.comm_fileprogress)
    ProgressBar commFileprogress;
    Unbinder unbinder;
    private OnUpdateDataListener mOnUpdateDataListener;

    private CommonFileAdapter mCommonFileAdapter;
    public void setOnUpdateDataListener(OnUpdateDataListener onUpdateDataListener) {
        mOnUpdateDataListener = onUpdateDataListener;
    }

    public static CommFileFragment newInstance() {
        return new CommFileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comm_file, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview(view);
        initdata();
        return view;
    }
    private void initview(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rl_NormalFile.setLayoutManager(layoutManager);
        commFileprogress.setVisibility(View.VISIBLE);
    }
    private void initdata(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //这里我将进行读取手机内存数据，需要在开启一个线程，避免主线程阻塞
                        new FileScannerTask(getContext(),CommFileFragment.this).execute();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(getContext(),"读写SD卡权限被拒绝",Toast.LENGTH_SHORT).show();
                    }
                }).start();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void scannerResult(List<FileEntity> entities) {
        commFileprogress.setVisibility(View.GONE);
        if(entities.size()>0){
            emptyView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
        }
        mCommonFileAdapter = new CommonFileAdapter(getContext(),entities);
        rl_NormalFile.setAdapter(mCommonFileAdapter);
        iniEvent(entities);
    }

    private void iniEvent(final List<FileEntity> entities) {
        mCommonFileAdapter.setOnItemClickListener(new OnFileItemClickListener() {
            @Override
            public void click(int position) {
                FileEntity entity = entities.get(position);
                String absolutePath = entity.getFilePath();
                ArrayList<FileEntity> files = PickerManager.getInstance().files;
                if(files.contains(entity)){
                    files.remove(entity);
                    if(mOnUpdateDataListener!=null){
                        mOnUpdateDataListener.update(-Long.parseLong(entity.getSize()));
                    }
                    entity.setSelected(!entity.isSelected());
                    mCommonFileAdapter.notifyDataSetChanged();
                }else {
                    if(PickerManager.getInstance().files.size()<PickerManager.getInstance().maxCount){
                        files.add(entity);
                        if(mOnUpdateDataListener!=null){
                            mOnUpdateDataListener.update(Long.parseLong(entity.getSize()));
                        }
                        entity.setSelected(!entity.isSelected());
                        mCommonFileAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getContext(),getString(R.string.file_select_max,PickerManager.getInstance().maxCount),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
