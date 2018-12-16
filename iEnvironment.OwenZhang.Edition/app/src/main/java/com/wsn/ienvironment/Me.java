package com.wsn.ienvironment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* author：张凌霄
 * 设置“我”界面
 */
public class Me extends Fragment {
	Intent intent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View parentView= inflater.inflate(R.layout.me, container, false);
		RelativeLayout relativeLayout=(RelativeLayout)parentView.findViewById(R.id.register);
		final ListView listview1 = (ListView) parentView.findViewById(R.id.listView1);
		final ListView listview2 = (ListView) parentView.findViewById(R.id.listView2);
		final ListView listview3 = (ListView) parentView.findViewById(R.id.listView3);
		int[] imageId1 = new int[] { R.drawable.sensor, R.drawable.history};
		int[] imageId2 = new int[] {R.drawable.save, R.drawable.download };
		int[] imageId3 = new int[] {  R.drawable.settings, R.drawable.web };
		String[] title1 = new String[] { "手机传感器", "历史天气" };
		String[] title2 = new String[] {  "收藏", "下载" };
		String[] title3 = new String[] {  "设置", "官方网站" };

		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent register_Intent = new Intent(getActivity(),
						Register.class);
				startActivity(register_Intent);
			}
		});

		List<Map<String, Object>> listItems1 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageId1.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageId1[i]);
			map.put("title", title1[i]);
			listItems1.add(map);
		}
		SimpleAdapter adapter1 = new SimpleAdapter(this.getActivity(), listItems1,
				R.layout.items, new String[] { "title", "image" }, new int[] {
				R.id.title, R.id.image });
		listview1.setAdapter(adapter1);
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				switch (position) {
					case 0:
						Intent sensor_intent = new Intent(getActivity(),SensorActivity.class);
						//sensor_intent.setClass(Me.this.getActivity(), SensorActivity.class);
						startActivity(sensor_intent);
						break;

					case 1:
						Intent historyweathor_intent = new Intent();
						historyweathor_intent.setClass(Me.this.getActivity(), Historyweather.class);
						Me.this.startActivityForResult(historyweathor_intent, 100);
						break;
				}
			}
		});

		List<Map<String, Object>> listItems2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageId2.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageId2[i]);
			map.put("title", title2[i]);
			listItems2.add(map);
		}
		SimpleAdapter adapter2 = new SimpleAdapter(this.getActivity(), listItems2,
				R.layout.items, new String[] { "title", "image" }, new int[] {
				R.id.title, R.id.image });
		listview2.setAdapter(adapter2);

		final List<Map<String, Object>> listItems3 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < imageId3.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageId3[i]);
			map.put("title", title3[i]);
			listItems3.add(map);
		}
		SimpleAdapter adapter3 = new SimpleAdapter(this.getActivity(), listItems3,
				R.layout.items, new String[] { "title", "image" }, new int[] {
				R.id.title, R.id.image });
		listview3.setAdapter(adapter3);
		listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				switch (position) {
					case 0:
						Intent setting_intent= new Intent();
						setting_intent.setClass(getActivity(), Setting.class);
						startActivity(setting_intent);break;
					case 1:
					 	intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("http://ienvironment.com.cn"));//连接网站
						startActivity(intent);break;
				}
			}
		});
		return parentView;
	}

}
