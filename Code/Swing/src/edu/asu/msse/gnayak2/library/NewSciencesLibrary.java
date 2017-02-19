package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.NewScience;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class NewSciencesLibrary {
	static NewSciencesLibrary newScienceLibrary;
	private HashMap<String, NewScience> newScienceMap;
	
	private NewSciencesLibrary() {
		newScienceMap = new HashMap<String, NewScience>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("newScienceobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			NewScience newScience = new NewScience((JSONObject)json.get(i));
			newScienceMap.put(newScience.getId(), newScience);
		}
		System.out.println("Successful");
	}
	
	public static NewSciencesLibrary getInstance() {
		if (newScienceLibrary == null) {
			newScienceLibrary = new NewSciencesLibrary();
		} else {
			return newScienceLibrary;
		}
		return newScienceLibrary;
	}
	
	public NewScience getNewScience(String id) {
		return newScienceMap.get(id);
	}
	
	public void deleteNewScience(String id) {
		newScienceMap.remove(id);
	}
	
	public NewScience editNewScience(NewScience newScience) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return newScienceMap.keySet();
	}
	
	public void addToLibrary(NewScience newScience) {
		newScienceMap.put(newScience.getId(), newScience);
	}
}
