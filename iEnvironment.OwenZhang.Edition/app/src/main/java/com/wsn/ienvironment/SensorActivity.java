package com.wsn.ienvironment;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by 张凌霄 on 2016/7/16.
 * 传感器界面
 */

public class SensorActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private float[] accSensorValue = new float[3];
    private float[] magSensorValue = new float[3];
    TextView tvAcce, tvOrientation, tvMagnetic, tvTemp, tvLight,
            tvGravity, tvGyroscope;
    String[] SensorInfo = new String[] { "", "", "", "", "" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sensor);
        tvAcce = (TextView) findViewById(R.id.tvAcce);
        tvOrientation = (TextView) findViewById(R.id.tvOrientation);
        tvMagnetic = (TextView) findViewById(R.id.tvMagnetic);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvLight = (TextView) findViewById(R.id.tvLight);
        tvGravity = (TextView) findViewById(R.id.tvGravity);
        tvGyroscope = (TextView) findViewById(R.id.tvGyroscope);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        initSensor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * author：张凌霄
    *初始化传感器数据
    */
    private void initSensor() {
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
                SensorManager.SENSOR_DELAY_UI);

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_UI);

    }
     /*
     * author：张凌霄
     *传感器信息发生变化时调用
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        int sensorType = event.sensor.getType();
        StringBuilder sb = null;
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accSensorValue = values;
                sb = new StringBuilder();
                sb.append("X方向上的加速度：");
                sb.append(values[0]);
                sb.append("\nY方向的加速度：");
                sb.append(values[1]);
                sb.append("\nZ方向的加速度：");
                sb.append(values[2]);
                tvAcce.setText(sb.toString());
                SensorInfo[0] = "0" + "#" + values[0] + "#" + values[1] + "#"
                        + values[2];

                float R[] = new float[9];
                float a[] = new float[3];
                sensorManager.getRotationMatrix(R, null, accSensorValue,
                        magSensorValue);
                sensorManager.getOrientation(R, a);
                a[0] = (float) Math.toDegrees(a[0]);
                a[1] = (float) Math.toDegrees(a[1]);
                a[2] = (float) Math.toDegrees(a[2]);
                sb = new StringBuilder();
                sb.append("绕Z轴转过的角度：");
                sb.append(a[0]);
                sb.append("\n绕Y轴转过的角度：");
                sb.append(a[1]);
                sb.append("\n绕X轴转过的角度：");
                sb.append(a[2]);
                tvOrientation.setText(sb.toString());
                SensorInfo[1] = "1" + "#" + a[0] + "#" + a[1] + "#" + a[2];
                break;

            case Sensor.TYPE_GYROSCOPE:
                sb = new StringBuilder();
                sb.append("沿X轴旋转的角速度：");
                sb.append(values[0]);
                sb.append("\n沿Y轴旋转的角速度：");
                sb.append(values[1]);
                sb.append("\n沿Z轴旋转的角速度：");
                sb.append(values[2]);
                tvGyroscope.setText(sb.toString());
                SensorInfo[2] = "2" + "#" + values[0] + "#" + values[1] + "#"
                        + values[2];
                break;
            case Sensor.TYPE_GRAVITY:
                sb = new StringBuilder();
                sb.append("沿X轴方向的重力：");
                sb.append(values[0]);
                sb.append("\n沿y轴方向的重力：");
                sb.append(values[1]);
                sb.append("\n沿z轴方向的重力：");
                sb.append(values[2]);
                tvGravity.setText(sb.toString());
                SensorInfo[3] = "3" + "#" + values[0] + "#" + values[1] + "#"
                        + values[2];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magSensorValue = values;
                sb = new StringBuilder();
                sb.append("X方向上的磁场强度：");
                sb.append(values[0]);
                sb.append("\nY方向上的磁场强度：");
                sb.append(values[1]);
                sb.append("\nZ方向上的磁场强度：");
                sb.append(values[2]);
                tvMagnetic.setText(sb.toString());
                SensorInfo[4] = "4" + "#" + values[0] + "#" + values[1] + "#"
                        + values[2];
                break;
            case Sensor.TYPE_TEMPERATURE:
                sb = new StringBuilder();
                sb.append("当前温度为：");
                sb.append(values[0]+"℃");
                tvTemp.setText(sb.toString());
                break;
            case Sensor.TYPE_LIGHT:
                sb = new StringBuilder();
                sb.append("当前光强为：");
                sb.append(values[0] + "lux(勒克斯)");
                tvLight.setText(sb.toString());
                break;
        }
    }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
}
