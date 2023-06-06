package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.MovieType;

@Service @Transactional
public class MovieTypeDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddMovieType(MovieType movieType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(movieType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryMovieTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MovieType movieType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List movieTypeList = q.list();
    	return (ArrayList<MovieType>) movieTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryMovieTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MovieType movieType where 1=1";
    	Query q = s.createQuery(hql);
    	List movieTypeList = q.list();
    	return (ArrayList<MovieType>) movieTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MovieType> QueryAllMovieTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From MovieType";
        Query q = s.createQuery(hql);
        List movieTypeList = q.list();
        return (ArrayList<MovieType>) movieTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From MovieType movieType where 1=1";
        Query q = s.createQuery(hql);
        List movieTypeList = q.list();
        recordNumber = movieTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public MovieType GetMovieTypeByTypeId(int typeId) {
        Session s = factory.getCurrentSession();
        MovieType movieType = (MovieType)s.get(MovieType.class, typeId);
        return movieType;
    }

    /*更新MovieType信息*/
    public void UpdateMovieType(MovieType movieType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(movieType);
    }

    /*删除MovieType信息*/
    public void DeleteMovieType (int typeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object movieType = s.load(MovieType.class, typeId);
        s.delete(movieType);
    }

}
