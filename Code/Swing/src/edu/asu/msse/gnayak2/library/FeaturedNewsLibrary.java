package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.FeaturedNews;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class FeaturedNewsLibrary {
	static FeaturedNewsLibrary newsLibrary;
	private HashMap<String, FeaturedNews> newsMap;
	
	private FeaturedNewsLibrary() {
		newsMap = new HashMap<String, FeaturedNews>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("featureobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			FeaturedNews news = new FeaturedNews((JSONObject)json.get(i));
			newsMap.put(news.getId(), news);
		}
		System.out.println("Successful");
	}
	
	public static FeaturedNewsLibrary getInstance() {
		if (newsLibrary == null) {
			newsLibrary = new FeaturedNewsLibrary();
		} else {
			return newsLibrary;
		}
		return newsLibrary;
	}
	
	public FeaturedNews getNews(String id) {
		return newsMap.get(id);
	}
	
	public void deleteNews(String id) {
		newsMap.remove(id);
	}
	
	public FeaturedNews editNews(FeaturedNews news) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return newsMap.keySet();
	}
	
	public void addToLibrary(FeaturedNews news) {
		newsMap.put(news.getId(), news);
	}
}
