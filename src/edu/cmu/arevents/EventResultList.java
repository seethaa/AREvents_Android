package edu.cmu.arevents;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EventResultList extends Activity {
	static final String TAG = "MACCHA";
	private ArrayList<String> itemsToAdd;
	private  ArrayList<HashMap<String, String>> eventList;
	private ArrayList<String> addedNames;

	// JSON Node names
		 static final String TAG_EVENT = "event";
		 static final String TAG_STARTTIME = "start_time";
		 static final String TAG_LAT = "latitude";
		 static final String TAG_LONG = "longitude";
		 static final String TAG_TITLE = "title";
		 static final String TAG_ADDRESS = "venue_address";
		 static final String TAG_DESCRIPTION = "description";
		 static final String TAG_CITY = "city_name";
		 static final String TAG_IMAGE = "image";

		ListView list;
	    LazyAdapter adapter;
		 
		// events JSONArray
		JSONArray events = null;
		String full_json_string = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.result_events_list);
        
        Log.d("TAG", "I got to first active bids activity..");
        addedNames = new ArrayList<String>();
        
        eventList = new ArrayList<HashMap<String, String>>();
        
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//          }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            full_json_string = extras.getString("full_json_string");
        }
        
	        	
				updateEvents();
	        	
	        	Log.d("TAG", "I got past that thread.");

	        	list=(ListView)findViewById(R.id.list);

	        	// Getting adapter by passing xml data ArrayList
	        	adapter=new LazyAdapter(this, eventList);
	        	list.setAdapter(adapter);

	        	// Click event for single list row
	        	list.setOnItemClickListener(new OnItemClickListener() {

	        		@Override
	        		public void onItemClick(AdapterView<?> parent, View view,
	        				int position, long id) {

	        		}
	        	});		
	        	
	        	this.adapter.notifyDataSetChanged();
//        setContentView(R.layout.activity_active_bids);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

 
    @Override
	protected void onResume()
	{
		super.onResume();
		updateEvents();
		this.adapter.notifyDataSetChanged();
	
	}

	@Override
	protected void onPause()
	{

		super.onPause();
		updateEvents();
		this.adapter.notifyDataSetChanged();
		
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		updateEvents();
		this.adapter.notifyDataSetChanged();
		
	}
    
   // @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    
    private void updateEvents(){

    	
        try {
        	
        	// getting JSON string from URL
            JSONObject json = new JSONObject(full_json_string);//MainActivity.getJSONObject(); //jParser.getJSONFromUrl(callURL);
     
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
		       // JSONObject image = c.getJSONObject(TAG_IMAGE);
		        String image_url = "http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg";//image.getString("url");
                
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
        
         
    }
    
    

}
