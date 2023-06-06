package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Movie;
import com.mobileclient.util.HttpUtil;

/*电影管理业务逻辑层*/
public class MovieService {
	/* 添加电影 */
	public String AddMovie(Movie movie) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("movieId", movie.getMovieId() + "");
		params.put("movieTypeObj", movie.getMovieTypeObj() + "");
		params.put("movieName", movie.getMovieName());
		params.put("moviePhoto", movie.getMoviePhoto());
		params.put("director", movie.getDirector());
		params.put("mainPerformer", movie.getMainPerformer());
		params.put("duration", movie.getDuration());
		params.put("areaObj", movie.getAreaObj() + "");
		params.put("playTime", movie.getPlayTime());
		params.put("price", movie.getPrice() + "");
		params.put("opera", movie.getOpera());
		params.put("recommendFlag", movie.getRecommendFlag());
		params.put("hitNum", movie.getHitNum() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询电影 */
	public List<Movie> QueryMovie(Movie queryConditionMovie) throws Exception {
		String urlString = HttpUtil.BASE_URL + "MovieServlet?action=query";
		if(queryConditionMovie != null) {
			urlString += "&movieTypeObj=" + queryConditionMovie.getMovieTypeObj();
			urlString += "&movieName=" + URLEncoder.encode(queryConditionMovie.getMovieName(), "UTF-8") + "";
			urlString += "&director=" + URLEncoder.encode(queryConditionMovie.getDirector(), "UTF-8") + "";
			urlString += "&mainPerformer=" + URLEncoder.encode(queryConditionMovie.getMainPerformer(), "UTF-8") + "";
			urlString += "&areaObj=" + queryConditionMovie.getAreaObj();
			urlString += "&playTime=" + URLEncoder.encode(queryConditionMovie.getPlayTime(), "UTF-8") + "";
			urlString += "&recommendFlag=" + URLEncoder.encode(queryConditionMovie.getRecommendFlag(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		MovieListHandler movieListHander = new MovieListHandler();
		xr.setContentHandler(movieListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Movie> movieList = movieListHander.getMovieList();
		return movieList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Movie> movieList = new ArrayList<Movie>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Movie movie = new Movie();
				movie.setMovieId(object.getInt("movieId"));
				movie.setMovieTypeObj(object.getInt("movieTypeObj"));
				movie.setMovieName(object.getString("movieName"));
				movie.setMoviePhoto(object.getString("moviePhoto"));
				movie.setDirector(object.getString("director"));
				movie.setMainPerformer(object.getString("mainPerformer"));
				movie.setDuration(object.getString("duration"));
				movie.setAreaObj(object.getInt("areaObj"));
				movie.setPlayTime(object.getString("playTime"));
				movie.setPrice((float) object.getDouble("price"));
				movie.setOpera(object.getString("opera"));
				movie.setRecommendFlag(object.getString("recommendFlag"));
				movie.setHitNum(object.getInt("hitNum"));
				movieList.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movieList;
	}

	/* 更新电影 */
	public String UpdateMovie(Movie movie) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("movieId", movie.getMovieId() + "");
		params.put("movieTypeObj", movie.getMovieTypeObj() + "");
		params.put("movieName", movie.getMovieName());
		params.put("moviePhoto", movie.getMoviePhoto());
		params.put("director", movie.getDirector());
		params.put("mainPerformer", movie.getMainPerformer());
		params.put("duration", movie.getDuration());
		params.put("areaObj", movie.getAreaObj() + "");
		params.put("playTime", movie.getPlayTime());
		params.put("price", movie.getPrice() + "");
		params.put("opera", movie.getOpera());
		params.put("recommendFlag", movie.getRecommendFlag());
		params.put("hitNum", movie.getHitNum() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除电影 */
	public String DeleteMovie(int movieId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("movieId", movieId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "电影信息删除失败!";
		}
	}

	/* 根据电影id获取电影对象 */
	public Movie GetMovie(int movieId)  {
		List<Movie> movieList = new ArrayList<Movie>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("movieId", movieId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MovieServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Movie movie = new Movie();
				movie.setMovieId(object.getInt("movieId"));
				movie.setMovieTypeObj(object.getInt("movieTypeObj"));
				movie.setMovieName(object.getString("movieName"));
				movie.setMoviePhoto(object.getString("moviePhoto"));
				movie.setDirector(object.getString("director"));
				movie.setMainPerformer(object.getString("mainPerformer"));
				movie.setDuration(object.getString("duration"));
				movie.setAreaObj(object.getInt("areaObj"));
				movie.setPlayTime(object.getString("playTime"));
				movie.setPrice((float) object.getDouble("price"));
				movie.setOpera(object.getString("opera"));
				movie.setRecommendFlag(object.getString("recommendFlag"));
				movie.setHitNum(object.getInt("hitNum"));
				movieList.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = movieList.size();
		if(size>0) return movieList.get(0); 
		else return null; 
	}
}
