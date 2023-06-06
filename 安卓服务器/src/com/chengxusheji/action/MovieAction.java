package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.MovieDAO;
import com.chengxusheji.domain.Movie;
import com.chengxusheji.dao.MovieTypeDAO;
import com.chengxusheji.domain.MovieType;
import com.chengxusheji.dao.AreaDAO;
import com.chengxusheji.domain.Area;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MovieAction extends BaseAction {

	/*图片或文件字段moviePhoto参数接收*/
	private File moviePhotoFile;
	private String moviePhotoFileFileName;
	private String moviePhotoFileContentType;
	public File getMoviePhotoFile() {
		return moviePhotoFile;
	}
	public void setMoviePhotoFile(File moviePhotoFile) {
		this.moviePhotoFile = moviePhotoFile;
	}
	public String getMoviePhotoFileFileName() {
		return moviePhotoFileFileName;
	}
	public void setMoviePhotoFileFileName(String moviePhotoFileFileName) {
		this.moviePhotoFileFileName = moviePhotoFileFileName;
	}
	public String getMoviePhotoFileContentType() {
		return moviePhotoFileContentType;
	}
	public void setMoviePhotoFileContentType(String moviePhotoFileContentType) {
		this.moviePhotoFileContentType = moviePhotoFileContentType;
	}
    /*界面层需要查询的属性: 影片类型*/
    private MovieType movieTypeObj;
    public void setMovieTypeObj(MovieType movieTypeObj) {
        this.movieTypeObj = movieTypeObj;
    }
    public MovieType getMovieTypeObj() {
        return this.movieTypeObj;
    }

    /*界面层需要查询的属性: 影片名称*/
    private String movieName;
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMovieName() {
        return this.movieName;
    }

    /*界面层需要查询的属性: 导演*/
    private String director;
    public void setDirector(String director) {
        this.director = director;
    }
    public String getDirector() {
        return this.director;
    }

    /*界面层需要查询的属性: 主演*/
    private String mainPerformer;
    public void setMainPerformer(String mainPerformer) {
        this.mainPerformer = mainPerformer;
    }
    public String getMainPerformer() {
        return this.mainPerformer;
    }

    /*界面层需要查询的属性: 地区*/
    private Area areaObj;
    public void setAreaObj(Area areaObj) {
        this.areaObj = areaObj;
    }
    public Area getAreaObj() {
        return this.areaObj;
    }

    /*界面层需要查询的属性: 放映时间*/
    private String playTime;
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }
    public String getPlayTime() {
        return this.playTime;
    }

    /*界面层需要查询的属性: 是否推荐*/
    private String recommendFlag;
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }
    public String getRecommendFlag() {
        return this.recommendFlag;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int movieId;
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getMovieId() {
        return movieId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource MovieTypeDAO movieTypeDAO;
    @Resource AreaDAO areaDAO;
    @Resource MovieDAO movieDAO;

    /*待操作的Movie对象*/
    private Movie movie;
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public Movie getMovie() {
        return this.movie;
    }

    /*跳转到添加Movie视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的MovieType信息*/
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        /*查询所有的Area信息*/
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        return "add_view";
    }

    /*添加Movie信息*/
    @SuppressWarnings("deprecation")
    public String AddMovie() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MovieType movieTypeObj = movieTypeDAO.GetMovieTypeByTypeId(movie.getMovieTypeObj().getTypeId());
            movie.setMovieTypeObj(movieTypeObj);
            Area areaObj = areaDAO.GetAreaByAreaId(movie.getAreaObj().getAreaId());
            movie.setAreaObj(areaObj);
            /*处理影片图片上传*/
            String moviePhotoPath = "upload/noimage.jpg"; 
       	 	if(moviePhotoFile != null)
       	 		moviePhotoPath = photoUpload(moviePhotoFile,moviePhotoFileContentType);
       	 	movie.setMoviePhoto(moviePhotoPath);
            movieDAO.AddMovie(movie);
            ctx.put("message",  java.net.URLEncoder.encode("Movie添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movie添加失败!"));
            return "error";
        }
    }

    /*查询Movie信息*/
    public String QueryMovie() {
        if(currentPage == 0) currentPage = 1;
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag, currentPage);
        /*计算总的页数和总的记录数*/
        movieDAO.CalculateTotalPageAndRecordNumber(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag);
        /*获取到总的页码数目*/
        totalPage = movieDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = movieDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieList",  movieList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("movieTypeObj", movieTypeObj);
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        ctx.put("movieName", movieName);
        ctx.put("director", director);
        ctx.put("mainPerformer", mainPerformer);
        ctx.put("areaObj", areaObj);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("playTime", playTime);
        ctx.put("recommendFlag", recommendFlag);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryMovieOutputToExcel() { 
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj,movieName,director,mainPerformer,areaObj,playTime,recommendFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Movie信息记录"; 
        String[] headers = { "电影id","影片类型","影片名称","影片图片","导演","主演","时长","地区","放映时间","票价","是否推荐","点击率"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<movieList.size();i++) {
        	Movie movie = movieList.get(i); 
        	dataset.add(new String[]{movie.getMovieId() + "",movie.getMovieTypeObj().getTypeName(),
movie.getMovieName(),movie.getMoviePhoto(),movie.getDirector(),movie.getMainPerformer(),movie.getDuration(),movie.getAreaObj().getAreaName(),
movie.getPlayTime(),movie.getPrice() + "",movie.getRecommendFlag(),movie.getHitNum() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Movie.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Movie信息*/
    public String FrontQueryMovie() {
        if(currentPage == 0) currentPage = 1;
        if(movieName == null) movieName = "";
        if(director == null) director = "";
        if(mainPerformer == null) mainPerformer = "";
        if(playTime == null) playTime = "";
        if(recommendFlag == null) recommendFlag = "";
        List<Movie> movieList = movieDAO.QueryMovieInfo(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag, currentPage);
        /*计算总的页数和总的记录数*/
        movieDAO.CalculateTotalPageAndRecordNumber(movieTypeObj, movieName, director, mainPerformer, areaObj, playTime, recommendFlag);
        /*获取到总的页码数目*/
        totalPage = movieDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = movieDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieList",  movieList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("movieTypeObj", movieTypeObj);
        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        ctx.put("movieName", movieName);
        ctx.put("director", director);
        ctx.put("mainPerformer", mainPerformer);
        ctx.put("areaObj", areaObj);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("playTime", playTime);
        ctx.put("recommendFlag", recommendFlag);
        return "front_query_view";
    }

    /*查询要修改的Movie信息*/
    public String ModifyMovieQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键movieId获取Movie对象*/
        Movie movie = movieDAO.GetMovieByMovieId(movieId);

        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("movie",  movie);
        return "modify_view";
    }

    /*查询要修改的Movie信息*/
    public String FrontShowMovieQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键movieId获取Movie对象*/
        Movie movie = movieDAO.GetMovieByMovieId(movieId);

        List<MovieType> movieTypeList = movieTypeDAO.QueryAllMovieTypeInfo();
        ctx.put("movieTypeList", movieTypeList);
        List<Area> areaList = areaDAO.QueryAllAreaInfo();
        ctx.put("areaList", areaList);
        ctx.put("movie",  movie);
        return "front_show_view";
    }

    /*更新修改Movie信息*/
    public String ModifyMovie() {
        ActionContext ctx = ActionContext.getContext();
        try {
            MovieType movieTypeObj = movieTypeDAO.GetMovieTypeByTypeId(movie.getMovieTypeObj().getTypeId());
            movie.setMovieTypeObj(movieTypeObj);
            Area areaObj = areaDAO.GetAreaByAreaId(movie.getAreaObj().getAreaId());
            movie.setAreaObj(areaObj);
            /*处理影片图片上传*/
            if(moviePhotoFile != null) {
            	String moviePhotoPath = photoUpload(moviePhotoFile,moviePhotoFileContentType);
            	movie.setMoviePhoto(moviePhotoPath);
            }
            movieDAO.UpdateMovie(movie);
            ctx.put("message",  java.net.URLEncoder.encode("Movie信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movie信息更新失败!"));
            return "error";
       }
   }

    /*删除Movie信息*/
    public String DeleteMovie() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            movieDAO.DeleteMovie(movieId);
            ctx.put("message",  java.net.URLEncoder.encode("Movie删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Movie删除失败!"));
            return "error";
        }
    }

}
