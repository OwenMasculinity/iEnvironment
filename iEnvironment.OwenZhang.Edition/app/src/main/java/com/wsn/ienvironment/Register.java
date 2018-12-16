package com.wsn.ienvironment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * author：张凌霄
 * 用户注册界面
 */

public class Register extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button button1=(Button)findViewById(R.id.affirm);
        Button button2=(Button)findViewById(R.id.reset);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nicknameET = (EditText) findViewById(R.id.nickname);    //用户名
                String nickname = nicknameET.getText().toString();

                RadioGroup sex=(RadioGroup)findViewById(R.id.sex);	//性别
                for(int i=0;i<sex.getChildCount();i++){
                    RadioButton r=(RadioButton)sex.getChildAt(i);
                    if(r.isChecked()){
                        String gender=r.getText().toString();
                        Log.i("注册信息", "性别:" + gender);
                        break;
                    }
                }

                EditText pwdET = (EditText) findViewById(R.id.pwd);    //密码
                String pwd = pwdET.getText().toString();
                EditText repwdET = (EditText) findViewById(R.id.repwd);    //确认密码
                String repwd = repwdET.getText().toString();

                EditText emailET = (EditText) findViewById(R.id.email);    //E-mail
                String email = emailET.getText().toString();

                EditText phoneET = (EditText) findViewById(R.id.phone);    //电话
                String phone = phoneET.getText().toString();

                if(!pwd.equals(repwd)) {    //判断两次密码是否一致
                    Toast.makeText(Register.this, "两次输出密码不一致，请重新输入", Toast.LENGTH_LONG).show();
                    ((EditText) findViewById(R.id.pwd)).setText("");    //清空密码编辑框
                    ((EditText) findViewById(R.id.repwd)).setText("");    //清空确认密码编辑框
                    ((EditText) findViewById(R.id.pwd)).requestFocus();    //让密码编辑框获得焦点
                }

                 Log.i("注册信息", "会员昵称:" + nickname);

                 Log.i("注册信息","密码:"+pwd);
                 Log.i("注册信息","E-mail地址ַ:"+email);
                 Log.i("注册信息","电话号码ַ:"+phone);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.nickname)).setText("");
                ((EditText) findViewById(R.id.email)).setText("");
                ((EditText) findViewById(R.id.phone)).setText("");
                ((EditText) findViewById(R.id.pwd)).setText("");    //清空密码编辑框
                ((EditText) findViewById(R.id.repwd)).setText("");    //清空确认密码编辑框
                ((EditText) findViewById(R.id.nickname)).requestFocus();    //让用户名编辑框获得焦点
            }

        });
    }
}
