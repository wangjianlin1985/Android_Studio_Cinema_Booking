package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.MovieType;
public class MovieTypeListHandler extends DefaultHandler {
	private List<MovieType> movieTypeList = null;
	private MovieType movieType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (movieType != null) { 
            String valueString = new String(ch, start, length); 
            if ("typeId".equals(tempString)) 
            	movieType.setTypeId(new Integer(valueString).intValue());
            else if ("typeName".equals(tempString)) 
            	movieType.setTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("MovieType".equals(localName)&&movieType!=null){
			movieTypeList.add(movieType);
			movieType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		movieTypeList = new ArrayList<MovieType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("MovieType".equals(localName)) {
            movieType = new MovieType(); 
        }
        tempString = localName; 
	}

	public List<MovieType> getMovieTypeList() {
		return this.movieTypeList;
	}
}
