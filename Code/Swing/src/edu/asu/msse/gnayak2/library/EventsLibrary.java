package edu.asu.msse.gnayak2.library;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.asu.msse.gnayak2.models.Event;
import edu.asu.msse.gnayak2.networking.HTTPConnectionHelper;

public class EventsLibrary {
	static EventsLibrary eventLibrary;
	private HashMap<String, Event> eventMap;
	
	private EventsLibrary() {
		eventMap = new HashMap<String, Event>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("eventobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			Event event = new Event((JSONObject)json.get(i));
			eventMap.put(event.getId(), event);
		}
		System.out.println("Successful");
	}
	
	public static EventsLibrary getInstance() {
		if (eventLibrary == null) {
			eventLibrary = new EventsLibrary();
		} else {
			return eventLibrary;
		}
		return eventLibrary;
	}
	
	public Event getEvent(String id) {
		return eventMap.get(id);
	}
	
	public void deleteEvent(String id) {
		eventMap.remove(id);
	}
	
	public Event editEvent(Event event) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return eventMap.keySet();
	}
	
	public void addToLibrary(Event event) {
		eventMap.put(event.getId(), event);
	}
}
