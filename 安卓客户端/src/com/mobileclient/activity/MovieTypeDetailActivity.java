package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.MovieType;
import com.mobileclient.service.MovieTypeService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
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
public class MovieTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明类型id控件
	private TextView TV_typeId;
	// 声明类型名称控件
	private TextView TV_typeName;
	/* 要保存的电影类型信息 */
	MovieType movieType = new MovieType(); 
	/* 电影类型管理业务逻辑层 */
	private MovieTypeService movieTypeService = new MovieTypeService();
	private int typeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.movietype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看电影类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_typeId = (TextView) findViewById(R.id.TV_typeId);
		TV_typeName = (TextView) findViewById(R.id.TV_typeName);
		Bundle extras = this.getIntent().getExtras();
		typeId = extras.getInt("typeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MovieTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    movieType = movieTypeService.GetMovieType(typeId); 
		this.TV_typeId.setText(movieType.getTypeId() + "");
		this.TV_typeName.setText(movieType.getTypeName());
	} 
}
