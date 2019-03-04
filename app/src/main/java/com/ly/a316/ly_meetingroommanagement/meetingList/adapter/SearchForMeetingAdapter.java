package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingListActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SearchViewActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


/*
Date:2019/2/26
Time:20:55
auther:xwd
*/
public class SearchForMeetingAdapter extends RecyclerView.Adapter {
    private Context context;
    List<Meeting> list;
    //精确搜索视图的数量
    private int count=0;
    //视图类型
    private  final int itemType1=1;
    private  final int itemType2=2;
    private MeetingDetailModel accurateSearch;

    public MeetingDetailModel getAccurateSearch() {
        return accurateSearch;
    }

    public void setAccurateSearch(MeetingDetailModel accurateSearch) {
        this.accurateSearch = accurateSearch;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public SearchForMeetingAdapter(Context context, List<Meeting> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return (count==1&&position==0)?itemType1:itemType2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch(viewType){
            case itemType1:
                view=LayoutInflater.from(context).inflate(R.layout.item_accurate_search_meetinglist,viewGroup,false);;
                viewHolder=new AccurateViewHolder(view);
                break;
            case itemType2:
                view = LayoutInflater.from(context).inflate(R.layout.item_search_meetinglist,viewGroup,false);
                viewHolder=new MyviewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        //精确搜索的视图
        if(count==1&&position==0){
           AccurateViewHolder holder=(AccurateViewHolder) viewHolder;
           holder.accurate_meetiing_name.setText(accurateSearch.getTitle());
           holder.accurate_meetiing_content.setText(accurateSearch.getContent());
           final String mId=SearchViewActivity.mId;
           holder.ll.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onclickListener.OnClick(mId);
               }
           });
        }else{
            Meeting cursor=list.get(position-count);
            ((MyviewHolder)viewHolder).textView.setText(cursor.getName());
            final String mId=cursor.getmId();
            ((MyviewHolder)viewHolder).ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickListener.OnClick(mId);
                }
            });
        }

    }
    private class MyviewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout ll;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.meeting_name);
            ll=itemView.findViewById(R.id.search_for_meeting_ll);
        }

    }
    private class AccurateViewHolder extends RecyclerView.ViewHolder{
      LinearLayout ll;
      RoundedImageView imageView;
      TextView accurate_meetiing_name;
      TextView accurate_meetiing_content;
        public AccurateViewHolder(@NonNull View view) {
            super(view);
            ll=view.findViewById(R.id.accurate_search_ll);
            imageView=view.findViewById(R.id.head_rv);
            accurate_meetiing_name=view.findViewById(R.id.accurate_meetiing_name);
            accurate_meetiing_content=view.findViewById(R.id.accurate_meetiing_content);

        }
    }
    public interface OnclickListener{
        void OnClick(String mId);
    }
    OnclickListener onclickListener;
    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }
    @Override
    public int getItemCount() {
        //就当名字不重复好了
        return count+list.size();
    }
}
