package com.ly.a316.ly_meetingroommanagement.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ly.a316.ly_meetingroommanagement.customView.kankan.wheel.widget.DateObject;

import com.ly.a316.ly_meetingroommanagement.customView.kankan.wheel.widget.OnWheelChangedListener;
import com.ly.a316.ly_meetingroommanagement.customView.kankan.wheel.widget.StringWheelAdapter;
import com.ly.a316.ly_meetingroommanagement.customView.kankan.wheel.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 作者：余智强
 * 2018/12/21
 */
public class DatePicker extends LinearLayout {

    private Calendar calendar = Calendar.getInstance();
    private WheelView newDays;
    private ArrayList<DateObject> dateList;
    private OnChangeListener onChangeListener; //onChangeListener
    private final int MARGIN_RIGHT = 10;     //调整文字右端距离
    private DateObject dateObject;      //日期数据对象
    int wek=1;

    int year,month, day , hour,miniute;
    //Constructors
    public DatePicker(Context context) {
        super(context);
        init(context);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public DatePicker(Context context,int year,int month,int day ,int hour,int miniute) {
        super(context);
        initt(context,year,month,day,hour,miniute);
    }
    private void initt(Context context,int yearr,int monthr,int dayr ,int hourr,int miniuter) {
        int year = yearr;
        int month = monthr;
        int day =dayr;
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        dateList = new ArrayList<DateObject>();

        for (int i = 0; i < 13; i++) {
            //通过年月数算天数
            Calendar a = Calendar.getInstance();
            if (month + i >= 13) {
                a.set(Calendar.YEAR, year+1);
                a.set(Calendar.MONTH, i-1);
            } else {
                a.set(Calendar.MONTH, month + i);
                a.set(Calendar.YEAR, year);
            }
            a.set(Calendar.DATE, 1);
            a.roll(Calendar.DATE, -1);
            int maxDate = a.get(Calendar.DAY_OF_MONTH);

            for (int j = 0; j < maxDate; j++) {
                if(month == month+i){
                    if(day+j >maxDate){
                        continue;
                    }
                    wek=(week+j) % 7 == 0 ? 7 :(week+j)% 7;
                    dateObject = new DateObject(year, month, day + j, wek, month );
                }else{
                    wek = wek+1;
                    int wekw=(wek) % 7 == 0 ? 7 :(wek)% 7;
                    dateObject = new DateObject(year, month + i, j+1, wekw, month);
                }
                dateList.add(dateObject);
            }
        }


        newDays = new WheelView(context);
        LayoutParams newDays_param = new LayoutParams(800, LayoutParams.WRAP_CONTENT);//设置显示日期的长度
        newDays_param.setMargins(0, 0, 0, 0);
        newDays.setLayoutParams(newDays_param);
        newDays.setAdapter(new StringWheelAdapter(dateList, 10));
        newDays.setVisibleItems(3);
        newDays.setCyclic(true);

        newDays.addChangingListener(onDaysChangedListener);
        addView(newDays);
    }
    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        dateList = new ArrayList<DateObject>();

        for (int i = 0; i < 13; i++) {
            //通过年月数算天数
            Calendar a = Calendar.getInstance();
            if (month + i >= 13) {
                a.set(Calendar.YEAR, year+1);
                a.set(Calendar.MONTH, i-1);
            } else {
                a.set(Calendar.MONTH, month + i);
                a.set(Calendar.YEAR, year);
            }
            a.set(Calendar.DATE, 1);
            a.roll(Calendar.DATE, -1);
            int maxDate = a.get(Calendar.DAY_OF_MONTH);

            for (int j = 0; j < maxDate; j++) {
                if(month == month+i){
                    if(day+j >maxDate){
                        continue;
                    }
                    wek=(week+j) % 7 == 0 ? 7 :(week+j)% 7;
                    dateObject = new DateObject(year, month, day + j, wek, month );
                }else{
                    wek = wek+1;
                    int wekw=(wek) % 7 == 0 ? 7 :(wek)% 7;
                    dateObject = new DateObject(year, month + i, j+1, wekw, month);
                }
                dateList.add(dateObject);
            }
        }


        newDays = new WheelView(context);
        LayoutParams newDays_param = new LayoutParams(800, LayoutParams.WRAP_CONTENT);//设置显示日期的长度
        newDays_param.setMargins(0, 0, 0, 0);
        newDays.setLayoutParams(newDays_param);
        newDays.setAdapter(new StringWheelAdapter(dateList, 10));
        newDays.setVisibleItems(3);
        newDays.setCyclic(true);

        newDays.addChangingListener(onDaysChangedListener);
        addView(newDays);
    }

    /**
     * 滑动改变监听器
     */
    private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView mins, int oldValue, int newValue) {
            calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
            change();
        }
    };

    /**
     * 滑动改变监听器回调的接口
     */
    public interface OnChangeListener {
        void onChange(int year, int month, int day, int day_of_week);
    }

    /**
     * 设置滑动改变监听器
     *
     * @param onChangeListener
     */
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 滑动最终调用的方法
     */
    private void change() {
        if (onChangeListener != null) {
            onChangeListener.onChange(
                    dateList.get(newDays.getCurrentItem()).getYear(),
                    dateList.get(newDays.getCurrentItem()).getMonth(),
                    dateList.get(newDays.getCurrentItem()).getDay(),
                    dateList.get(newDays.getCurrentItem()).getWeek());
        }
    }


    /**
     * 根据day_of_week得到汉字星期
     *
     * @return
     */
    public static String getDayOfWeekCN(int day_of_week) {
        String result = null;
        switch (day_of_week) {
            case 1:
                result = "星期日";
                break;
            case 2:
                result = "星期一";
                break;
            case 3:
                result = "星期二";
                break;
            case 4:
                result = "星期三";
                break;
            case 5:
                result = "星期四";
                break;
            case 6:
                result = "星期五";
                break;
            case 7:
                result = "星期六";
                break;
            default:
                break;
        }
        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
