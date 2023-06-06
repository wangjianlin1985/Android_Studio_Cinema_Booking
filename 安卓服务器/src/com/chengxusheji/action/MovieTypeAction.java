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
import com.chengxusheji.dao.MovieTypeDAO;
import com.chengxusheji.domain.MovieType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MovieTypeAction extends BaseAction {

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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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

    /*待操作的MovieType对象*/
    private MovieType movieType;
    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }
    public MovieType getMovieType() {
        return this.movieType;
    }

    /*跳转到添加MovieType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加MovieType信息*/
    @SuppressWarnings("deprecation")
    public String AddMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            movieTypeDAO.AddMovieType(movieType);
            ctx.put("message",  java.net.URLEncoder.encode("MovieType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieType添加失败!"));
            return "error";
        }
    }

    /*查询MovieType信息*/
    public String QueryMovieType() {
        if(currentPage == 0) currentPage = 1;
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        movieTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = movieTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = movieTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieTypeList",  movieTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryMovieTypeOutputToExcel() { 
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MovieType信息记录"; 
        String[] headers = { "类型id","类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<movieTypeList.size();i++) {
        	MovieType movieType = movieTypeList.get(i); 
        	dataset.add(new String[]{movieType.getTypeId() + "",movieType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"MovieType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询MovieType信息*/
    public String FrontQueryMovieType() {
        if(currentPage == 0) currentPage = 1;
        List<MovieType> movieTypeList = movieTypeDAO.QueryMovieTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        movieTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = movieTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = movieTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("movieTypeList",  movieTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的MovieType信息*/
    public String ModifyMovieTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取MovieType对象*/
        MovieType movieType = movieTypeDAO.GetMovieTypeByTypeId(typeId);

        ctx.put("movieType",  movieType);
        return "modify_view";
    }

    /*查询要修改的MovieType信息*/
    public String FrontShowMovieTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取MovieType对象*/
        MovieType movieType = movieTypeDAO.GetMovieTypeByTypeId(typeId);

        ctx.put("movieType",  movieType);
        return "front_show_view";
    }

    /*更新修改MovieType信息*/
    public String ModifyMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            movieTypeDAO.UpdateMovieType(movieType);
            ctx.put("message",  java.net.URLEncoder.encode("MovieType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieType信息更新失败!"));
            return "error";
       }
   }

    /*删除MovieType信息*/
    public String DeleteMovieType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            movieTypeDAO.DeleteMovieType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("MovieType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MovieType删除失败!"));
            return "error";
        }
    }

}
