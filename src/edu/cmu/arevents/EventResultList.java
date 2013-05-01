package edu.cmu.arevents;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		 static String imgURL;
		 
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
	        			
	        			TextView nameView = ((TextView) view.findViewById(R.id.name));
	        			String name = nameView.getText().toString();
	        			
	        			HashMap<String, String> n2 = eventList.get(position);
	        			String details = "";
	        			
	        			for (String key : n2.keySet()) {
	        				   System.out.println("------------------------------------------------");
	        				   System.out.println("key: " + key + " value: " + n2.get(key));
	        			
			   	        	Intent intent = new Intent(EventResultList.this, EventInfoActivity.class);
			   	        	Bundle mBundle = new Bundle();
	   	        	
	   	        	
	   	        	
	   	        			if (key.equalsIgnoreCase("title")){
	   	        				details = details + "title: " +n2.get(key) + ", ";
	   	        				 title = n2.get(key)+"";
	   	        				
	   	        				intent.putExtra("title", title);
	   	        				mBundle.putString("title", title);
	   	        				
	   	        			}
	   	        			if (key.equalsIgnoreCase("venue_address")){
	   	        				details = details + "address: " +n2.get(key) + ", ";
	   	        				 addr =n2.get(key)+"";
	   	        				intent.putExtra("address",addr);
	   	        				mBundle.putString("address",addr);
	   	        			}
	   	        			if (key.equalsIgnoreCase("description")){
	   	        				descr = n2.get(key)+"";
	   	        				details = details + "descr: " +n2.get(key) + ", ";
//	   	        				intent.putExtra("description",n2.get(key));
	   	        			}
	   	        			if (key.equalsIgnoreCase("start_time")){
	   	        				details = details + "start: " +n2.get(key) + ", ";
	   	        				 st_time = n2.get(key) +"";
	   	        				intent.putExtra("start_time", st_time);
	   	        				mBundle.putString("start_time", st_time);
	   	        			}
//	   	        			if (key.equalsIgnoreCase("image")){
//	   	        				details = details + "start: " +n2.get(key) + ", ";
//	   	        				 imgURL = n2.get(key) +"";
//	   	        				intent.putExtra("start_time", imgURL);
//	   	        				mBundle.putString("start_time", imgURL);
//	   	        			}
	   	        			
	   	        			imgURL ="";
	   	        			//mBundle.putString("details", details);
	   	        			

	   	        			intent.putExtra("details", details);
	   	        			//intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

	   	        			startActivity(intent);
	        				   
	   	        	   		//Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();

	        			}


	        			

	        	   		
	        			System.out.println("name= " +name + ", n2 = "+n2);
	        			
	        			
//	        			for (HashMap<String, String> map : eventList){
//	        				for (Entry<String, String> entry : map.entrySet()){
//	        					
//		        					System.out.println(entry.getKey() + " => " + entry.getValue());
//
//	        					
//	        				}
//	        			         
//	        			}
	        			     
	        			
	        			

	        			

	        		}
	        	});		
	        	
	        	this.adapter.notifyDataSetChanged();
//        setContentView(R.layout.activity_active_bids);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
 
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
		         
		        int startIndex2 = full_json_string.indexOf("\"image\":");
				String imgStr = null;
				
				if (startIndex2!= -1){
					imgStr = full_json_string.substring(startIndex2, full_json_string.length()-1);
					int urlIndex = imgStr.indexOf("\"url\":");
					
					
					if (urlIndex!=-1){
						imgStr = imgStr.substring(urlIndex+6);
						
						int htIndex = imgStr.indexOf(",\"height\":");
						imgStr = imgStr.substring(0,htIndex);
					}
				}
				else{
					imgStr = "";
				}
				
		     // image is again JSON Object
//		        JSONObject image = c.getJSONObject(TAG_IMAGE);
		        String img_url = "";
//		        if (image!= null){
//		        	img_url = image.getString("url");
//		        	
//		        }
		        
		        img_url = imgStr.substring(1,imgStr.length()-1);
		        img_url = img_url.replaceAll("\\\\", "");
		        System.out.println("URL Used for Image: "+img_url);
		        
		       // Toast.makeText(getApplicationContext(), "image url: "+img_url, Toast.LENGTH_LONG).show();
		       
		        // image is again JSON Object
//		        String imgTxt = c.getString(TAG_IMAGE);
//		        
//		        System.out.println("img text is: "+imgTxt);
//		        JSONObject imgJson = new JSONObject(imgTxt);
//		        
//		        String image_url = imgJson.getString("url");
		        
		        //String image_url = "http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg";//image.getString("url");
                
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
                map.put(TAG_IMAGE, img_url);
 
                // adding HashList to ArrayList
                
                eventList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
         
    }
    
    

}
