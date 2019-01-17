package com.ly.a316.ly_meetingroommanagement.meetting.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.classes.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.classes.LevelZero;

import java.util.List;

/*
Date:2019/1/17
Time:20:28
auther:xwd
*/
public class MulitemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;


    public MulitemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.invite_list_item_zero);
        addItemType(TYPE_LEVEL_1,R.layout.invite_list_item_one);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                final LevelZero lv0 = (LevelZero) item;
                helper.setText(R.id.Lv0_tv, lv0.departmentName);
                final int poss=helper.getAdapterPosition();
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = poss;
                        if (lv0.isExpanded()) {
                            helper.setImageResource(R.id.list_tr,R.drawable.down_tri001);
                            collapse(pos);
                        } else {
                            helper.setImageResource(R.id.list_tr,R.drawable.right_tri001);
                            expand(pos);
                        }
                    }
                });
                break;
            case 1:
                final LevelOne lv1 = (LevelOne) item;
              //  helper.setImageResource(R.id.Lv1_iv, lv1.friendSculpture);
                helper.setText(R.id.Lv1_tv,lv1.name);
                helper.setText(R.id.invite_job_tv,lv1.eJob);
//                helper.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });

                break;
        }
    }
}
