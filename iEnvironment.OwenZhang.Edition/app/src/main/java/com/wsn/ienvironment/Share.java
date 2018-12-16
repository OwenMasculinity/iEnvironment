package com.wsn.ienvironment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.wsn.ienvironment.util.Utils;

/**
 * author：张凌霄
 * “关于我们”界面
 */
public class Share extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        TextView version=(TextView)findViewById(R.id.version);
        version.setText("版本号："+"v " + Utils.getVersion(this));  //获取版本信息
    }

}
