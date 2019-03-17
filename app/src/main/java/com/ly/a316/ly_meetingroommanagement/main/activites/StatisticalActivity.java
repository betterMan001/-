package com.ly.a316.ly_meetingroommanagement.main.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.coder.circlebar.CircleBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.main.models.ScheduleModel;
import com.ly.a316.ly_meetingroommanagement.main.services.imp.StatisticalScheduleServiceImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticalActivity extends BaseActivity implements OnChartValueSelectedListener {

    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
    @BindView(R.id.static_day)
    TextView staticDay;
    @BindView(R.id.static_week)
    TextView staticWeek;
    @BindView(R.id.static_month)
    TextView staticMonth;
    @BindView(R.id.circle_bar)
    CircleBar circleBar;
    @BindView(R.id.process_bar_static)
    TextView processBarStatic;
    @BindView(R.id.best_day)
    TextView bestDay;
    private ScheduleModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_statistical);
        ButterKnife.bind(this);
        //从后台获取日程的数据
        new StatisticalScheduleServiceImp(this).statistics(MyApplication.getId());
    }

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StatisticalActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        //1.折线图
        initlineChart();
        //2.柱状图
        initBarChart();
        //3.圆形进度条
        initCircleBar();
    }

    private void initCircleBar() {
        //circleBar.setProgressNum(300);
        float done = (float) (new Integer(model.getThisWeekDoneScheduleNumber()));
        float unfinish = (float) (new Integer(model.getThisWeekCreateScheduleNumber()));
        int rate = (int) (done / unfinish * 100);
        circleBar.setMaxstepnumber(100);
        circleBar.setColor(getResources().getColor(R.color.line_pink));
        circleBar.update(rate, 3000);
        processBarStatic.setText(new Integer(rate).toString() + "%");

    }

    private void initBarChart() {
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        //设置最大实体数目
        barChart.setMaxVisibleValueCount(20);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);

        //设置x轴的位置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        //隐藏垂直的网格
        barChart.getAxisLeft().setDrawGridLines(false);
        //隐藏水平的宫格
        barChart.getAxisRight().setDrawGridLines(false);
        //隐藏右边的y坐标轴
        barChart.getAxisRight().setDrawAxisLine(false);
        //隐藏坐标轴上的数字
        barChart.getAxisRight().setDrawLabels(false);
        //调整Y坐标轴的0的位置
        barChart.getAxisLeft().setAxisMinimum(0f);
        //自定义x轴数据
        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return weeks[(int) value];
            }
        });
//        barChart.getAxisRight().setDrawTopYLabelEntry();
//        barChart.getAxisRight().setDrawZeroLine();
//        barChart.getAxisRight().setCenterAxisLabels();

//        barChart.getAxisRight().setEnabled();
//        barChart.getAxisRight().setGranularityEnabled();
//        barChart.getAxisRight().setDrawLimitLinesBehindData();
//        barChart.getAxisRight().set
        // 添加y轴动画
        barChart.animateY(1500);
        barChart.getLegend().setEnabled(false);
        //设置柱状图数据
        makeBarData();
    }

    private void initlineChart() {
        //1.折线图
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawBorders(false);

        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        //设置x轴在底部显示
        lineChart.getXAxis().setDrawAxisLine(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.setTouchEnabled(false);
        //设置折线动画
        lineChart.animateXY(2000, 2000);
        //设置线的数据集合
        lineChart.setData(makelineChartData(model.getThisWeekDoScheduleList()));
        //刷新视图
        lineChart.invalidate();
    }

    public LineData makelineChartData(String dataList) {
        //设置数据，给坐标点
        List<Entry> entries = new ArrayList<Entry>();
        //解析从接口拿到的数据
        String stringList = dataList;
        String[] weekScheduleList = stringList.split(",");
        //这里初始化的是最近一周的数据
        for (int i = 1; i <= 7; i++) {
            int val = 0;
            if (i <= weekScheduleList.length)
                val = new Integer(weekScheduleList[i - 1]);
            entries.add(new Entry(i, val));
        }

        //这就是这条线的坐标集合，可以加多条线
        LineDataSet dataSet = new LineDataSet(entries, "完成情况");
        //设置线的样式
        dataSet.setColor(getResources().getColor(R.color.line_pink));
        dataSet.setCircleColor(getResources().getColor(R.color.line_pink));
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        //LineDataSet.Mode.CUBIC_BEZIER
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        // draw points as solid circles
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
        dataSet.setFillDrawable(drawable);
        // dataSet.setFillColor(getResources().getColor(R.color.pink_background));
        //吧线的集合放在图标中
        LineData lineData = new LineData(dataSet);
        return lineData;
    }

    public void makeBarData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        //解析从接口拿到的数据
        String stringList = model.getThisWeekDoScheduleList();
        String[] weekScheduleList = stringList.split(",");
        //如果是当前周的前几天后几天的数据要自己填
        for (int i = 0; i < 7; i++) {
            int val = 0;
            if (i < weekScheduleList.length) {
                val = new Integer(weekScheduleList[i]);
            }
            values.add(new BarEntry(i, val));
        }
        BarDataSet set1;
        set1 = new BarDataSet(values, "Data Set");
        set1.setColor(getResources().getColor(R.color.classical_blue));
        //设置是否在柱状图上方显示数据
        set1.setDrawValues(true);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
        //计算最佳工作日
        int max = 0;
        int pos = 0;
        for (int i = 0; i < 7; i++) {
            int temp = 0;
            if (i < weekScheduleList.length)
                temp = new Integer(weekScheduleList[i]);
            if (temp > max) {
                max = temp;
                pos = i;
            }
        }
        String day = "";
        switch (pos) {
            case 0:
                day = "周一";
                break;
            case 1:
                day = "周二";
                break;
            case 2:
                day = "周三";
                break;
            case 3:
                day = "周四";
                break;
            case 4:
                day = "周五";
                break;
            case 5:
                day = "周六";
                break;
            case 6:
                day = "周日";
                break;

        }
        bestDay.setText("本周最佳工作日： " + day);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @OnClick({R.id.static_day, R.id.static_week, R.id.static_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.static_day:
                staticDay.setTextColor(getResources().getColor(R.color.classical_blue));
                staticMonth.setTextColor(getResources().getColor(R.color.black));
                staticWeek.setTextColor(getResources().getColor(R.color.black));
                lineChart.setData(makelineChartData(model.getThisWeekDoScheduleList()));
                lineChart.invalidate();
                lineChart.animateXY(2000, 2000);
                break;
            case R.id.static_week:
                staticDay.setTextColor(getResources().getColor(R.color.black));
                staticMonth.setTextColor(getResources().getColor(R.color.black));
                staticWeek.setTextColor(getResources().getColor(R.color.classical_blue));

                lineChart.setData(makelineChartData(model.getSevenWeekDoneList()));
                lineChart.invalidate();
                lineChart.animateXY(2000, 2000);
                break;
            case R.id.static_month:
                staticDay.setTextColor(getResources().getColor(R.color.black));
                staticMonth.setTextColor(getResources().getColor(R.color.classical_blue));
                staticWeek.setTextColor(getResources().getColor(R.color.black));
                lineChart.setData(makelineChartData(model.getSevenMonthDoneList()));
                lineChart.invalidate();
                lineChart.animateXY(2000, 2000);
                break;
        }
    }

    public void statisticalCallBack(final ScheduleModel model) {
        this.model = model;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    @OnClick(R.id.back_ll)
    public void onViewClicked() {
        finish();
    }
}
