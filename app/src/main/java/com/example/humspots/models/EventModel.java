package com.example.humspots.models;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class EventModel {
    String posterURL;
    String date;
    String title;
    String description;
    String summary;
    String venueId;

    //empty constructor needed by the parceler library
    public EventModel() {}

    public EventModel(JSONObject jsonObject) throws JSONException {
        posterURL = jsonObject.getJSONObject("logo").getString("url");
        date = jsonObject.getJSONObject("start").getString("local");
        title = jsonObject.getJSONObject("name").getString("text");
        description = jsonObject.getJSONObject("description").getString("text");
        summary = jsonObject.getString("summary");
        venueId = jsonObject.getString("venue_id");
    }

    public static List<EventModel> fromJsonArray(JSONArray eventJsonArray) throws JSONException {
        List<EventModel> events = new ArrayList<>();

        for(int i = 0; i < eventJsonArray.length(); i++) {
            events.add(new EventModel(eventJsonArray.getJSONObject(i)));
        }
        return events;
    }

    public static List<EventModel> fromJsonObject(String eventJsonObjectStr) throws JSONException {
        List<EventModel> events = new ArrayList<>();

        JSONObject object = new JSONObject(eventJsonObjectStr);
        JSONArray Jarray  = object.getJSONArray("address");

        for(int i = 0; i < Jarray.length(); i++) {
            events.add(new EventModel(Jarray.getJSONObject(i)));
        }

        return events;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getDayOfMonth() {
        return date.substring(8, 10);
    }

    public String getMonthOfYear() {
        String monthValue = date.substring(5, 7);

        if(monthValue.equals("01")){return "JAN";}
        else if(monthValue.equals("02")){return "FEB";}
        else if(monthValue.equals("03")){return "MAR";}
        else if(monthValue.equals("04")){return "APR";}
        else if(monthValue.equals("05")){return "MAY";}
        else if(monthValue.equals("06")){return "JUN";}
        else if(monthValue.equals("07")){return "JUL";}
        else if(monthValue.equals("08")){return "AUG";}
        else if(monthValue.equals("09")){return "SEP";}
        else if(monthValue.equals("10")){return "OCT";}
        else if(monthValue.equals("11")){return "NOV";}
        return "DEC";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public String getVenueId(){
        return venueId;
    }
}
