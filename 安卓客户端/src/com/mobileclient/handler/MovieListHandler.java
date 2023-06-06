package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Movie;
public class MovieListHandler extends DefaultHandler {
	private List<Movie> movieList = null;
	private Movie movie;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (movie != null) { 
            String valueString = new String(ch, start, length); 
            if ("movieId".equals(tempString)) 
            	movie.setMovieId(new Integer(valueString).intValue());
            else if ("movieTypeObj".equals(tempString)) 
            	movie.setMovieTypeObj(new Integer(valueString).intValue());
            else if ("movieName".equals(tempString)) 
            	movie.setMovieName(valueString); 
            else if ("moviePhoto".equals(tempString)) 
            	movie.setMoviePhoto(valueString); 
            else if ("director".equals(tempString)) 
            	movie.setDirector(valueString); 
            else if ("mainPerformer".equals(tempString)) 
            	movie.setMainPerformer(valueString); 
            else if ("duration".equals(tempString)) 
            	movie.setDuration(valueString); 
            else if ("areaObj".equals(tempString)) 
            	movie.setAreaObj(new Integer(valueString).intValue());
            else if ("playTime".equals(tempString)) 
            	movie.setPlayTime(valueString); 
            else if ("price".equals(tempString)) 
            	movie.setPrice(new Float(valueString).floatValue());
            else if ("opera".equals(tempString)) 
            	movie.setOpera(valueString); 
            else if ("recommendFlag".equals(tempString)) 
            	movie.setRecommendFlag(valueString); 
            else if ("hitNum".equals(tempString)) 
            	movie.setHitNum(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Movie".equals(localName)&&movie!=null){
			movieList.add(movie);
			movie = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		movieList = new ArrayList<Movie>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Movie".equals(localName)) {
            movie = new Movie(); 
        }
        tempString = localName; 
	}

	public List<Movie> getMovieList() {
		return this.movieList;
	}
}
