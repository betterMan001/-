package com.ly.a316.ly_meetingroommanagement.endActivity.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.endActivity.adaper.AllFileAdapter;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileEntity;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnFileItemClickListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnUpdateDataListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.FileSelectFilter;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.FileUtils;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.PickerManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllFileFragment extends Fragment {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.rl_all_file)
    RecyclerView rlAllFile;
    @BindView(R.id.empty_view)
    TextView emptyView;
    Unbinder unbinder;

    private String mPath;
    private String rootPath;
    private List<FileEntity> mListFiles;
    private FileSelectFilter mFilter;
    //筛选类型条件
    private String[] mFileTypes = new String[]{};
    private AllFileAdapter mAllFileAdapter;
    private OnUpdateDataListener mOnUpdateDataListener;


    public void setOnUpdateDataListener(OnUpdateDataListener onUpdateDataListener) {
        mOnUpdateDataListener = onUpdateDataListener;
    }

    public static AllFileFragment newInstance() {
        return new AllFileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_file, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview(view);
        initData();
        initEvent();
        return view;
    }
    void initview(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlAllFile = (RecyclerView) view.findViewById(R.id.rl_all_file);
        rlAllFile.setLayoutManager(layoutManager);
    }
    void initData(){
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        getData();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(getContext(),"读写sdk权限被拒绝",Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }
    private void getData(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getContext(), R.string.not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilter = new FileSelectFilter(mFileTypes);
        mListFiles = getFileList(mPath);
        mAllFileAdapter = new AllFileAdapter(getContext(),mListFiles,mFilter);
        rlAllFile.setAdapter(mAllFileAdapter);
    }

    void initEvent(){
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPath = new File(mPath).getParent();
                if (tempPath == null || mPath.equals(rootPath)) {
                    Toast.makeText(getContext(),"最外层了",Toast.LENGTH_SHORT).show();
                    return;
                }
                mPath = tempPath;
                mListFiles = getFileList(mPath);
                mAllFileAdapter.updateListData(mListFiles);
                mAllFileAdapter.notifyDataSetChanged();
            }
        });
        mAllFileAdapter.setOnItemClickListener(new OnFileItemClickListener() {
            @Override
            public void click(int position) {
                FileEntity entity = mListFiles.get(position);
                //如果是文件夹点击进入文件夹
                if (entity.getmFile().isDirectory()) {
                    getIntoChildFolder(position);
                }else {
                    File file = entity.getmFile();
                    ArrayList<FileEntity> files = PickerManager.getInstance().files;
                    if(files.contains(entity)){
                        files.remove(entity);
                        if(mOnUpdateDataListener!=null){
                            mOnUpdateDataListener.update(-file.length());
                        }
                        entity.setSelected(!entity.isSelected());
                        mAllFileAdapter.notifyDataSetChanged();
                    }else {
                        if(PickerManager.getInstance().files.size()<PickerManager.getInstance().maxCount){
                            files.add(entity);
                            if(mOnUpdateDataListener!=null){
                                mOnUpdateDataListener.update(file.length());
                            }
                            entity.setSelected(!entity.isSelected());
                            mAllFileAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(),getString(R.string.file_select_max,PickerManager.getInstance().maxCount),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    //进入子文件夹
    private void getIntoChildFolder(int position) {
        mPath = mListFiles.get(position).getmFile().getAbsolutePath();
        //更新数据源
        mListFiles = getFileList(mPath);
        mAllFileAdapter.updateListData(mListFiles);
        mAllFileAdapter.notifyDataSetChanged();
        rlAllFile.scrollToPosition(0);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    private List<FileEntity> getFileList(String path) {
        List<FileEntity> fileListByDirPath = FileUtils.getFileListByDirPath(path, mFilter);
        if(fileListByDirPath.size()>0){
            emptyView.setVisibility(View.GONE);
        }else {
            emptyView.setVisibility(View.VISIBLE);
        }
        return fileListByDirPath;
    }
}
