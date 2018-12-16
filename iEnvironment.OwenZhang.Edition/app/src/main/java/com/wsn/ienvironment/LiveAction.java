package com.wsn.ienvironment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wsn.ienvironment.photo.PhotoMain;
import com.wsn.ienvironment.util.Utils;

public class LiveAction extends Fragment {

	private TextView CityText;
	private String city;
	private LinearLayout ChangeCity;
	private Intent intent;
	private SharedPreferences sp;
	private ImageView camera;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.live_action);
		View rootView = inflater.inflate(R.layout.live_action, container, false);
		camera = (ImageView) rootView.findViewById(R.id.camera);
		camera.setOnClickListener(new MyButtonListener());
		/*sp = getActivity().getSharedPreferences("LiveAction", Context.MODE_PRIVATE);

		ChangeCity = (LinearLayout) rootView.findViewById(R.id.change_city_layout);
		CityText = (TextView) rootView.findViewById(R.id.city2);

		ChangeCity.setOnClickListener(new ButtonListener());*/
		//super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.live_action);

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1 && !data.getStringExtra("city").equals(city)) {
			city = data.getStringExtra("city");
			CityText.setText(city);
			if (Utils.checkNetwork(LiveAction.this.getActivity()) == false) {
				Toast.makeText(LiveAction.this.getActivity(), "网络异常,请检查网络设置", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	class MyButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.change_city_layout:
					intent = new Intent();
					intent.setClass(LiveAction.this.getActivity(), SelectCity.class);
					LiveAction.this.startActivityForResult(intent, 101);
					break;
				case R.id.camera:
					intent = new Intent();
					intent.setClass(LiveAction.this.getActivity(), PhotoMain.class);
					LiveAction.this.startActivityForResult(intent,102);
				default:
					break;
			}
		}
	}
}
