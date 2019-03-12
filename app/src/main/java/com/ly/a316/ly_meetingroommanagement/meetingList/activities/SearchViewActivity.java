package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.SearchForMeetingAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.MeetingDetailServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetingList.utils.MySQLiteOpenHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchViewActivity extends BaseActivity {


    @BindView(R.id.search_recycle)
    RecyclerView searchRecycle;
    @BindView(R.id.my_searchview)
    SearchView mSearchView;
    //数据库变量
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private SearchForMeetingAdapter adapter;
    public static String mId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_search_view);
        ButterKnife.bind(this);
        //将现在的数据插入数据库
        initDataForDataBase();
        initView();
    }

    private void initDataForDataBase() {
        //获取helper
        helper = new MySQLiteOpenHelper(this, "meeting.db", null, 1);
        //先把上次的记录删除了
        deleteData();
        //插入这次的记录
        wholeInsertData();
    }

    private void wholeInsertData() {
        int length = MeetingListActivity.meetingList.size();
        for (int i = 0; i < length; i++) {
            Meeting meeting = MeetingListActivity.meetingList.get(i);
            insertData(meeting.getmId(), meeting.getName());
        }
    }

    private void insertData(String mId, String name) {
        db = helper.getWritableDatabase();//打开数据库进行读写操作
        db.execSQL("insert into meetingList(mId,name) values('" + mId + "','" + name + "')");
        db.close();
    }

    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from meetingList");
        /**query()、rawQuery()	查询数据库
         * insert()	插入数据
         delete()	删除数据
         *execSQL()	可进行增删改操作, 不能进行查询操作
         */
        db.close();
    }

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SearchViewActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        //初始化搜索框样式
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */

        mSearchView.setIconifiedByDefault(true);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
        mSearchView.setSubmitButtonEnabled(false);
        /**
         * 初始是否已经是展开的状态
         * 写上此句后searchView初始展开的，也就是是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能展开出现输入框
         */
        //设置字体大小
        TextView txt_search = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//设置字体大小为14sp
        txt_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
        mSearchView.onActionViewExpanded();
        // 设置search view的背景色
        mSearchView.setBackgroundColor(getResources().getColor(R.color.white));
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setQueryHint("请输入要搜索的内容");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //提交时上搜索
            @Override
            public boolean onQueryTextSubmit(String name) {
                queraData(name);
                return false;
            }

            //当文字改变时搜索
            @Override
            public boolean onQueryTextChange(String name) {
                queraData(name);
                return false;
            }
        });
    }


    //从数据库中模糊查找出数据
    private void queraData(String name) {
        db = helper.getReadableDatabase();
        boolean d = hasData(name);
        //如果有则查出，并且存下来
        if (d) {
            String sql = "select name,mId from meetingList where name like '%" + name + "%' order by id desc ";
            Log.d("quera:", sql);
            Cursor cursor = db.rawQuery(sql, null);
            //cursor是一个结果集的光标，它随机指向一个位置
            cursor.moveToFirst();
            MeetingListActivity.searchMeetingList = new ArrayList<>();
            do {
                String tempName = cursor.getString(cursor.getColumnIndex("name"));
                String mId = cursor.getString(cursor.getColumnIndex("mId"));
                Meeting temp = new Meeting();
                temp.setName(tempName);
                temp.setmId(mId);
                MeetingListActivity.searchMeetingList.add(temp);
            } while (cursor.moveToNext());
            adapter = new SearchForMeetingAdapter(SearchViewActivity.this, MeetingListActivity.searchMeetingList);
            /*
            *1.判断是否有精确搜索的结果
            *2.默认会议名称不会重复
            *3.用会议id来获得新的数据
            */
            String mId="";
            if(MeetingListActivity.searchMeetingList.size()!=0){
                for(Meeting meeting:MeetingListActivity.searchMeetingList){
                    if(name.equals(meeting.getName())==true){
                        mId=meeting.getmId();
                        break;
                    }
                }
            }
            //为了保证异步访问的顺序，要做判断来初始化视图
            //如果没有精确搜索
            if("".equals(mId)){
                initRecycleView(0,null);
            }else{
                SearchViewActivity.mId=mId;
                //从后台获取会议的数据
                new MeetingDetailServiceImp(this,"2").meetDetail(mId);
            }

        }
        db.close();
    }
    private void initRecycleView(int count,MeetingDetailModel model){
        searchRecycle.setLayoutManager(new LinearLayoutManager(SearchViewActivity.this));
        searchRecycle.setAdapter(adapter);
        adapter.setOnclickListener(new SearchForMeetingAdapter.OnclickListener() {
            @Override
            public void OnClick(String mId) {
                //将选中会议的id传给MeetingListActivity让它重新显示
                MeetingDetailActivity.start(SearchViewActivity.this, mId,MeetingListActivity.duration);
            }
        });
        adapter.setAccurateSearch(model);
        adapter.setCount(count);
        adapter.notifyDataSetChanged();
    }
    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = db.rawQuery("select id as _id,name from meetingList where name like '%" + tempName + "%' order by id desc ", null);
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    @OnClick(R.id.back_search)
    public void onViewClicked() {
        finish();
    }
    //精确搜索的数据回调
    public void meetingDetailCallBack(final MeetingDetailModel model){
        //获取数据然后显示
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecycleView(1,model);
            }
        });
    }
}
