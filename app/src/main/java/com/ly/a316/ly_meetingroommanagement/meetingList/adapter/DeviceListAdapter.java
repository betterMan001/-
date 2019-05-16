package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.DeviceListActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Device;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.DeviceServiceImp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/*
Date:2019/5/13
Time:18:26
auther:xwd
*/
public class DeviceListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Device> list;
    //判断开关的状态
    private List<Boolean> isSwitchList=new ArrayList<>();
    private DeviceListViewHolder tempholder=null;
    String CONTROL_DEVICE_FAIL="连接设备失败，无法控制设备！";
    //当前位置
   private int pos=0;
    public DeviceListAdapter(Context context, List<Device> list) {
        this.context = context;
        this.list = list;
        int length=list.size();
        for(int i=0;i<length;i++){
            isSwitchList.add(false);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.device_list_item,parent,false);
        return new DeviceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
     DeviceListViewHolder holder=(DeviceListViewHolder) viewHolder;
     Device model=list.get(i);
     holder.device_name.setText(model.getdName());
     holder.device_status.setText(model.getdState());
     RoundedImageView headView=holder.choose_riv;
     //加载头像图片
     RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.printer)
                .error(R.drawable.printer);
     Glide
               .with(context)
               .load(model.getdType())
               .apply(requestOptions)
               .into(headView);
     holder.switch_niu.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             Boolean isSwitch=isSwitchList.get(i);
             pos=i;
             //获取当前项的Holder
             tempholder=holder;
             if(isSwitch == true){
                 //关灯
                 new DeviceServiceImp(DeviceListAdapter.this).kai_close(model.getdId());
             }else{
                 //开灯
                 new DeviceServiceImp(DeviceListAdapter.this).kai_led(model.getdId());

             }
         }
     });
    }
    public class DeviceListViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout device_list_item_ll;
        public TextView device_name;
        public TextView device_status;
        public RoundedImageView choose_riv;
        public ImageView device_fix;
        public ImageView switch_niu;
        //对应select_item中单项
        public DeviceListViewHolder(View itemView) {
            super(itemView);
            device_list_item_ll=itemView.findViewById(R.id.device_list_item_ll);
            device_name=itemView.findViewById(R.id.device_name);
            device_status=itemView.findViewById(R.id.device_status);
            choose_riv=itemView.findViewById(R.id.choose_riv);
            device_fix=itemView.findViewById(R.id.device_fix);
            switch_niu=itemView.findViewById(R.id.switch_niu);
        }

    }
    public void deviceSwitchOnCallBack(final String result){
        DeviceListActivity activity= (DeviceListActivity)context;
        Boolean isSwitch=isSwitchList.get(pos);
        isSwitch = ("1".equals(result))?true:false;
        isSwitchList.set(pos,isSwitch);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("1".equals(result)){
                    tempholder.switch_niu.setImageResource(R.drawable.switch_yes);
                }else{
                  activity.subThreadToast(CONTROL_DEVICE_FAIL);
                }
            }
        });

    }
    public void deviceSwitchOFFCallBack(final String result){
        DeviceListActivity activity= (DeviceListActivity)context;
        Boolean isSwitch=isSwitchList.get(pos);

        isSwitch = ("1".equals(result))?false:true;
        isSwitchList.set(pos,isSwitch);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("1".equals(result)){
                    tempholder.switch_niu.setImageResource(R.drawable.switch_no);
                }else{
                    activity.subThreadToast(CONTROL_DEVICE_FAIL);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
