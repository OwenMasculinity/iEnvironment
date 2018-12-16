package com.wsn.ienvironment;

/*
* author：张凌霄
* 定义图表坐标位置
 */
public class MyChartItem {
    private String x;
    private float y;

    public MyChartItem(String vx, float vy) {
        this.x = vx;
        this.y = vy;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
