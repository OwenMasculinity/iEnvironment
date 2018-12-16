package com.wsn.ienvironment;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.wsn.ienvironment.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

/**
 * @author 邱子濛
 */

@SuppressLint("SimpleDateFormat")
public class Environment extends Fragment {

	private JSONArray jArray;
	private String result = null;
	private InputStream is = null;
	private StringBuilder sb = null;
	final private String DATE_KEY[] = { "date_0", "date_1", "date_2", "date_3" };
	final private String AQI_KEY[] = { "aqi_0", "aqi_1", "aqi_2", "aqi_3" };
	final private String INSOLATION_KEY[] = { "insolation_0", "insolation_1", "insolation_2", "insolation_3" };
	final private String PM25_KEY[] = { "pm25_0", "pm25_1", "pm25_2", "pm25_3" };
	public static Handler handler;
	public static Environment context;
	private String[] pm25Array = new String[]{"--", "--", "--", "--"};
	private String[] aqiArray = new String[]{"--", "--", "--", "--"};
	private String[] dateArray = new String[]{"--", "--", "--", "--"};
	private String[] insolationArray = new String[]{"--", "--", "--", "--"};
	private SharedPreferences sp;
	private LinearLayout environmentBg;
	private LinearLayout titleBarLayout;
	private LinearLayout changeCity;
	private TextView cityText;
	private ImageView share;
	private ImageView about;
	private static ImageView refresh;
	private static ProgressBar refreshing;
	private TextView updateTimeText;
	private MyScrollView1 scrollView;
	private LinearLayout currentEnvironmentLayout;
	private ImageView AqiIcon;
	private TextView currentPM25Text;
	private TextView currentInsolationText;
	private TextView aqiText;
	private TextView dateText;
	private ListView environmentForecastList;
	private Intent intent;
	private Time time;
	private static Runnable run;
	private Builder builder;
	private String currentWeekDay;
	private static String city;
	private String currentPM25;
	private int index = 0;
	private long currentTime = System.currentTimeMillis() + (1000 * 60 * 10);
	private static final int START_ALPHA = 0;
	private static final int END_ALPHA = 255;
	private int fadingHeight = 600;
	private Drawable drawable;
	private Drawable drawable1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.environment, container, false);
        environmentBg = (LinearLayout) parentView.findViewById(R.id.environment_bg);
        titleBarLayout = (LinearLayout) parentView.findViewById(R.id.title_bar_layout);
        changeCity = (LinearLayout) parentView.findViewById(R.id.change_city_layout);
        cityText = (TextView) parentView.findViewById(R.id.city);
        share = (ImageView) parentView.findViewById(R.id.share);
        about = (ImageView) parentView.findViewById(R.id.about);
        refresh = (ImageView) parentView.findViewById(R.id.refresh);
        refreshing = (ProgressBar) parentView.findViewById(R.id.refreshing);
        updateTimeText = (TextView) parentView.findViewById(R.id.update_time);
        scrollView = (MyScrollView1) parentView.findViewById(R.id.scroll_view);
        currentEnvironmentLayout = (LinearLayout) parentView.findViewById(R.id.current_environment_layout);
        AqiIcon = (ImageView) parentView.findViewById(R.id.aqi_icon);
        currentPM25Text = (TextView) parentView.findViewById(R.id.current_pm25);
        currentInsolationText = (TextView) parentView.findViewById(R.id.current_pm10);
        aqiText = (TextView) parentView.findViewById(R.id.aqi);
        dateText = (TextView) parentView.findViewById(R.id.date);
        environmentForecastList = (ListView) parentView.findViewById(R.id.environment_forecast_list);
        changeCity.setOnClickListener(new ButtonListener());
        share.setOnClickListener(new ButtonListener());
        about.setOnClickListener(new ButtonListener());
        refresh.setOnClickListener(new ButtonListener());

        /*Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/HelveticaNeueLTPro-Lt.ttf");
        currentPM25Text.setTypeface(face);*/
        setCurrentEnvironmentLayoutHeight();
		handler = new MyHandler();
		context = this;
		time = new Time();

		/*
		*author 张凌霄
		*/
		//改变scrollView透明度
		drawable = getResources().getDrawable(R.color.black_background);
		drawable.setAlpha(START_ALPHA);
		scrollView.setBackgroundDrawable(drawable);
		//改变title透明度
		drawable1 = getResources().getDrawable(R.color.black);
		drawable1.setAlpha(START_ALPHA);
		titleBarLayout.setBackgroundDrawable(drawable1);
		scrollView.setOnScrollChangedListener(scrollChangedListener);


		run = new Runnable() {

			@Override
			public void run() {
				refreshing(false);
				Toast.makeText(Environment.this.getActivity(), "网络超时,请稍候再试", Toast.LENGTH_SHORT)
						.show();
			}
		};
		sp = getActivity().getSharedPreferences("environment", Context.MODE_PRIVATE);
		if ("".equals(sp.getString("city", ""))) {
			intent = new Intent();
			intent.setClass(Environment.this.getActivity(), SelectCity.class);
			intent.putExtra("city", "");
			Environment.this.startActivityForResult(intent, 100);
			/*updateTimeText.setText("— — 更新");
			environmentBg.setBackgroundResource(R.drawable.bg_na);
			scrollView.setVisibility(View.GONE);*/
			/**
			 * 设置界面元素初始值
			 */
			String[] fakepm25Array = new String[]{"33", "32", "31", "30"};
			cityText.setText(city);
			currentPM25Text.setText("--");
			currentInsolationText.setText("太阳辐射--");
			aqiText.setText("--");
		} else {
			initData();
			updateEnvironmentImage();
			updateEnvironmentInfo();
		}

        /**
         * 透明通知栏
         * author:邱子濛
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
			/*environmentBg.setBackgroundResource(R.drawable.bg_na);
			scrollView.setVisibility(View.GONE);*/
			if (Utils.checkNetwork(Environment.this.getActivity()) == false) {
				Toast.makeText(Environment.this.getActivity(), "网络异常,请检查网络设置", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			updateEnvironment();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	/**
	 * author张凌霄
	 * 设置布局的高度（铺满屏幕）
	 */
    public void setCurrentEnvironmentLayoutHeight() {
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
			// 选项卡高度
			int tabHeight = ((FragmentTabSupportSlip) getActivity()).getTabHeight();
			LayoutParams linearParams = (LayoutParams) currentEnvironmentLayout
					.getLayoutParams();
			linearParams.height = displayHeight- titleBarHeight-tabHeight;
			currentEnvironmentLayout.setLayoutParams(linearParams);
		}

    class ButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.change_city_layout:
                    intent = new Intent();
                    intent.setClass(Environment.this.getActivity(), SelectCity.class);
                    Environment.this.startActivityForResult(intent, 100);
                    break;
                case R.id.share:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "我正在使用 《爱环境》，可以随时随地查看环境和天气信息，是您居家/出差、旅行的贴心助手！感谢您的支持，武汉大学WSN实验室出品。");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Environment.this
                            .startActivity(Intent.createChooser(intent, "好友分享"));
                    break;
                case R.id.about:
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.weather_dialog,
                            (ViewGroup) getActivity().findViewById(R.layout.weather_dialog));
                    TextView version = (TextView) dialogLayout
                            .findViewById(R.id.version);
                    version.setText("V " + Utils.getVersion(Environment.this.getActivity()));
                    builder = new Builder(Environment.this.getActivity());
                    builder.setTitle("关于");
                    builder.setView(dialogLayout);
                    builder.setPositiveButton("确定", null);
                    builder.setCancelable(false);
                    builder.show();
                    break;
                case R.id.refresh:
                    if (Utils.checkNetwork(Environment.this.getActivity()) == false) {
                        Toast.makeText(Environment.this.getActivity(), "网络异常,请检查网络设置",
								Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updateEnvironment();
                    break;
                default:
                    break;
            }
        }
    }

	/**
	 * 更新环境
	 * author:邱子濛
	 */
	public void updateEnvironment() {
		refreshing(true);
		//handler.postDelayed(run, 60 * 1000);
		Thread upEnvironment = new Thread(new Runnable() {
			@Override
			public void run() {
				// http get
				Message msg = Environment.handler.obtainMessage();
				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(
							"http://ienvironment.com.cn/WSN_Data_huanjing.php");
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
							pm25Array[i] = json_data.getString("PM25");
							insolationArray[i] = json_data.getString("PM10");
							aqiArray[i] = json_data.getString("AQI");
							dateArray[i] = json_data.getString("time");
							//pm25Array[i] = pm25Array[i] + "°";
							//insolationArray[i] = "太阳辐射值" + insolationArray[i];
							dateArray[i] = dateArray[i].substring(11);
						}
						currentPM25 = pm25Array[0];
						Bundle bundle = new Bundle();
						bundle.putStringArray("date", dateArray);
						bundle.putStringArray("insolation", insolationArray);
						bundle.putStringArray("aqi", aqiArray);
						bundle.putStringArray("pm25", pm25Array);
						bundle.putString("city", "武汉");
						bundle.putString("current_pm25", currentPM25);
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
						pm25Array = new String[]{"--", "--", "--", "--"};
						insolationArray = new String[]{"--", "--", "--", "--"};
						aqiArray = new String[]{"--", "--", "--", "--"};
						dateArray = new String[]{"--", "--", "--", "--"};
						currentPM25 = "--";
						Bundle bundle = new Bundle();
						bundle.putStringArray("date", dateArray);
						bundle.putStringArray("insolation", insolationArray);
						bundle.putStringArray("aqi", aqiArray);
						bundle.putStringArray("pm25", pm25Array);
						bundle.putString("city", "武汉");
						bundle.putString("current_pm25", currentPM25);
						msg.setData(bundle);
						msg.what = 0;
					} catch (Exception e) {
						Log.e("log_tag", "Error converting result 2 " + e.toString());
					}

				}
				Environment.handler.sendMessage(msg);
			}
		});
		upEnvironment.start();
	}

	/**
	 * 保存环境信息
	 * author 邱子濛
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

		for (int i = 0; i < 4; i++) {
			editor.putString(DATE_KEY[i], dateArray[i]);
			editor.putString(AQI_KEY[i], aqiArray[i]);
			editor.putString(INSOLATION_KEY[i], insolationArray[i]);
			editor.putString(PM25_KEY[i], pm25Array[i]);
		}
		editor.putString("city", city);
		editor.putString("current_pm25", currentPM25);

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
						R.layout.environment_forecast_item, null);
				holder = new ViewHolder();
				holder.date = (TextView) convertView
						.findViewById(R.id.environment_forecast_date);
				holder.img = (ImageView) convertView
						.findViewById(R.id.environment_forecast_img);
				holder.aqi = (TextView) convertView
						.findViewById(R.id.environment_forecast_aqi);
				holder.pm25 = (TextView) convertView
						.findViewById(R.id.environment_forecast_pm25);
				holder.insolation = (TextView) convertView
						.findViewById(R.id.environment_forecast_insolation);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			/*Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
					"fonts/fangzhenglantingxianhe_GBK.ttf");*/
			holder.date.setText(getData().get(position).get("date").toString());
			holder.img.setImageResource((Integer) getData().get(position).get(
					"img"));
			holder.aqi.setText(getData().get(position).get("aqi")
					.toString());
			holder.pm25.setText(getData().get(position)
					.get("pm25").toString());
			//holder.pm25.setTypeface(face);
			holder.insolation.setText(getData().get(position).get("insolation").toString());
			return convertView;
		}
	}

	class ViewHolder {
		TextView date;
		ImageView img;
		TextView aqi;
		TextView pm25;
		TextView insolation;
	}

	/**
	 * 获取环境预报信息
	 * author 邱子濛
	 * @return 环境预报list
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
			map.put("img", getEnvironmentImg(aqiArray[i]));
			map.put("aqi", aqiArray[i]);
			map.put("pm25", pm25Array[i]);
			map.put("insolation", insolationArray[i]);
			list.add(map);
		}
		return list;
	}

	/**
	 * 根据天气信息设置天气图片
	 *
	 * @param environment
	 *            天气信息
	 * @return 对应的天气图片id
	 *
	 * author:邱子濛
	 */
	public int getEnvironmentImg(String aqi) {
		int img = 0;
		if (aqi.compareTo("50")<=0) {
			img = R.drawable.aqi_you;
		} else if ((aqi.compareTo("50")>0) && (aqi.compareTo("100")<=0)) {
			img = R.drawable.aqi_liang;
		} else if ((aqi.compareTo("100")>0) && (aqi.compareTo("150")<=0)) {
			img = R.drawable.aqi_zhong;
		} else if ((aqi.compareTo("150")>0) && (aqi.compareTo("200")<=0)) {
			img = R.drawable.aqi_cha;
		} else {
			img = R.drawable.aqi_e;
		}
		return img;
	}


	/**
	 * 主线程与更新环境的线程间通讯
	 *
	 * @author 邱子濛
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
					aqiArray = bundle.getStringArray("aqi");
					insolationArray = bundle.getStringArray("insolation");
					pm25Array = bundle.getStringArray("pm25");
					city = bundle.getString("city");
					currentPM25 = bundle.getString("current_pm25");
					saveData();
					initData();
					updateEnvironmentImage();
					updateEnvironmentInfo();
					break;
				case 2:
					builder = new Builder(Environment.this.getActivity());
					builder.setTitle("提示");
					builder.setMessage("没有查询到[" + city + "]的天气信息。");
					builder.setPositiveButton("重试",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									intent = new Intent();
									intent.setClass(Environment.this.getActivity(), SelectCity.class);
									Environment.this
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
					Toast.makeText(Environment.this.getActivity(), "更新失败,请稍候再试", Toast.LENGTH_SHORT)
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
		aqiArray = new String[4];
		insolationArray = new String[4];
		pm25Array = new String[4];
		for (int i = 0; i < 4; i++) {
			dateArray[i] = sp.getString(DATE_KEY[i], "");
			aqiArray[i] = sp.getString(AQI_KEY[i], "");
			insolationArray[i] = sp.getString(INSOLATION_KEY[i], "");
			pm25Array[i] = sp.getString(PM25_KEY[i], "");
		}
		city = sp.getString("city", "");
		currentPM25 = sp.getString("current_pm25", "");
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
	 * 更新背景图片和环境图标
	 *
	 * author: 邱子濛
	 *
	 */
	private void updateEnvironmentImage() {
		scrollView.setVisibility(View.VISIBLE);
		String currentaqi = aqiArray[index];
		time.setToNow();
		if (currentaqi.compareTo("50")<=0) {
			if (time.hour >= 7 && time.hour < 19) {
				environmentBg.setBackgroundResource(R.drawable.bg_fine_day);
				AqiIcon.setImageResource(R.drawable.aqi_you);
			} else {
				environmentBg.setBackgroundResource(R.drawable.bg_fine_night);
				AqiIcon.setImageResource(R.drawable.aqi_you);
			}
		} else if ((currentaqi.compareTo("50")>0) && (currentaqi.compareTo("100")<=0)) {
			environmentBg.setBackgroundResource(R.drawable.bg_cloudy_day);
			AqiIcon.setImageResource(R.drawable.aqi_liang);
		} else if ((currentaqi.compareTo("100")>0) && (currentaqi.compareTo("150")<=0)) {
			environmentBg.setBackgroundResource(R.drawable.bg_fog);
			AqiIcon.setImageResource(R.drawable.aqi_zhong);
		} else if ((currentaqi.compareTo("150")>0) && (currentaqi.compareTo("200")<=0)) {
			environmentBg.setBackgroundResource(R.drawable.bg_haze);
			AqiIcon.setImageResource(R.drawable.aqi_cha);
		} else {
			environmentBg.setBackgroundResource(R.drawable.bg_sand_storm);
			AqiIcon.setImageResource(R.drawable.aqi_e);
		}
	}

	/**
	 * 更新界面（环境信息）
	 */
	@SuppressLint("SimpleDateFormat")
	private void updateEnvironmentInfo() {
		cityText.setText(city);
		currentPM25Text.setText(currentPM25);
		currentInsolationText.setText(insolationArray[index]);
		aqiText.setText(aqiArray[index]);
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
			if (Utils.checkNetwork(Environment.this.getActivity()) == true) {
				updateEnvironment();
			}
		}
		updateTimeText.setText(updateTime + " 更新");
		environmentForecastList.setAdapter(new MyAdapter(Environment.this.getActivity()));
		Utils.setListViewHeightBasedOnChildren(environmentForecastList);
	}

	/*
 	* @author 张凌霄
 	* 滑动ScrollView时填充颜色透明度变化
 	*/
	private MyScrollView1.OnScrollChangedListener scrollChangedListener = new MyScrollView1.OnScrollChangedListener() {
		@Override
		public void onScrollChanged(ScrollView who, int x, int y, int oldx, int oldy) {
			if (y > fadingHeight) {
				y = fadingHeight;
			}
			drawable.setAlpha(y * (END_ALPHA - START_ALPHA) / fadingHeight + START_ALPHA);
			drawable1.setAlpha(y * (END_ALPHA - START_ALPHA) / fadingHeight + START_ALPHA);
		}
	};
}
