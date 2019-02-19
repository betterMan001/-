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

import java.io.File;
import java.util.List;

/**
 * 作者：余智强
 * 2019/2/14
 */
public class CommonFileAdapter extends RecyclerView.Adapter<CommonFileAdapter.FilePickerViewHolder> {
    private Context mContext;
    private List<FileEntity> mData;
    private OnFileItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnFileItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommonFileAdapter(Context context, List<FileEntity> data) {
        this.mContext = context;
        mData = data;
    }

    @Override
    public FilePickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker, parent, false);
        return new FilePickerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FilePickerViewHolder holder, int position) {
        final FileEntity entity = mData.get(position);
        holder.tvName.setText(entity.getFileName());
        holder.tvDetail.setText(entity.getMimeType());
        String title = entity.getFileType().getTitle();
        if (entity.isSelected()) {
            holder.ivChoose.setImageResource(R.drawable.file_choice);
        } else {
            holder.ivChoose.setImageResource(R.mipmap.file_no_selection);
        }
        if (title.equals("IMG")) {
            Glide.with(mContext).load(new File(entity.getFilePath())).into(holder.ivType);
        } else {
            holder.ivType.setImageResource(entity.getFileType().getIconStyle());
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
        return mData.size();
    }

    class FilePickerViewHolder extends RecyclerView.ViewHolder {
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