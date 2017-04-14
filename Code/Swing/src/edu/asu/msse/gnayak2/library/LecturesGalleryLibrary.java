package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.GalleryModel;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class LecturesGalleryLibrary {
	private HashMap<String, GalleryModel> galleryMap;
	private String lectureId; 
	
	public String getLectureId() {
		return lectureId;
	}

	public void setLectureId(String lectureId) {
		this.lectureId = lectureId;
	}

	public LecturesGalleryLibrary(String lectureId, boolean isNew) {
		this.lectureId = lectureId;
		galleryMap = new HashMap<String, GalleryModel>();
		if (!isNew) {
			HTTPConnectionHelper helper = new HTTPConnectionHelper();
			String response = "";
			try {
				response = helper.sendGet("lectureimages/"+lectureId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(response);
			JSONObject jsonObject = new JSONObject(response);
			JSONArray json = jsonObject.getJSONArray("imagesarray");
			
			for (int i=0;i<json.length();i++) {
				GalleryModel gallery = new GalleryModel((JSONObject)json.get(i));
				galleryMap.put(gallery.getId(), gallery);
			}
			System.out.println("Successful");
		} 
	}
	
	
	public GalleryModel getGallery(String id) {
		return galleryMap.get(id);
	}
	
	public void deleteGallery(String id) {
		galleryMap.remove(id);
	}
	
	public GalleryModel editGallery(GalleryModel gallery) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return galleryMap.keySet();
	}
	
	public void addToLibrary(GalleryModel gallery) {
		galleryMap.put(gallery.getId(), gallery);
	}
}
