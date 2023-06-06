package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.MovieService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.OrderStateService;
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

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
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

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_orderNo = (TextView)convertView.findViewById(R.id.tv_orderNo);
	  holder.tv_movieObj = (TextView)convertView.findViewById(R.id.tv_movieObj);
	  holder.tv_moviePrice = (TextView)convertView.findViewById(R.id.tv_moviePrice);
	  holder.tv_orderNum = (TextView)convertView.findViewById(R.id.tv_orderNum);
	  holder.tv_orderPrice = (TextView)convertView.findViewById(R.id.tv_orderPrice);
	  holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
	  holder.tv_orderTime = (TextView)convertView.findViewById(R.id.tv_orderTime);
	  holder.tv_orderStateObj = (TextView)convertView.findViewById(R.id.tv_orderStateObj);
	  /*设置各个控件的展示内容*/
	  holder.tv_orderNo.setText("订单编号：" + mData.get(position).get("orderNo").toString());
	  holder.tv_movieObj.setText("下单电影：" + (new MovieService()).GetMovie(Integer.parseInt(mData.get(position).get("movieObj").toString())).getMovieName());
	  holder.tv_moviePrice.setText("电影价格：" + mData.get(position).get("moviePrice").toString());
	  holder.tv_orderNum.setText("购买数量：" + mData.get(position).get("orderNum").toString());
	  holder.tv_orderPrice.setText("订单总价：" + mData.get(position).get("orderPrice").toString());
	  holder.tv_userObj.setText("下单用户：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
	  holder.tv_orderTime.setText("下单时间：" + mData.get(position).get("orderTime").toString());
	  holder.tv_orderStateObj.setText("订单状态：" + (new OrderStateService()).GetOrderState(Integer.parseInt(mData.get(position).get("orderStateObj").toString())).getOrderStateName());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_orderNo;
    	TextView tv_movieObj;
    	TextView tv_moviePrice;
    	TextView tv_orderNum;
    	TextView tv_orderPrice;
    	TextView tv_userObj;
    	TextView tv_orderTime;
    	TextView tv_orderStateObj;
    }
} 
