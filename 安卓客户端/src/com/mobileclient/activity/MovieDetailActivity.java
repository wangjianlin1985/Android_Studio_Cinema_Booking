package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.domain.MovieType;
import com.mobileclient.service.MovieTypeService;
import com.mobileclient.domain.Area;
import com.mobileclient.service.AreaService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class MovieDetailActivity extends Activity {
	// 声明我要订票按钮
	private Button btnOrder;
	// 声明返回按钮
	private Button btnReturn;
	// 声明电影id控件
	private TextView TV_movieId;
	// 声明影片类型控件
	private TextView TV_movieTypeObj;
	// 声明影片名称控件
	private TextView TV_movieName;
	// 声明影片图片图片框
	private ImageView iv_moviePhoto;
	// 声明导演控件
	private TextView TV_director;
	// 声明主演控件
	private TextView TV_mainPerformer;
	// 声明时长控件
	private TextView TV_duration;
	// 声明地区控件
	private TextView TV_areaObj;
	// 声明放映时间控件
	private TextView TV_playTime;
	// 声明票价控件
	private TextView TV_price;
	// 声明剧情简介控件
	private TextView TV_opera;
	// 声明是否推荐控件
	private TextView TV_recommendFlag;
	// 声明点击率控件
	private TextView TV_hitNum;
	/* 要保存的电影信息 */
	Movie movie = new Movie(); 
	/* 电影管理业务逻辑层 */
	private MovieService movieService = new MovieService();
	private MovieTypeService movieTypeService = new MovieTypeService();
	private AreaService areaService = new AreaService();
	private int movieId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.movie_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看电影详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnOrder = (Button) findViewById(R.id.BtnOrder);
		TV_movieId = (TextView) findViewById(R.id.TV_movieId);
		TV_movieTypeObj = (TextView) findViewById(R.id.TV_movieTypeObj);
		TV_movieName = (TextView) findViewById(R.id.TV_movieName);
		iv_moviePhoto = (ImageView) findViewById(R.id.iv_moviePhoto); 
		TV_director = (TextView) findViewById(R.id.TV_director);
		TV_mainPerformer = (TextView) findViewById(R.id.TV_mainPerformer);
		TV_duration = (TextView) findViewById(R.id.TV_duration);
		TV_areaObj = (TextView) findViewById(R.id.TV_areaObj);
		TV_playTime = (TextView) findViewById(R.id.TV_playTime);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_opera = (TextView) findViewById(R.id.TV_opera);
		TV_recommendFlag = (TextView) findViewById(R.id.TV_recommendFlag);
		TV_hitNum = (TextView) findViewById(R.id.TV_hitNum);
		Bundle extras = this.getIntent().getExtras();
		movieId = extras.getInt("movieId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MovieDetailActivity.this.finish();
			}
		}); 
		
		btnOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MovieDetailActivity.this,OrderInfoUserAddActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("movieId", movieId);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		}); 
		
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    movie = movieService.GetMovie(movieId); 
	    movie.setHitNum(movie.getHitNum() + 1);
	    movieService.UpdateMovie(movie);
	    
		this.TV_movieId.setText(movie.getMovieId() + "");
		MovieType movieTypeObj = movieTypeService.GetMovieType(movie.getMovieTypeObj());
		this.TV_movieTypeObj.setText(movieTypeObj.getTypeName());
		this.TV_movieName.setText(movie.getMovieName());
		byte[] moviePhoto_data = null;
		try {
			// 获取图片数据
			moviePhoto_data = ImageService.getImage(HttpUtil.BASE_URL + movie.getMoviePhoto());
			Bitmap moviePhoto = BitmapFactory.decodeByteArray(moviePhoto_data, 0,moviePhoto_data.length);
			this.iv_moviePhoto.setImageBitmap(moviePhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_director.setText(movie.getDirector());
		this.TV_mainPerformer.setText(movie.getMainPerformer());
		this.TV_duration.setText(movie.getDuration());
		Area areaObj = areaService.GetArea(movie.getAreaObj());
		this.TV_areaObj.setText(areaObj.getAreaName());
		this.TV_playTime.setText(movie.getPlayTime());
		this.TV_price.setText(movie.getPrice() + "");
		this.TV_opera.setText(movie.getOpera());
		this.TV_recommendFlag.setText(movie.getRecommendFlag());
		this.TV_hitNum.setText(movie.getHitNum() + "");
	} 
}
