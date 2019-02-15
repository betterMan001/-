package com.ly.a316.ly_meetingroommanagement.endActivity.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileEntity;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnFileItemClickListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * 作者：余智强
 * 2019/2/15
 */
public class AllFileAdapter extends RecyclerView.Adapter<AllFileAdapter.FilePickerViewHolder> {
    private List<FileEntity> mListData;
    private Context mContext;
    private FileFilter mFileFilter;
    private OnFileItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnFileItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AllFileAdapter(Context context, List<FileEntity> listData, FileFilter fileFilter) {
        mListData = listData;
        mContext = context;
        mFileFilter = fileFilter;
    }

    @Override
    public FilePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker, parent, false);
        return new FilePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilePickerViewHolder holder, int positon) {
        final FileEntity entity = mListData.get(positon);
        final File file = entity.getmFile();
        holder.tvName.setText(file.getName());
        if (file.isDirectory()) {
            holder.ivType.setImageResource(R.drawable.file_picker_folder);
            holder.ivChoose.setVisibility(View.GONE);
        } else {
            if(entity.getFileType()!=null){
                String title = entity.getFileType().getTitle();
                if (title.equals("IMG")) {
                    Glide.with(mContext).load(new File(entity.getFilePath())).into(holder.ivType);
                } else {
                    holder.ivType.setImageResource(entity.getFileType().getIconStyle());
                }
            }else {
                holder.ivType.setImageResource(R.drawable.file_picker_def);
            }
            holder.ivChoose.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(FileUtils.getReadableFileSize(file.length()));

            if (entity.isSelected()) {
                holder.ivChoose.setImageResource(R.drawable.file_choice);
            } else {
                holder.ivChoose.setImageResource(R.mipmap.file_no_selection);
            }
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.click(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    /**
     * 更新数据源
     *
     * @param mListData
     */
    public void updateListData(List<FileEntity> mListData) {
        this.mListData = mListData;
    }
    public class FilePickerViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout layoutRoot;
        protected ImageView ivType, ivChoose;
        protected TextView tvName;
        protected TextView tvDetail;

        public FilePickerViewHolder(View itemView) {
            super(itemView);
            ivType = (ImageView) itemView.findViewById(R.id.iv_type);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_item_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            ivChoose = (ImageView) itemView.findViewById(R.id.iv_choose);
        }
    }
}

