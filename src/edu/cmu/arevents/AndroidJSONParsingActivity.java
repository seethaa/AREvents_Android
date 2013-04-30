package edu.cmu.arevents;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class AndroidJSONParsingActivity extends ListActivity {
 
	private String callURL = "http://api.eventful.com/json/events/search?app_key=n3nW8cSHCL576MMW&" +
			"where=37.785834,-122.406417&" +
			"keywords=concert&" +
			"within=5&" +
			"date=This+Week&" +
			"category=performing_arts";
			
	// url to make request
	//private static String url = "http://api.androidhive.info/contacts/";
	 
	// JSON Node names
	private static final String TAG_EVENT = "event";
	private static final String TAG_STARTTIME = "start_time";
	private static final String TAG_LAT = "latitude";
	private static final String TAG_LONG = "longitude";
	private static final String TAG_TITLE = "title";
	private static final String TAG_ADDRESS = "venue_address";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_CITY = "city_name";
	private static final String TAG_IMAGE = "image";

	 
	// events JSONArray
	JSONArray events = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        // Hashmap for ListView
        ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();
 
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(callURL);
 
        try {
            // Getting Array of Contacts
            events = json.getJSONArray(TAG_EVENT);
             
            // looping through All Contacts
            for(int i = 0; i < events.length(); i++){
                JSONObject c = events.getJSONObject(i);
                 
                String start_time = c.getString(TAG_STARTTIME);
		        String latitude = c.getString(TAG_LAT);
		        String longitude = c.getString(TAG_LONG);
		        String address = c.getString(TAG_ADDRESS);
		        String title = c.getString(TAG_TITLE);
		        String description = c.getString(TAG_DESCRIPTION);
		        String city = c.getString(TAG_CITY);
		         
		        // Phone number is agin JSON Object
		        JSONObject image = c.getJSONObject(TAG_IMAGE);
		        String image_url = image.getString("url");
                
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map.put(TAG_STARTTIME, start_time);
                map.put(TAG_LAT, latitude);
                map.put(TAG_LONG, longitude);
                map.put(TAG_ADDRESS, address);
                map.put(TAG_TITLE, title);
                map.put(TAG_DESCRIPTION, description);
                map.put(TAG_CITY, city);
                map.put(TAG_IMAGE, image_url);
 
                // adding HashList to ArrayList
                eventList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
         
         
        /**
         * Updating parsed JSON data into ListView
         * */
        ListAdapter adapter = new SimpleAdapter(this, eventList,
                R.layout.list_item,
                new String[] { TAG_IMAGE, TAG_TITLE, TAG_ADDRESS }, new int[] {
                        R.id.thumbnail, R.id.name, R.id.address });
 
        setListAdapter(adapter);
 
        // selecting single ListView item
        ListView lv = getListView();
 
        // Launching new screen on Selecting Single ListItem
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                String address = ((TextView) view.findViewById(R.id.address)).getText().toString();
                //String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
                 
                // Starting new intent
                Intent in = new Intent(getApplicationContext(), EventInfo.class);
                in.putExtra(TAG_TITLE, name);
                in.putExtra(TAG_ADDRESS, address);
                //in.putExtra(TAG_PHONE_MOBILE, description);
                startActivity(in);
            }
        });
    }
 
}