package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.Lecture;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class LecturesLibrary {
	static LecturesLibrary lectureLibrary;
	private HashMap<String, Lecture> lectureMap;
	
	private LecturesLibrary() {
		lectureMap = new HashMap<String, Lecture>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("lectureobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			Lecture lecture = new Lecture((JSONObject)json.get(i));
			lectureMap.put(lecture.getId(), lecture);
		}
		System.out.println("Successful");
	}
	
	public static LecturesLibrary getInstance() {
		if (lectureLibrary == null) {
			lectureLibrary = new LecturesLibrary();
		} else {
			return lectureLibrary;
		}
		return lectureLibrary;
	}
	
	public Lecture getLecture(String id) {
		return lectureMap.get(id);
	}
	
	public void deleteLecture(String id) {
		lectureMap.remove(id);
	}
	
	public Lecture editLecture(Lecture lecture) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return lectureMap.keySet();
	}
	
	public void addToLibrary(Lecture lecture) {
		lectureMap.put(lecture.getId(), lecture);
	}
}
