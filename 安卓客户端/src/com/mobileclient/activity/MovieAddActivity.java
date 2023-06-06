package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.domain.MovieType;
import com.mobileclient.service.MovieTypeService;
import com.mobileclient.domain.Area;
import com.mobileclient.service.AreaService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MovieAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明影片类型下拉框
	private Spinner spinner_movieTypeObj;
	private ArrayAdapter<String> movieTypeObj_adapter;
	private static  String[] movieTypeObj_ShowText  = null;
	private List<MovieType> movieTypeList = null;
	/*影片类型管理业务逻辑层*/
	private MovieTypeService movieTypeService = new MovieTypeService();
	// 声明影片名称输入框
	private EditText ET_movieName;
	// 声明影片图片图片框控件
	private ImageView iv_moviePhoto;
	private Button btn_moviePhoto;
	protected int REQ_CODE_SELECT_IMAGE_moviePhoto = 1;
	private int REQ_CODE_CAMERA_moviePhoto = 2;
	// 声明导演输入框
	private EditText ET_director;
	// 声明主演输入框
	private EditText ET_mainPerformer;
	// 声明时长输入框
	private EditText ET_duration;
	// 声明地区下拉框
	private Spinner spinner_areaObj;
	private ArrayAdapter<String> areaObj_adapter;
	private static  String[] areaObj_ShowText  = null;
	private List<Area> areaList = null;
	/*地区管理业务逻辑层*/
	private AreaService areaService = new AreaService();
	// 声明放映时间输入框
	private EditText ET_playTime;
	// 声明票价输入框
	private EditText ET_price;
	// 声明剧情简介输入框
	private EditText ET_opera;
	// 声明是否推荐输入框
	private EditText ET_recommendFlag;
	// 声明点击率输入框
	private EditText ET_hitNum;
	protected String carmera_path;
	/*要保存的电影信息*/
	Movie movie = new Movie();
	/*电影管理业务逻辑层*/
	private MovieService movieService = new MovieService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.movie_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加电影");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_movieTypeObj = (Spinner) findViewById(R.id.Spinner_movieTypeObj);
		// 获取所有的影片类型
		try {
			movieTypeList = movieTypeService.QueryMovieType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int movieTypeCount = movieTypeList.size();
		movieTypeObj_ShowText = new String[movieTypeCount];
		for(int i=0;i<movieTypeCount;i++) { 
			movieTypeObj_ShowText[i] = movieTypeList.get(i).getTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		movieTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, movieTypeObj_ShowText);
		// 设置下拉列表的风格
		movieTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_movieTypeObj.setAdapter(movieTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_movieTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				movie.setMovieTypeObj(movieTypeList.get(arg2).getTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_movieTypeObj.setVisibility(View.VISIBLE);
		ET_movieName = (EditText) findViewById(R.id.ET_movieName);
		iv_moviePhoto = (ImageView) findViewById(R.id.iv_moviePhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_moviePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MovieAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_moviePhoto);
			}
		});
		btn_moviePhoto = (Button) findViewById(R.id.btn_moviePhoto);
		btn_moviePhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_moviePhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_moviePhoto);  
			}
		});
		ET_director = (EditText) findViewById(R.id.ET_director);
		ET_mainPerformer = (EditText) findViewById(R.id.ET_mainPerformer);
		ET_duration = (EditText) findViewById(R.id.ET_duration);
		spinner_areaObj = (Spinner) findViewById(R.id.Spinner_areaObj);
		// 获取所有的地区
		try {
			areaList = areaService.QueryArea(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int areaCount = areaList.size();
		areaObj_ShowText = new String[areaCount];
		for(int i=0;i<areaCount;i++) { 
			areaObj_ShowText[i] = areaList.get(i).getAreaName();
		}
		// 将可选内容与ArrayAdapter连接起来
		areaObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, areaObj_ShowText);
		// 设置下拉列表的风格
		areaObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_areaObj.setAdapter(areaObj_adapter);
		// 添加事件Spinner事件监听
		spinner_areaObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				movie.setAreaObj(areaList.get(arg2).getAreaId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_areaObj.setVisibility(View.VISIBLE);
		ET_playTime = (EditText) findViewById(R.id.ET_playTime);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_opera = (EditText) findViewById(R.id.ET_opera);
		ET_recommendFlag = (EditText) findViewById(R.id.ET_recommendFlag);
		ET_hitNum = (EditText) findViewById(R.id.ET_hitNum);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加电影按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取影片名称*/ 
					if(ET_movieName.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "影片名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_movieName.setFocusable(true);
						ET_movieName.requestFocus();
						return;	
					}
					movie.setMovieName(ET_movieName.getText().toString());
					if(movie.getMoviePhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						MovieAddActivity.this.setTitle("正在上传图片，稍等...");
						String moviePhoto = HttpUtil.uploadFile(movie.getMoviePhoto());
						MovieAddActivity.this.setTitle("图片上传完毕！");
						movie.setMoviePhoto(moviePhoto);
					} else {
						movie.setMoviePhoto("upload/noimage.jpg");
					}
					/*验证获取导演*/ 
					if(ET_director.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "导演输入不能为空!", Toast.LENGTH_LONG).show();
						ET_director.setFocusable(true);
						ET_director.requestFocus();
						return;	
					}
					movie.setDirector(ET_director.getText().toString());
					/*验证获取主演*/ 
					if(ET_mainPerformer.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "主演输入不能为空!", Toast.LENGTH_LONG).show();
						ET_mainPerformer.setFocusable(true);
						ET_mainPerformer.requestFocus();
						return;	
					}
					movie.setMainPerformer(ET_mainPerformer.getText().toString());
					/*验证获取时长*/ 
					if(ET_duration.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "时长输入不能为空!", Toast.LENGTH_LONG).show();
						ET_duration.setFocusable(true);
						ET_duration.requestFocus();
						return;	
					}
					movie.setDuration(ET_duration.getText().toString());
					/*验证获取放映时间*/ 
					if(ET_playTime.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "放映时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_playTime.setFocusable(true);
						ET_playTime.requestFocus();
						return;	
					}
					movie.setPlayTime(ET_playTime.getText().toString());
					/*验证获取票价*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "票价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					movie.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取剧情简介*/ 
					if(ET_opera.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "剧情简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_opera.setFocusable(true);
						ET_opera.requestFocus();
						return;	
					}
					movie.setOpera(ET_opera.getText().toString());
					/*验证获取是否推荐*/ 
					if(ET_recommendFlag.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "是否推荐输入不能为空!", Toast.LENGTH_LONG).show();
						ET_recommendFlag.setFocusable(true);
						ET_recommendFlag.requestFocus();
						return;	
					}
					movie.setRecommendFlag(ET_recommendFlag.getText().toString());
					/*验证获取点击率*/ 
					if(ET_hitNum.getText().toString().equals("")) {
						Toast.makeText(MovieAddActivity.this, "点击率输入不能为空!", Toast.LENGTH_LONG).show();
						ET_hitNum.setFocusable(true);
						ET_hitNum.requestFocus();
						return;	
					}
					movie.setHitNum(Integer.parseInt(ET_hitNum.getText().toString()));
					/*调用业务逻辑层上传电影信息*/
					MovieAddActivity.this.setTitle("正在上传电影信息，稍等...");
					String result = movieService.AddMovie(movie);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_moviePhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_moviePhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_moviePhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_moviePhoto.setImageBitmap(booImageBm);
				this.iv_moviePhoto.setScaleType(ScaleType.FIT_CENTER);
				this.movie.setMoviePhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_moviePhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_moviePhoto.setImageBitmap(bm); 
				this.iv_moviePhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			movie.setMoviePhoto(filename); 
		}
	}
}
