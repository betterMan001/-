package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.NewInviteActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.DeptServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelZero;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Date:2019/1/17
Time:20:28
auther:xwd
*/
public class MulitemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public NewInviteActivity activity;
    public  int Tpos;
    LevelZero currentData;
    List<MultiItemEntity> levelData;
    Boolean checkAll=false;
    //用map保存对应部门数据是否被加载过
    Map isLoadDepartmentMap=new HashMap();
    Map isSelectDepartmentMap=new HashMap();
    RoundedImageView imageView;
    public MulitemAdapter(List<MultiItemEntity> data, NewInviteActivity activity) {
        super(data);
        levelData=data;
        this.activity=activity;
        addItemType(TYPE_LEVEL_0, R.layout.invite_list_item_zero);
        addItemType(TYPE_LEVEL_1,R.layout.invite_list_item_one);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {

        //判断当前项的类型
        switch (helper.getItemViewType()) {
            case 0://一级列表
                final LevelZero lv0 = (LevelZero) item;
                helper.setText(R.id.Lv0_tv, lv0.departmentName);
                //这个才是列表项的真正位置
                currentData=lv0;
                //设置监听事件，点击选择列表下的所有员工
                final RoundedImageView selectItem=helper.getView(R.id.choose_one_riv);
                if(isSelectDepartmentMap.containsKey(lv0.departmentId)==false){
                    selectItem.setImageResource(R.color.white);
                }else{
                    selectItem.setImageResource(R.drawable.check_001);
                }
                selectItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int poss=levelData.indexOf(lv0);
                        Tpos = poss;
                        if (lv0.isExpanded()) {
                            if (isSelectDepartmentMap.containsKey(lv0.departmentId)==false){
                                isSelectDepartmentMap.put(lv0.departmentId,lv0.departmentId);
                                LevelZero temp=( LevelZero) levelData.get(Tpos);
                                List<LevelOne>list=temp.getSubItems();
                                for(LevelOne subitem:list){
                                    final String eId=subitem.eId;
                                    if(NewInviteActivity.selectedEmployees.containsKey(eId)==false){
                                        NewInviteActivity.selectedEmployees.put(eId,subitem);
                                    }
                                }

                                notifyDataSetChanged();
                                selectItem.setImageResource(R.drawable.check_001);
                            }else{
                                LevelZero temp=( LevelZero) levelData.get(Tpos);
                                List<LevelOne>list=temp.getSubItems();
                                for(LevelOne subitem:list){
                                    final String eId=subitem.eId;
                                    if(NewInviteActivity.selectedEmployees.containsKey(eId)==true){
                                        NewInviteActivity.selectedEmployees.remove(eId);
                                    }
                                }
                                selectItem.setImageResource(R.color.white);
                                isSelectDepartmentMap.remove(lv0.departmentId);
                                notifyDataSetChanged();
                            }
                        }
                        else{
                            activity.subThreadToast("请先展开该部门！");
                        }

                }
                });
                //设置监听事件，点击收缩或者展开。
                helper.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final int poss=levelData.indexOf(lv0);
                        int pos = poss;
                        if (lv0.isExpanded()) {
                            //设置图片的变化
                            helper.setImageResource(R.id.list_tr,R.drawable.down_tri001);
                            collapse(pos);
                        } else {
                           Tpos=poss;
                            helper.setImageResource(R.id.list_tr,R.drawable.right_tri001);
                            //判断部门是否被加载过
                            if(isLoadDepartmentMap.containsKey(lv0.departmentId)==true)
                            expand(pos);
                            else{
                                checkAll=true;
                                new DeptServiceImp(MulitemAdapter.this).getAllEmployeeByDepartmentId(lv0.departmentId);
                            }
                        }
                    }
                });
                break;
            case 1://二级列表
                final LevelOne lv1 = (LevelOne) item;
                helper.setText(R.id.Lv1_tv,lv1.name);
                helper.setText(R.id.invite_job_tv,lv1.eJob);
                final String eId=lv1.eId;
                //通过map的里存的值判断是否被选中，以此可以保存被收起来的项的状态
                if(NewInviteActivity.selectedEmployees.containsKey(eId)==false){
                    helper.setImageResource(R.id.choose_riv,R.color.white);
                }
                else{
                    helper.setImageResource(R.id.choose_riv,R.drawable.check_001);
                }
                //设置头像
                ImageView headView=helper.getView(R.id.invite_head);
                //设置glide加载的选项
                RequestOptions requestOptions=new RequestOptions()
                        .placeholder(R.drawable.touxiang)
                        .error(R.drawable.touxiang);
                Glide
                        .with(activity)
                        .load(lv1.getHeadURL())
                        .apply(requestOptions)
                        .into(headView);
                //点击相应的项，向map插入数据，并且改变（是否选中）的图片
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                           if(NewInviteActivity.selectedEmployees.containsKey(eId)==false){
                               helper.setImageResource(R.id.choose_riv,R.drawable.check_001);
                               NewInviteActivity.selectedEmployees.put(eId,lv1);
                           }
                           else{
                               helper.setImageResource(R.id.choose_riv,R.color.white);
                               NewInviteActivity.selectedEmployees.remove(eId);
                           }
                    }
                 });

                break;
        }
    }
    public void leveloneCallBack(List<LevelOne> list){

        //更新对应部门列表的数据
       final LevelZero temp=( LevelZero) levelData.get(Tpos);
        temp.setSubItems(list);
        isLoadDepartmentMap.put(temp.departmentId,temp.departmentId);
         activity.runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 expand(Tpos);
//                 if(checkAll==true){
//                         checkAll=false;
//                     if (isSelectDepartmentMap.containsKey(temp.departmentId)==false){
//                         isSelectDepartmentMap.put(temp.departmentId,temp.departmentId);
//                         LevelZero temp=( LevelZero) levelData.get(Tpos);
//                         List<LevelOne>list=temp.getSubItems();
//                         for(LevelOne subitem:list){
//                             final String eId=subitem.eId;
//                             if(NewInviteActivity.selectedEmployees.containsKey(eId)==false){
//                                 NewInviteActivity.selectedEmployees.put(eId,subitem);
//                             }
//                         }
//
//                         notifyDataSetChanged();
//                         imageView.setImageResource(R.drawable.gou1);
//                     }else{
//                         LevelZero temp=( LevelZero) levelData.get(Tpos);
//                         List<LevelOne>list=temp.getSubItems();
//                         for(LevelOne subitem:list){
//                             final String eId=subitem.eId;
//                             if(NewInviteActivity.selectedEmployees.containsKey(eId)==true){
//                                 NewInviteActivity.selectedEmployees.remove(eId);
//                             }
//                         }
//                         imageView.setImageResource(R.drawable.empty_circle001);
//                         isSelectDepartmentMap.remove(temp.departmentId);
//                         notifyDataSetChanged();
//                     }
//                 }
             }
         });
    }
}
