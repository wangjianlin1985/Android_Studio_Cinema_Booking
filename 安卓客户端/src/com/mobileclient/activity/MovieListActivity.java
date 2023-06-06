package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Movie;
import com.mobileclient.service.MovieService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.MovieSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MovieListActivity extends Activity {
	MovieSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int movieId;
	/* 电影操作业务逻辑层对象 */
	MovieService movieService = new MovieService();
	/*保存查询参数条件的电影对象*/
	private Movie queryConditionMovie;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.movie_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MovieListActivity.this, MovieQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("电影查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MovieListActivity.this, MovieAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		if(declare.getIdentify().equals("user")) add_btn.setVisibility(View.GONE);
		
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionMovie = (Movie)extras.getSerializable("queryConditionMovie");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionMovie = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new MovieSimpleAdapter(MovieListActivity.this, list,
	        					R.layout.movie_list_item,
	        					new String[] { "movieTypeObj","movieName","moviePhoto","director","mainPerformer","duration","areaObj","playTime","price","recommendFlag","hitNum" },
	        					new int[] { R.id.tv_movieTypeObj,R.id.tv_movieName,R.id.iv_moviePhoto,R.id.tv_director,R.id.tv_mainPerformer,R.id.tv_duration,R.id.tv_areaObj,R.id.tv_playTime,R.id.tv_price,R.id.tv_recommendFlag,R.id.tv_hitNum,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(movieListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int movieId = Integer.parseInt(list.get(arg2).get("movieId").toString());
            	Intent intent = new Intent();
            	intent.setClass(MovieListActivity.this, MovieDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("movieId", movieId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener movieListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) MovieListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑电影信息"); 
				menu.add(0, 1, 0, "删除电影信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑电影信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取电影id
			movieId = Integer.parseInt(list.get(position).get("movieId").toString());
			Intent intent = new Intent();
			intent.setClass(MovieListActivity.this, MovieEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("movieId", movieId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除电影信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取电影id
			movieId = Integer.parseInt(list.get(position).get("movieId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(MovieListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = movieService.DeleteMovie(movieId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询电影信息 */
			List<Movie> movieList = movieService.QueryMovie(queryConditionMovie);
			for (int i = 0; i < movieList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("movieId",movieList.get(i).getMovieId());
				map.put("movieTypeObj", movieList.get(i).getMovieTypeObj());
				map.put("movieName", movieList.get(i).getMovieName());
				/*byte[] moviePhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ movieList.get(i).getMoviePhoto());// 获取图片数据
				BitmapFactory.Options moviePhoto_opts = new BitmapFactory.Options();  
				moviePhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(moviePhoto_data, 0, moviePhoto_data.length, moviePhoto_opts); 
				moviePhoto_opts.inSampleSize = photoListActivity.computeSampleSize(moviePhoto_opts, -1, 100*100); 
				moviePhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap moviePhoto = BitmapFactory.decodeByteArray(moviePhoto_data, 0, moviePhoto_data.length, moviePhoto_opts);
					map.put("moviePhoto", moviePhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("moviePhoto", HttpUtil.BASE_URL+ movieList.get(i).getMoviePhoto());
				map.put("director", movieList.get(i).getDirector());
				map.put("mainPerformer", movieList.get(i).getMainPerformer());
				map.put("duration", movieList.get(i).getDuration());
				map.put("areaObj", movieList.get(i).getAreaObj());
				map.put("playTime", movieList.get(i).getPlayTime());
				map.put("price", movieList.get(i).getPrice());
				map.put("recommendFlag", movieList.get(i).getRecommendFlag());
				map.put("hitNum", movieList.get(i).getHitNum());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
