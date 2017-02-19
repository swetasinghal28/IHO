package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.News;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class NewsLibrary {
	static NewsLibrary newsLibrary;
	private HashMap<String, News> newsMap;
	
	private NewsLibrary() {
		newsMap = new HashMap<String, News>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("newsobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			News news = new News((JSONObject)json.get(i));
			newsMap.put(news.getId(), news);
		}
		System.out.println("Successful");
	}
	
	public static NewsLibrary getInstance() {
		if (newsLibrary == null) {
			newsLibrary = new NewsLibrary();
		} else {
			return newsLibrary;
		}
		return newsLibrary;
	}
	
	public News getNews(String id) {
		return newsMap.get(id);
	}
	
	public void deleteNews(String id) {
		newsMap.remove(id);
	}
	
	public News editNews(News news) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return newsMap.keySet();
	}
	
	public void addToLibrary(News news) {
		newsMap.put(news.getId(), news);
	}
}
