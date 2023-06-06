package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Movie;
import com.mobileclient.domain.MovieType;
import com.mobileclient.service.MovieTypeService;
import com.mobileclient.domain.Area;
import com.mobileclient.service.AreaService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class MovieQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明影片类型下拉框
	private Spinner spinner_movieTypeObj;
	private ArrayAdapter<String> movieTypeObj_adapter;
	private static  String[] movieTypeObj_ShowText  = null;
	private List<MovieType> movieTypeList = null; 
	/*电影类型管理业务逻辑层*/
	private MovieTypeService movieTypeService = new MovieTypeService();
	// 声明影片名称输入框
	private EditText ET_movieName;
	// 声明导演输入框
	private EditText ET_director;
	// 声明主演输入框
	private EditText ET_mainPerformer;
	// 声明地区下拉框
	private Spinner spinner_areaObj;
	private ArrayAdapter<String> areaObj_adapter;
	private static  String[] areaObj_ShowText  = null;
	private List<Area> areaList = null; 
	/*地区管理业务逻辑层*/
	private AreaService areaService = new AreaService();
	// 声明放映时间输入框
	private EditText ET_playTime;
	// 声明是否推荐输入框
	private EditText ET_recommendFlag;
	/*查询过滤条件保存到这个对象中*/
	private Movie queryConditionMovie = new Movie();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.movie_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置电影查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_movieTypeObj = (Spinner) findViewById(R.id.Spinner_movieTypeObj);
		// 获取所有的电影类型
		try {
			movieTypeList = movieTypeService.QueryMovieType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int movieTypeCount = movieTypeList.size();
		movieTypeObj_ShowText = new String[movieTypeCount+1];
		movieTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=movieTypeCount;i++) { 
			movieTypeObj_ShowText[i] = movieTypeList.get(i-1).getTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		movieTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movieTypeObj_ShowText);
		// 设置影片类型下拉列表的风格
		movieTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_movieTypeObj.setAdapter(movieTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_movieTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionMovie.setMovieTypeObj(movieTypeList.get(arg2-1).getTypeId()); 
				else
					queryConditionMovie.setMovieTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_movieTypeObj.setVisibility(View.VISIBLE);
		ET_movieName = (EditText) findViewById(R.id.ET_movieName);
		ET_director = (EditText) findViewById(R.id.ET_director);
		ET_mainPerformer = (EditText) findViewById(R.id.ET_mainPerformer);
		spinner_areaObj = (Spinner) findViewById(R.id.Spinner_areaObj);
		// 获取所有的地区
		try {
			areaList = areaService.QueryArea(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int areaCount = areaList.size();
		areaObj_ShowText = new String[areaCount+1];
		areaObj_ShowText[0] = "不限制";
		for(int i=1;i<=areaCount;i++) { 
			areaObj_ShowText[i] = areaList.get(i-1).getAreaName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		areaObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, areaObj_ShowText);
		// 设置地区下拉列表的风格
		areaObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_areaObj.setAdapter(areaObj_adapter);
		// 添加事件Spinner事件监听
		spinner_areaObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionMovie.setAreaObj(areaList.get(arg2-1).getAreaId()); 
				else
					queryConditionMovie.setAreaObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_areaObj.setVisibility(View.VISIBLE);
		ET_playTime = (EditText) findViewById(R.id.ET_playTime);
		ET_recommendFlag = (EditText) findViewById(R.id.ET_recommendFlag);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionMovie.setMovieName(ET_movieName.getText().toString());
					queryConditionMovie.setDirector(ET_director.getText().toString());
					queryConditionMovie.setMainPerformer(ET_mainPerformer.getText().toString());
					queryConditionMovie.setPlayTime(ET_playTime.getText().toString());
					queryConditionMovie.setRecommendFlag(ET_recommendFlag.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionMovie", queryConditionMovie);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
