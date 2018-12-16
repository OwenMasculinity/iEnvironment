package com.wsn.ienvironment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * author：张凌霄
 * 设置界面
 */
public class Setting extends Activity {
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        final ListView setting = (ListView) findViewById(R.id.setting);
        String[] Settings=new String[]{"清空缓存","意见反馈","推荐给好友","关于我们"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.setting_list_item,Settings);


        setting.setAdapter(adapter);
        setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                switch (position){
                    case 1:break;
                    case 2:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
                        intent.putExtra(Intent.EXTRA_TEXT,
                                "我正在使用 《爱环境》，可以随时随地查看环境和天气信息，是您居家/出差、旅行的贴心助手！感谢您的支持，武汉大学WSN实验室出品。");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Setting.this.startActivity(Intent.createChooser(intent, "好友分享"));break;
                    case 3:
                        intent = new Intent(Setting.this,Share.class);
                        startActivity(intent);break;
                }
            }
        });

    }

  /*  private CornerListView cornerListView = null;

             private List<Map<String,String>> listData = null;
      private SimpleAdapter adapter = null;


             @Override
     protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
            setContentView(R.layout.main_tab_setting);

               cornerListView = (CornerListView)findViewById(R.id.setting_list);
               setListData();

               adapter = new SimpleAdapter(getApplicationContext(), listData, R.layout.main_tab_setting_list_item , new String[]{"text"}, new int[]{R.id.setting_list_item_text});
              cornerListView.setAdapter(adapter);
         }


           private void setListData(){
          listData = new ArrayList<Map<String,String>>();

               Map<String,String> map = new HashMap<String, String>();
              map.put("text", "图库更新");
              listData.add(map);

              map = new HashMap<String, String>();
             map.put("text", "收藏图片");
              listData.add(map);

            map = new HashMap<String, String>();
               map.put("text", "下载目录");
              listData.add(map);
       }*/
 }



