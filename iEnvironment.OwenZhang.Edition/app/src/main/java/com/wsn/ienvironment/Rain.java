package com.wsn.ienvironment;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * author：张凌霄
 */
public class Rain extends Activity {
    MyChartView rainforeseechart,raincurrentchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rain);
        //EngineManager.getInstance().setContext(this.getApplicationContext()).setDB(null);

        rainforeseechart=(MyChartView) findViewById(R.id.rain_foresee_chart);
        raincurrentchart= (MyChartView) findViewById(R.id.rain_current_chart);

        rainforeseechart.SetTuView(getrainforeseelist(), "单位: %");
        raincurrentchart.SetTuView(getraincurrentlist(), "单位: 毫米(ms)");

    }

    /*
    * author：张凌霄
    *return 降雨概率预测图表
     */
    ArrayList<MyChartItem> getrainforeseelist(){
        ArrayList<MyChartItem> list = new ArrayList<MyChartItem>();
        list.add(new MyChartItem("昨天", 90f));
        list.add(new MyChartItem("今天", 22.4f));
        list.add(new MyChartItem("明天", 24.7f));
        list.add(new MyChartItem("4/18", 23.5f));
        list.add(new MyChartItem("4/19", 25.5f));
        list.add(new MyChartItem("4/20", -100f));
        return list;
    }


    /*
    * author：张凌霄
    *return 当前降雨量图表
     */
    ArrayList<MyChartItem> getraincurrentlist(){
        ArrayList<MyChartItem> list = new ArrayList<MyChartItem>();
        list.add(new MyChartItem("昨天", 23.2f));
        list.add(new MyChartItem("今天", 22.4f));
        list.add(new MyChartItem("明天", 24.7f));
        list.add(new MyChartItem("4/18", 23.5f));
        list.add(new MyChartItem("4/20", 25.5f));
        return list;
    }
}
