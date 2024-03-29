package edu.cmu.arevents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

		 static String title;
		 static String addr;
		 static String st_time;
		 static String descr;
		 static String i_url;
		 static String city;
		 static String event_latitude;
		 static String event_longitude;
		 
		ListView list;
	    LazyAdapter adapter;
	    
	    private Button arButton;
		 
		// events JSONArray
		JSONArray events = null;
		String full_json_string = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.result_events_list);
        
        
        arButton = (Button) this.findViewById(R.id.ar_view_button);
        arButton.setOnClickListener(ar_button_listen); 
  	  
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
	        			
	        			TextView nameView = ((TextView) view.findViewById(R.id.name));
	        			String name = nameView.getText().toString();
	        			
	        			HashMap<String, String> n2 = eventList.get(position);
	        			String details = "";
	        			
	        			for (String key : n2.keySet()) {
	        				   System.out.println("------------------------------------------------");
	        				   System.out.println("key: " + key + " value: " + n2.get(key));
	        			
		   	        	Intent intent = new Intent(EventResultList.this, EventInfoActivity.class);
		   	        	//Bundle mBundle = new Bundle();
	   	        	
	   	        	
	   	        	
	   	        			if (key.equalsIgnoreCase("title")){
	   	        				details = details + "title: " +n2.get(key) + ", ";
	   	        				 title = n2.get(key)+"";
	   	        				
	   	        			//	intent.putExtra("title", title);
//	   	        				mBundle.putString("title", title);
	   	        				
	   	        			}
	   	        			if (key.equalsIgnoreCase("venue_address")){
	   	        				details = details + "address: " +n2.get(key) + ", ";
	   	        				 addr =n2.get(key)+"";
	   	        				//intent.putExtra("address",addr);
//	   	        				mBundle.putString("address",addr);
	   	        			}
	   	        			if (key.equalsIgnoreCase("description")){
	   	        				descr = n2.get(key)+"";
	   	        				details = details + "descr: " +n2.get(key) + ", ";
//	   	        				intent.putExtra("description",n2.get(key));
	   	        			}
	   	        			if (key.equalsIgnoreCase("start_time")){
	   	        				details = details + "start: " +n2.get(key) + ", ";
	   	        				 st_time = n2.get(key) +"";
	   	        				//intent.putExtra("start_time", st_time);
//	   	        				mBundle.putString("start_time", st_time);
	   	        			}
	   	        			if (key.equalsIgnoreCase("city_name")){
	   	        				details = details + "start: " +n2.get(key) + ", ";
	   	        				 city = n2.get(key) +"";
	   	        				//intent.putExtra("city", city);
//	   	        				mBundle.putString("city", city);
	   	        			}
	   	        			if (key.equalsIgnoreCase("latitude")){
	   	        				details = details + "start: " +n2.get(key) + ", ";
	   	        				event_latitude = n2.get(key) +"";
	   	        			}
	   	        			if (key.equalsIgnoreCase("longitude")){
	   	        				details = details + "start: " +n2.get(key) + ", ";
	   	        				 event_longitude = n2.get(key) +"";
	   	        			
	   	        			}


	   	        			intent.putExtra("details", details);
	   	        			//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   	        			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
	   	        			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	   	        			startActivity(intent);
	        				   
	   	        	   		//Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();

	        			}
 		
	        			System.out.println("name= " +name + ", n2 = "+n2);
	        		

	        		}
	        	});		
	        	
	        	this.adapter.notifyDataSetChanged();

    }


    private OnClickListener ar_button_listen = new OnClickListener(){
    	@Override
    	public void onClick(View v) {
    
    		
    		Intent i = new Intent();
    		i.setAction(Intent.ACTION_VIEW);
    		i.setDataAndType(Uri.parse("file:///sdcard/data.JSON"), "application/mixare-json");
    		startActivity(i); 

        }
    };
 
    @Override
	protected void onResume()
	{
		super.onResume();
//		updateEvents();
//		this.adapter.notifyDataSetChanged();
	
	}

	@Override
	protected void onPause()
	{

		super.onPause();
	
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		
	}
    

    
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

		        i_url = "http://c.fixya.net/fixya20/products/e/eventful/118x100/eventful_1614415.jpg";
		        String imgTxt = c.getString("image");
				//Toast.makeText(getApplicationContext(), "the image text: "+imgTxt, Toast.LENGTH_LONG).show();


		        if (!imgTxt.equalsIgnoreCase("null")){
//		    		Toast.makeText(getApplicationContext(), "the image text: "+imgTxt, Toast.LENGTH_LONG).show();

			        JSONObject object2 = new JSONObject(imgTxt);
			        i_url = object2.getString("medium");
			        JSONObject object3 = new JSONObject(i_url);
			        i_url = object3.getString("url");
		        }

		        
//		        
		        System.out.println("img text is: "+imgTxt);
		        System.out.println("i_url is: "+i_url);


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
                map.put(TAG_IMAGE, i_url);
 
                // adding HashList to ArrayList
                
                eventList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
         
    }
    
    

}
