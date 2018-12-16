package com.wsn.ienvironment;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.wsn.ienvironment.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 邱子濛
 */

@SuppressLint("SimpleDateFormat")
public class Weather extends Fragment {

	private JSONArray jArray;
	private String result = null;
	private InputStream is = null;
	private StringBuilder sb = null;
	final private String DATE_KEY[] = { "date_0", "date_1", "date_2", "date_3" };
	final private String WEATHER_KEY[] = { "weather_0", "weather_1",
			"weather_2", "weather_3" };
	final private String WIND_KEY[] = { "wind_0", "wind_1", "wind_2", "wind_3" };
	final private String TEMPERATURE_KEY[] = { "temperature_0",
			"temperature_1", "temperature_2", "temperature_3" };
	public static Handler handler;
	public static Weather context;
	private String[] dateArray = new String[]{"--", "--", "--", "--"};
	private String[] weatherArray = new String[]{"--", "--", "--", "--"};
	private String[] windArray = new String[]{"--", "--", "--", "--"};
	private String[] temperatureArray = new String[]{"--", "--", "--", "--"};
	private SharedPreferences sp;
	private LinearLayout weatherBg;
	private LinearLayout titleBarLayout;
	private LinearLayout changeCity;
	private TextView cityText;
	private ImageView share;
	private ImageView about;
	private static ImageView refresh;
	private static ProgressBar refreshing;
	private TextView updateTimeText;
	private ScrollView scrollView;
	private LinearLayout currentWeatherLayout;
	private ImageView weatherIcon;
	private TextView currentTemperatureText;
	private TextView currentWeatherText;
	private TextView temperatureText;
	private TextView windText;
	private TextView dateText;
	private ListView weatherForecastList;
	private Intent intent;
	private Time time;
	private static Runnable run;
	private Builder builder;
	private String currentWeekDay;
	private static String city;
	private String currentTemperature;
	private int index = 0;
	private long currentTime = System.currentTimeMillis() + (1000 * 60 * 10);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View parentView= inflater.inflate(R.layout.weather, container, false);
		weatherBg = (LinearLayout) parentView.findViewById(R.id.weather_bg);
		titleBarLayout = (LinearLayout) parentView.findViewById(R.id.title_bar_layout);
		changeCity = (LinearLayout) parentView.findViewById(R.id.change_city_layout);
		cityText = (TextView)parentView. findViewById(R.id.city);
		share = (ImageView)parentView.findViewById(R.id.share);
		about = (ImageView) parentView.findViewById(R.id.about);
		refresh = (ImageView) parentView.findViewById(R.id.refresh);
		refreshing = (ProgressBar)parentView. findViewById(R.id.refreshing);
		updateTimeText = (TextView) parentView.findViewById(R.id.update_time);
		scrollView = (ScrollView)parentView. findViewById(R.id.scroll_view);
		currentWeatherLayout = (LinearLayout) parentView.findViewById(R.id.current_weather_layout);
		weatherIcon = (ImageView)parentView.findViewById(R.id.weather_icon);
		currentTemperatureText = (TextView) parentView.findViewById(R.id.current_temperature);
		currentWeatherText = (TextView)parentView.findViewById(R.id.current_weather);
		temperatureText = (TextView) parentView.findViewById(R.id.temperature);
		windText = (TextView) parentView.findViewById(R.id.wind);
		dateText = (TextView) parentView.findViewById(R.id.date);
		weatherForecastList = (ListView) parentView.findViewById(R.id.weather_forecast_list);
		changeCity.setOnClickListener(new ButtonListener());
		share.setOnClickListener(new ButtonListener());
		about.setOnClickListener(new ButtonListener());
		refresh.setOnClickListener(new ButtonListener());
		//new Thread(upWeather).start();
		Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/HelveticaNeueLTPro-Lt.ttf");
		currentTemperatureText.setTypeface(face);
		setCurrentWeatherLayoutHight();
		handler = new MyHandler();
		context = this;
		time = new Time();
		run = new Runnable() {

			@Override
			public void run() {
				refreshing(false);
				Toast.makeText(Weather.this.getActivity(), "网络超时,请稍候再试", Toast.LENGTH_SHORT)
						.show();
			}
		};
		sp = getActivity().getSharedPreferences("weather", Context.MODE_PRIVATE);
		if ("".equals(sp.getString("city", ""))) {
			intent = new Intent();
			intent.setClass(Weather.this.getActivity(), SelectCity.class);
			intent.putExtra("city", "");
			Weather.this.startActivityForResult(intent, 100);
			updateTimeText.setText("— — 更新");
			weatherBg.setBackgroundResource(R.drawable.bg_na);
			//scrollView.setVisibility(View.GONE);
		} else {
			initData();
			updateWeatherImage();
			updateWeatherInfo();
		}
		/**
		 * 透明通知栏
		 *
		 * @author 邱子濛
		 *
		 */
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		Window window = getActivity().getWindow();
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
				| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(Color.TRANSPARENT);
		window.setNavigationBarColor(Color.TRANSPARENT);}
		return parentView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1 && !data.getStringExtra("city").equals(city)) {
			city = data.getStringExtra("city");
			cityText.setText(city);
			updateTimeText.setText("— — 更新");
			weatherBg.setBackgroundResource(R.drawable.bg_na);
			//scrollView.setVisibility(View.GONE);
			if (Utils.checkNetwork(Weather.this.getActivity()) == false) {
				Toast.makeText(Weather.this.getActivity(), "网络异常,请检查网络设置", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			updateWeather();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 主线程与更新天气的线程间通讯
	 * 
	 * author 晨彦
	 * 
	 */
	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			refreshing(false);
			switch (msg.what) {
			case 1:
				handler.removeCallbacks(run);
				Bundle bundle = msg.getData();
				dateArray = bundle.getStringArray("date");
				weatherArray = bundle.getStringArray("weather");
				windArray = bundle.getStringArray("wind");
				temperatureArray = bundle.getStringArray("temperature");
				city = bundle.getString("city");
				currentTemperature = bundle.getString("current_temperature");
				saveData();
				initData();
				updateWeatherImage();
				updateWeatherInfo();
				break;
			case 2:
				builder = new Builder(Weather.this.getActivity());
				builder.setTitle("提示");
				builder.setMessage("没有查询到[" + city + "]的天气信息。");
				builder.setPositiveButton("重试",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								intent = new Intent();
								intent.setClass(Weather.this.getActivity(), SelectCity.class);
								Weather.this
										.startActivityForResult(intent, 100);
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								getActivity().finish();
							}
						});
				builder.setCancelable(false);
				builder.show();
				break;
			case 0:
				Toast.makeText(Weather.this.getActivity(), "更新失败,请稍候再试", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		dateArray = new String[4];
		weatherArray = new String[4];
		windArray = new String[4];
		temperatureArray = new String[4];
		for (int i = 0; i < 4; i++) {
			dateArray[i] = sp.getString(DATE_KEY[i], "");
			weatherArray[i] = sp.getString(WEATHER_KEY[i], "");
			windArray[i] = sp.getString(WIND_KEY[i], "");
			temperatureArray[i] = sp.getString(TEMPERATURE_KEY[i], "");
		}
		city = sp.getString("city", "");
		currentTemperature = sp.getString("current_temperature", "");
		time.setToNow();
		switch (time.weekDay) {
		case 0:
			currentWeekDay = "周日";
			break;
		case 1:
			currentWeekDay = "周一";
			break;
		case 2:
			currentWeekDay = "周二";
			break;
		case 3:
			currentWeekDay = "周三";
			break;
		case 4:
			currentWeekDay = "周四";
			break;
		case 5:
			currentWeekDay = "周五";
			break;
		case 6:
			currentWeekDay = "周六";
			break;
		default:
			break;
		}
		for (int i = 0; i < 4; i++) {
			if (dateArray[i].equals(currentWeekDay)) {
				index = i;
			}
		}
	}

	/**
	 * 更新背景图片和天气图标
	 */
	private void updateWeatherImage() {
		scrollView.setVisibility(View.VISIBLE);
		String currentWeather = weatherArray[index];
		if (currentWeather.contains("转")) {
			currentWeather = currentWeather.substring(0,
					currentWeather.indexOf("转"));
		}
		time.setToNow();
		if (currentWeather.contains("晴")) {
			if (time.hour >= 7 && time.hour < 19) {
				weatherBg.setBackgroundResource(R.drawable.bg_fine_day);
				weatherIcon.setImageResource(R.drawable.weather_img_fine_day);
			} else {
				weatherBg.setBackgroundResource(R.drawable.bg_fine_night);
				weatherIcon.setImageResource(R.drawable.weather_img_fine_night);
			}
		} else if (currentWeather.contains("多云")) {
			if (time.hour >= 7 && time.hour < 19) {
				weatherBg.setBackgroundResource(R.drawable.bg_cloudy_day);
				weatherIcon.setImageResource(R.drawable.weather_img_cloudy_day);
			} else {
				weatherBg.setBackgroundResource(R.drawable.bg_cloudy_night);
				weatherIcon
						.setImageResource(R.drawable.weather_img_cloudy_night);
			}
		} else if (currentWeather.contains("阴")) {
			weatherBg.setBackgroundResource(R.drawable.bg_overcast);
			weatherIcon.setImageResource(R.drawable.weather_img_overcast);
		} else if (currentWeather.contains("雷")) {
			weatherBg.setBackgroundResource(R.drawable.bg_thunder_storm);
			weatherIcon.setImageResource(R.drawable.weather_img_thunder_storm);
		} else if (currentWeather.contains("雨")) {
			weatherBg.setBackgroundResource(R.drawable.bg_rain);
			if (currentWeather.contains("小雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_small);
			} else if (currentWeather.contains("中雨")) {
				weatherIcon
						.setImageResource(R.drawable.weather_img_rain_middle);
			} else if (currentWeather.contains("大雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_big);
			} else if (currentWeather.contains("暴雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_storm);
			} else if (currentWeather.contains("雨夹雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_rain_snow);
			} else if (currentWeather.contains("冻雨")) {
				weatherIcon.setImageResource(R.drawable.weather_img_sleet);
			} else {
				weatherIcon
						.setImageResource(R.drawable.weather_img_rain_middle);
			}
		} else if (currentWeather.contains("雪")
				|| currentWeather.contains("冰雹")) {
			weatherBg.setBackgroundResource(R.drawable.bg_snow);
			if (currentWeather.contains("小雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_small);
			} else if (currentWeather.contains("中雪")) {
				weatherIcon
						.setImageResource(R.drawable.weather_img_snow_middle);
			} else if (currentWeather.contains("大雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_big);
			} else if (currentWeather.contains("暴雪")) {
				weatherIcon.setImageResource(R.drawable.weather_img_snow_storm);
			} else if (currentWeather.contains("冰雹")) {
				weatherIcon.setImageResource(R.drawable.weather_img_hail);
			} else {
				weatherIcon
						.setImageResource(R.drawable.weather_img_snow_middle);
			}
		} else if (currentWeather.contains("雾")) {
			weatherBg.setBackgroundResource(R.drawable.bg_fog);
			weatherIcon.setImageResource(R.drawable.weather_img_fog);
		} else if (currentWeather.contains("霾")) {
			weatherBg.setBackgroundResource(R.drawable.bg_haze);
			weatherIcon.setImageResource(R.drawable.weather_img_fog);
		} else if (currentWeather.contains("沙尘暴")
				|| currentWeather.contains("浮尘")
				|| currentWeather.contains("扬沙")) {
			weatherBg.setBackgroundResource(R.drawable.bg_sand_storm);
			weatherIcon.setImageResource(R.drawable.weather_img_sand_storm);
		} else {
			weatherBg.setBackgroundResource(R.drawable.bg_na);
			weatherIcon.setImageResource(R.drawable.weather_img_fine_day);
		}
	}

	/**
	 * 更新界面（天气信息）
	 */
	@SuppressLint("SimpleDateFormat")
	private void updateWeatherInfo() {
		cityText.setText(city);
		currentTemperatureText.setText(currentTemperature);
		currentWeatherText.setText(weatherArray[index]);
		temperatureText.setText(temperatureArray[index]);
		windText.setText(windArray[index]);
		Time time = new Time();
		time.setToNow();
		String date = new SimpleDateFormat("MM/dd").format(new Date());
		dateText.setText(currentWeekDay + " " + date);
		String updateTime = sp.getString("update_time", "");
		if (Integer.parseInt(updateTime.substring(0, 4)) == time.year
				&& Integer.parseInt(updateTime.substring(5, 7)) == time.month + 1
				&& Integer.parseInt(updateTime.substring(8, 10)) == time.monthDay) {
			updateTime = "今天" + updateTime.substring(updateTime.indexOf(" "));
			updateTimeText.setTextColor(getResources().getColor(R.color.white));
		} else {
			updateTime = updateTime.substring(5).replace("-", "月")
					.replace(" ", "日 ");
			updateTimeText.setTextColor(getResources().getColor(R.color.red));
			// 超过一天没有更新天气，自动帮用户更新
			if (Utils.checkNetwork(Weather.this.getActivity()) == true) {
				updateWeather();
			}
		}
		updateTimeText.setText(updateTime + " 更新");
		weatherForecastList.setAdapter(new MyAdapter(Weather.this.getActivity()));
		Utils.setListViewHeightBasedOnChildren(weatherForecastList);
	}

	/**
	 * 设置布局的高度（铺满屏幕）
	 */
	private void setCurrentWeatherLayoutHight() {
		// 通知栏高度
		int statusBarHeight = 0;
		try {
			statusBarHeight = getResources().getDimensionPixelSize(
					Integer.parseInt(Class
							.forName("com.android.internal.R$dimen")
							.getField("status_bar_height")
							.get(Class.forName("com.android.internal.R$dimen")
									.newInstance()).toString()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 屏幕高度
		@SuppressWarnings("deprecation")
		int displayHeight = ((WindowManager) getActivity()
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
		// title bar LinearLayout高度
		titleBarLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		int titleBarHeight = titleBarLayout.getMeasuredHeight();

		LayoutParams linearParams = (LayoutParams) currentWeatherLayout
				.getLayoutParams();
		linearParams.height = displayHeight - statusBarHeight - titleBarHeight-225;
		currentWeatherLayout.setLayoutParams(linearParams);
	}

	/**
	 * 更新天气
	 *
	 * author 邱子濛
	 *
	 */

	public void updateWeather() {
		refreshing(true);
		//handler.postDelayed(run, 60 * 1000);
		Thread upWeather = new Thread(new Runnable() {
			@Override
			public void run() {
				// http get
				Message msg = Weather.handler.obtainMessage();
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(
							"http://ienvironment.com.cn/WSN_Data_qixiang.php");
					HttpResponse response = httpclient.execute(httpget);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
				} catch (Exception e) {
					Log.e("log_tag", "Error in http connection" + e.toString());
				}
				// convert response to string
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "utf-8"), 8);
					sb = new StringBuilder();
					sb.append(reader.readLine() + "\n");

					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					result = sb.toString();
				} catch (Exception e) {
					Log.e("log_tag", "Error converting result " + e.toString());
				}
				// paring data
				if (result != null) {
					try {
						jArray = new JSONArray(result);
						JSONObject json_data = null;
						for (int i = 0; i < 4; i++) {
							json_data = jArray.getJSONObject(i);
							temperatureArray[i] = json_data.getString("airtemp");
							windArray[i] = json_data.getString("windirection") + json_data.getString("windspeed");
							weatherArray[i] = json_data.getString("rainfall");
							dateArray[i] = json_data.getString("time");
							temperatureArray[i] = temperatureArray[i] + "°";
							dateArray[i] = dateArray[i].substring(11);
						}
						currentTemperature = temperatureArray[0];
						Bundle bundle = new Bundle();
						bundle.putStringArray("date", dateArray);
						bundle.putStringArray("weather", weatherArray);
						bundle.putStringArray("wind", windArray);
						bundle.putStringArray("temperature", temperatureArray);
						bundle.putString("city", "武汉");
						bundle.putString("current_temperature", currentTemperature);
						msg.setData(bundle);
						msg.what = 1;
					} catch (JSONException e1) {
						// Toast.makeText(getBaseContext(), "No City Found"
						// ,Toast.LENGTH_LONG).show();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

				} else {
					try {
						temperatureArray = new String[]{"--", "--", "--", "--"};
						windArray = new String[]{"--", "--", "--", "--"};
						weatherArray = new String[]{"--", "--", "--", "--"};
						dateArray = new String[]{"--", "--", "--", "--"};
						currentTemperature = "--";
						Bundle bundle = new Bundle();
						bundle.putStringArray("date", dateArray);
						bundle.putStringArray("weather", weatherArray);
						bundle.putStringArray("wind", windArray);
						bundle.putStringArray("temperature", temperatureArray);
						bundle.putString("city", "武汉");
						bundle.putString("current_temperature", currentTemperature);
						msg.setData(bundle);
						msg.what = 0;
					} catch (Exception e) {
						Log.e("log_tag", "Error converting result 2 " + e.toString());
					}

				}
				Weather.handler.sendMessage(msg);
			}
		});
		upWeather.start();

	}

	/**
	 * 保存天气信息
	 */
	private void saveData() {
		String updateTime = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());
		Time time = new Time();
		time.setToNow();
		String hour, minute;
		hour = time.hour + "";
		minute = time.minute + "";
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		if (minute.length() < 2) {
			minute = "0" + minute;
		}
		updateTime = updateTime + " " + hour + ":" + minute;
		String upTime = hour + ":" + minute;
		Editor editor = sp.edit();
		editor.putString("update_time", updateTime);
		//Widget 上面显示的时间
		editor.putString("up_time", upTime);
		
		if (dateArray != null) {
			for (int i = 0; i < 4; i++) {
				editor.putString(DATE_KEY[i], dateArray[i]);
				editor.putString(WEATHER_KEY[i], weatherArray[i]);
				editor.putString(WIND_KEY[i], windArray[i]);
				editor.putString(TEMPERATURE_KEY[i], temperatureArray[i]);
			}
			editor.putString("city", city);
			editor.putString("current_temperature", currentTemperature);
		} else {
			for (int i = 0; i < 4; i++) {
				editor.putString(DATE_KEY[i], "--");
				editor.putString(WEATHER_KEY[i], "--");
				editor.putString(WIND_KEY[i], "--");
				editor.putString(TEMPERATURE_KEY[i], "--");
			}
			editor.putString("city", "--");
			editor.putString("current_temperature", "--");
		}
		//设置过期时间
		editor.putLong("validTime", currentTime);
		editor.commit();
	}

	/**
	 * 刷新时显示进度条
	 * 
	 * @param isRefreshing
	 *            是否正在刷新
	 */
	private static void refreshing(boolean isRefreshing) {
		if (isRefreshing) {
			refresh.setVisibility(View.GONE);
			refreshing.setVisibility(View.VISIBLE);
		} else {
			refresh.setVisibility(View.VISIBLE);
			refreshing.setVisibility(View.GONE);
		}
	}

	@SuppressLint("InflateParams")
	class MyAdapter extends BaseAdapter {

		private Context mContext;

		private MyAdapter(Context mContext) {
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			return getData().size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.weather_forecast_item, null);
				holder = new ViewHolder();
				holder.date = (TextView) convertView
						.findViewById(R.id.weather_forecast_date);
				holder.img = (ImageView) convertView
						.findViewById(R.id.weather_forecast_img);
				holder.weather = (TextView) convertView
						.findViewById(R.id.weather_forecast_weather);
				holder.temperature = (TextView) convertView
						.findViewById(R.id.weather_forecast_temperature);
				holder.wind = (TextView) convertView
						.findViewById(R.id.weather_forecast_wind);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/fangzhenglantingxianhe_GBK.ttf");
			holder.date.setText(getData().get(position).get("date").toString());
			holder.img.setImageResource((Integer) getData().get(position).get(
					"img"));
			holder.weather.setText(getData().get(position).get("weather")
					.toString());
			holder.temperature.setText(getData().get(position)
					.get("temperature").toString());
			holder.temperature.setTypeface(face);
			holder.wind.setText(getData().get(position).get("wind").toString());
			return convertView;
		}
	}

	class ViewHolder {
		TextView date;
		ImageView img;
		TextView weather;
		TextView temperature;
		TextView wind;
	}

	/**
	 * 获取天气预报信息
	 * author 邱子濛
	 * @return 天气预报list
	 */
	private ArrayList<HashMap<String, Object>> getData() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (dateArray[i].equals(currentWeekDay)) {
				map.put("date", "今天");
			} else {
				map.put("date", dateArray[i]);
			}
			map.put("img", getWeatherImg(weatherArray[i]));
			map.put("weather", weatherArray[i]);
			map.put("temperature", temperatureArray[i]);
			map.put("wind", windArray[i]);
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据天气信息设置天气图片
	 * 
	 * @param weather
	 *            天气信息
	 * @return 对应的天气图片id
	 */
	public int getWeatherImg(String weather) {
		int img = 0;
		if (weather.contains("转")) {
			weather = weather.substring(0, weather.indexOf("转"));
		}
		if (weather.contains("晴")) {
			img = R.drawable.weather_icon_fine;
		} else if (weather.contains("多云")) {
			img = R.drawable.weather_icon_cloudy;
		} else if (weather.contains("阴")) {
			img = R.drawable.weather_icon_overcast;
		} else if (weather.contains("雷")) {
			img = R.drawable.weather_icon_thunder_storm;
		} else if (weather.contains("小雨")) {
			img = R.drawable.weather_icon_rain_small;
		} else if (weather.contains("中雨")) {
			img = R.drawable.weather_icon_rain_middle;
		} else if (weather.contains("大雨")) {
			img = R.drawable.weather_icon_rain_big;
		} else if (weather.contains("暴雨")) {
			img = R.drawable.weather_icon_rain_storm;
		} else if (weather.contains("雨夹雪")) {
			img = R.drawable.weather_icon_rain_snow;
		} else if (weather.contains("冻雨")) {
			img = R.drawable.weather_icon_sleet;
		} else if (weather.contains("小雪")) {
			img = R.drawable.weather_icon_snow_small;
		} else if (weather.contains("中雪")) {
			img = R.drawable.weather_icon_snow_middle;
		} else if (weather.contains("大雪")) {
			img = R.drawable.weather_icon_snow_big;
		} else if (weather.contains("暴雪")) {
			img = R.drawable.weather_icon_snow_storm;
		} else if (weather.contains("冰雹")) {
			img = R.drawable.weather_icon_hail;
		} else if (weather.contains("雾") || weather.contains("霾")) {
			img = R.drawable.weather_icon_fog;
		} else if (weather.contains("沙尘暴") || weather.contains("浮尘")
				|| weather.contains("扬沙")) {
			img = R.drawable.weather_icon_sand_storm;
		} else {
			img = R.drawable.weather_icon_fine;
		}
		return img;
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.change_city_layout:
				intent = new Intent();
				intent.setClass(Weather.this.getActivity(), SelectCity.class);
				Weather.this.startActivityForResult(intent, 100);
				break;
			case R.id.share:
				intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
				intent.putExtra(Intent.EXTRA_TEXT,
						"我正在使用 《爱环境》，可以随时随地查看环境和天气信息，是您居家/出差、旅行的贴心助手！感谢您的支持，武汉大学WSN实验室出品。");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Weather.this
						.startActivity(Intent.createChooser(intent, "好友分享"));
				break;
			case R.id.about:
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View dialogLayout = inflater.inflate(R.layout.weather_dialog,
						(ViewGroup) getActivity().findViewById(R.layout.weather_dialog));
				TextView version = (TextView) dialogLayout
						.findViewById(R.id.version);
				version.setText("V " + Utils.getVersion(Weather.this.getActivity()));
				builder = new Builder(Weather.this.getActivity());
				builder.setTitle("关于");
				builder.setView(dialogLayout);
				builder.setPositiveButton("确定", null);
				builder.setCancelable(false);
				builder.show();
				break;
			case R.id.refresh:
				if (Utils.checkNetwork(Weather.this.getActivity()) == false) {
					Toast.makeText(Weather.this.getActivity(), "网络异常,请检查网络设置",
							Toast.LENGTH_SHORT).show();
					return;
				}
				updateWeather();
				break;
			default:
				break;
			}
		}

	}
}
