package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.MovieTypeService;
import com.mobileclient.service.AreaService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class MovieSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public MovieSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.movie_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_movieTypeObj = (TextView)convertView.findViewById(R.id.tv_movieTypeObj);
	  holder.tv_movieName = (TextView)convertView.findViewById(R.id.tv_movieName);
	  holder.iv_moviePhoto = (ImageView)convertView.findViewById(R.id.iv_moviePhoto);
	  holder.tv_director = (TextView)convertView.findViewById(R.id.tv_director);
	  holder.tv_mainPerformer = (TextView)convertView.findViewById(R.id.tv_mainPerformer);
	  holder.tv_duration = (TextView)convertView.findViewById(R.id.tv_duration);
	  holder.tv_areaObj = (TextView)convertView.findViewById(R.id.tv_areaObj);
	  holder.tv_playTime = (TextView)convertView.findViewById(R.id.tv_playTime);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_recommendFlag = (TextView)convertView.findViewById(R.id.tv_recommendFlag);
	  holder.tv_hitNum = (TextView)convertView.findViewById(R.id.tv_hitNum);
	  /*设置各个控件的展示内容*/
	  holder.tv_movieTypeObj.setText("影片类型：" + (new MovieTypeService()).GetMovieType(Integer.parseInt(mData.get(position).get("movieTypeObj").toString())).getTypeName());
	  holder.tv_movieName.setText("影片名称：" + mData.get(position).get("movieName").toString());
	  holder.iv_moviePhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener moviePhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_moviePhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("moviePhoto"),moviePhotoLoadListener);  
	  holder.tv_director.setText("导演：" + mData.get(position).get("director").toString());
	  holder.tv_mainPerformer.setText("主演：" + mData.get(position).get("mainPerformer").toString());
	  holder.tv_duration.setText("时长：" + mData.get(position).get("duration").toString());
	  holder.tv_areaObj.setText("地区：" + (new AreaService()).GetArea(Integer.parseInt(mData.get(position).get("areaObj").toString())).getAreaName());
	  holder.tv_playTime.setText("放映时间：" + mData.get(position).get("playTime").toString());
	  holder.tv_price.setText("票价：" + mData.get(position).get("price").toString());
	  holder.tv_recommendFlag.setText("是否推荐：" + mData.get(position).get("recommendFlag").toString());
	  holder.tv_hitNum.setText("点击率：" + mData.get(position).get("hitNum").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_movieTypeObj;
    	TextView tv_movieName;
    	ImageView iv_moviePhoto;
    	TextView tv_director;
    	TextView tv_mainPerformer;
    	TextView tv_duration;
    	TextView tv_areaObj;
    	TextView tv_playTime;
    	TextView tv_price;
    	TextView tv_recommendFlag;
    	TextView tv_hitNum;
    }
} 
