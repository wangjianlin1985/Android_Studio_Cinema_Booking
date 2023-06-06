<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Movie" %>
<%@ page import="com.chengxusheji.domain.MovieType" %>
<%@ page import="com.chengxusheji.domain.Area" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的MovieType信息
    List<MovieType> movieTypeList = (List<MovieType>)request.getAttribute("movieTypeList");
    //获取所有的Area信息
    List<Area> areaList = (List<Area>)request.getAttribute("areaList");
    Movie movie = (Movie)request.getAttribute("movie");

%>
<HTML><HEAD><TITLE>查看电影</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>电影id:</td>
    <td width=70%><%=movie.getMovieId() %></td>
  </tr>

  <tr>
    <td width=30%>影片类型:</td>
    <td width=70%>
      <%=movie.getMovieTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>影片名称:</td>
    <td width=70%><%=movie.getMovieName() %></td>
  </tr>

  <tr>
    <td width=30%>影片图片:</td>
    <td width=70%><img src="<%=basePath %><%=movie.getMoviePhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>导演:</td>
    <td width=70%><%=movie.getDirector() %></td>
  </tr>

  <tr>
    <td width=30%>主演:</td>
    <td width=70%><%=movie.getMainPerformer() %></td>
  </tr>

  <tr>
    <td width=30%>时长:</td>
    <td width=70%><%=movie.getDuration() %></td>
  </tr>

  <tr>
    <td width=30%>地区:</td>
    <td width=70%>
      <%=movie.getAreaObj().getAreaName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>放映时间:</td>
    <td width=70%><%=movie.getPlayTime() %></td>
  </tr>

  <tr>
    <td width=30%>票价:</td>
    <td width=70%><%=movie.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>剧情简介:</td>
    <td width=70%><%=movie.getOpera() %></td>
  </tr>

  <tr>
    <td width=30%>是否推荐:</td>
    <td width=70%><%=movie.getRecommendFlag() %></td>
  </tr>

  <tr>
    <td width=30%>点击率:</td>
    <td width=70%><%=movie.getHitNum() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
