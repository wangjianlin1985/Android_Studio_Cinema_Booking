package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.MovieType;
import com.mobileclient.util.HttpUtil;

/*电影类型管理业务逻辑层*/
public class MovieTypeService {
	/* 添加电影类型 */
	public String AddMovieType(MovieType movieType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", movieType.getTypeId() + "");
		params.put("typeName", movieType.getTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询电影类型 */
	public List<MovieType> QueryMovieType(MovieType queryConditionMovieType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "MovieTypeServlet?action=query";
		if(queryConditionMovieType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		MovieTypeListHandler movieTypeListHander = new MovieTypeListHandler();
		xr.setContentHandler(movieTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<MovieType> movieTypeList = movieTypeListHander.getMovieTypeList();
		return movieTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<MovieType> movieTypeList = new ArrayList<MovieType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MovieType movieType = new MovieType();
				movieType.setTypeId(object.getInt("typeId"));
				movieType.setTypeName(object.getString("typeName"));
				movieTypeList.add(movieType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieTypeList;
	}

	/* 更新电影类型 */
	public String UpdateMovieType(MovieType movieType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", movieType.getTypeId() + "");
		params.put("typeName", movieType.getTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除电影类型 */
	public String DeleteMovieType(int typeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "电影类型信息删除失败!";
		}
	}

	/* 根据类型id获取电影类型对象 */
	public MovieType GetMovieType(int typeId)  {
		List<MovieType> movieTypeList = new ArrayList<MovieType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MovieType movieType = new MovieType();
				movieType.setTypeId(object.getInt("typeId"));
				movieType.setTypeName(object.getString("typeName"));
				movieTypeList.add(movieType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = movieTypeList.size();
		if(size>0) return movieTypeList.get(0); 
		else return null; 
	}
}
