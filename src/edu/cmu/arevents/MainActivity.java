package edu.cmu.arevents;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener, LocationListener {
	
	//private String callURL = "http://api.eventful.com/json/events/search?app_key=test_key&where=37.785834,-122.406417&keywords=concert&within=5&date=This+Week&category=performing_arts";
	
	
			//"http://api.eventful.com/json/events/search?app_key=test_key&location=San+Diego&sort_order=popularity&include=categories";
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

	private static JSONObject json;
	private String full_json_string;
	 
	// events JSONArray
	JSONArray events = null;
	
	
//"http://api.eventful.com/json/events/search?app_key=test_key&location=San+Diego&sort_order=popularity&include=categories";

	private String catURL = "http://api.eventful.com/json/categories/list?app_key=test_key"; 
	private RadioButton distanceButton;
	private RadioButton timeButton;
	
	//variables to get current location
	LocationManager LC;
	private String provider; 

	private String callURL = null;
	private double s_lat = 0.0;
	private double s_long = 0.0;
	private String s_keyword = "";
	private String s_within = "5";
	private String s_date = "This+Week";
	private String s_category = "";
	private EditText keywordText;
	private RadioGroup rDistanceGroup;
	private RadioGroup rTimeGroup;
	
@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.filter_screen);
	
	// find the radiobutton by returned id
	 rDistanceGroup = (RadioGroup) this.findViewById(R.id.radioDistanceGroup);
     
	
	 rTimeGroup = (RadioGroup) this.findViewById(R.id.radioTimeGroup);

     
     EditText locationText = (EditText) this.findViewById(R.id.locationText);
     //locationText.setOnClickListener(curr_location_listen);  
     
     keywordText = (EditText) this.findViewById(R.id.keywordText);
     //keywordText.setOnClickListener(keyword_listen); 
     
     
//     Button searchButton = (Button) this.findViewById(R.id.searchButton);
//     searchButton.setOnClickListener(search_button_listen); 
     
     findViewById(R.id.searchButton).setOnClickListener(this);
     
     
     //get location details:
     LC =(LocationManager) getSystemService(Context.LOCATION_SERVICE);

     Criteria criteria=new Criteria();
     provider=LC.getBestProvider(criteria, false);
             Location loc=LC.getLastKnownLocation(provider);
     if (loc!=null){
    	 onLocationChanged(loc);
     }
     else{
    	 Log.d("LOCATION: ", "No Provider");
     }
             
	}

	@Override
	public void onLocationChanged(Location location) {
	   double lat=(double) (location.getLatitude());
	   double lon=(double) (location.getLongitude());
	   
	   s_lat = lat;
	   s_long = lon;
	   
	   EditText locationText = (EditText) this.findViewById(R.id.locationText);
	   locationText.setText(lat +", " + lon);
	   
	   Log.d("LOCATION: ", lat +", " + lon);
   		Toast.makeText(getApplicationContext(), lat +", " + lon, Toast.LENGTH_LONG).show();

//	                latitudeval.setText(String.valueOf(lat));
//	                longitudeval.setText(String.valueOf(lon));
	}


	@Override
	public void onClick(View arg0) {
	Button searchB = (Button)findViewById(R.id.searchButton);
	searchB.setClickable(false);
	
	s_keyword = keywordText.getText().toString();
	
	
	RadioGroup rg=(RadioGroup)findViewById(R.id.radioDistanceGroup);
	RadioButton b_within =  (RadioButton)this.findViewById(rg.getCheckedRadioButtonId());
	s_within = b_within.getText().toString();
	
	RadioGroup rg2=(RadioGroup)findViewById(R.id.radioTimeGroup);
	RadioButton b_date =  (RadioButton)this.findViewById(rg2.getCheckedRadioButtonId());
	s_date = b_date.getText().toString();
	
//	int distId = rDistanceGroup.getCheckedRadioButtonId(); 
//	distanceButton = (RadioButton) findViewById(distId);
//	s_within = (String) distanceButton.getText();
//	
//	 int timeId = rTimeGroup.getCheckedRadioButtonId();
//	 timeButton = (RadioButton) findViewById(timeId);
//	 s_date = (String) timeButton.getText();
	 
	 if (s_date.equalsIgnoreCase("This Week")){
		 s_date = "This+Week";
	 }else if (s_date.equalsIgnoreCase("This Month")){
		 s_date = "This+Month";
	 }else if (s_date.equalsIgnoreCase("Today")){
		 s_date = "Today";
	 }
	 
	 System.out.println("distance and date: "+s_within + ","+ s_date);
	//s_category = 
			
	new LongRunningGetIO().execute();
	}

	    
	 private OnClickListener curr_location_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
		        	//Intent intent = new Intent(MainActivity.this, TakePhoto.class); 
		        	//startActivity(intent);

	        }
	    };

//	private OnClickListener keyword_listen = new OnClickListener(){
//		    	@Override
//		    	public void onClick(View v) {
//
//			        s_keyword=  keywordText.getText().toString();
//			        System.out.println("got to keyword_listen and s_keyword is : "+s_keyword);
//		        }
//		    };  
		    
		    
private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
	protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
	InputStream in = entity.getContent();
	StringBuffer out = new StringBuffer();
	int n = 1;
	while (n>0) {
	byte[] b = new byte[4096];
	n =  in.read(b);
	if (n>0) out.append(new String(b, 0, n));
	}
	return out.toString();
	}
	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		
		callURL = "http://api.eventful.com/json/events/search?app_key=test_key" +
				"&where="+s_lat +","+s_long+
				"&keywords=" + s_keyword +
				"&within=" +s_within+
				"&date=" +s_date +
				"&category=performing_arts";

		System.out.println("URL being used: " + callURL);
		Log.d("URL being used: ", callURL);
		
		HttpGet httpGet = new HttpGet(callURL);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			text = getASCIIContentFromEntity(entity);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return text;
	}
	protected void onPostExecute(String results) {
	if (results!=null) {
		
		//DECIDE WHAT TO DO WITH TEXT HERE!! 
		
		// Creating JSON Parser instance
		//JSONParser jParser = new JSONParser();
		 
		// getting JSON string from URL
		//JSONObject json = jParser.getJSONFromUrl(callURL);
		JSONObject json = null;
		try {
			json = new JSONObject(results);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String jsonstring = json.toString();
		Log.d("JSON PARSER: ", jsonstring);
		
		Toast.makeText(getApplicationContext(), jsonstring, Toast.LENGTH_LONG).show();
		
		
		int startIndex = jsonstring.indexOf("{\"event\"");
		
		if (startIndex!= -1){
			jsonstring = jsonstring.substring(startIndex, jsonstring.length()-1);
		}
		else{
			jsonstring = "";
		}
		
		//Toast.makeText(getApplicationContext(), jsonstring, Toast.LENGTH_LONG).show();
    	
    	Log.d("JSON PARSER: ", jsonstring);
		
    	//Toast.makeText(getApplicationContext(), jsonstring, Toast.LENGTH_LONG).show();

    	full_json_string = jsonstring;
		
		try {
			
			json = new JSONObject(jsonstring);
			
			boolean hasEvents = json.has("event");
	    	
	    	
	    	//Toast.makeText(getApplicationContext(), "event: "+hasEvents, Toast.LENGTH_LONG).show();

		    // Getting Array of Contacts
		    events = json.getJSONArray(TAG_EVENT);
		     
		    // looping through All Contacts
		    for(int i = 0; i < events.length(); i++){
		        JSONObject c = events.getJSONObject(i);
		         
		       
		        // Storing each json item in variable
		        String start_time = c.getString(TAG_STARTTIME);
		        String latitude = c.getString(TAG_LAT);
		        String longitude = c.getString(TAG_LONG);
		        String address = c.getString(TAG_ADDRESS);
		        String title = c.getString(TAG_TITLE);
		        String description = c.getString(TAG_DESCRIPTION);
		        String city = c.getString(TAG_CITY);
		         
		        // Phone number is agin JSON Object
		       // JSONObject image = c.getJSONObject(TAG_IMAGE);
		        
		        //String mobile = phone.getString(TAG_PHONE_MOBILE);
//		        String home = phone.getString(TAG_PHONE_HOME);
//		        String office = phone.getString(TAG_PHONE_OFFICE);
		         
		    }
		} catch (JSONException e) {
	   		Toast.makeText(getApplicationContext(), "No Events found with this criteria!", Toast.LENGTH_LONG).show();

		    e.printStackTrace();
		}
		
		
		Intent intent = new Intent(MainActivity.this, EventResultList.class); 
		intent.putExtra("full_json_string",full_json_string);
    	startActivity(intent);
    	
    	
    	
		
//		EditText et = (EditText)findViewById(R.id.locationText);
//		et.setText(results);
	}
	Button searchB = (Button)findViewById(R.id.searchButton);
	searchB.setClickable(true);
	}
}



	public static JSONObject getJSONObject(){
		return MainActivity.json;
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("LOCATION: ", "provider disabled");

	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("LOCATION: ", "provider enabled");
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}






//http://api.eventful.com/rest/events/search?app_key=test_key&location=San+Diego&sort_order=popularity&include=categories
//	
//	
///rest/users/locales/list?app_key=gjCTspt6Npz4LwxC
//
//
//http://api.eventful.com/rest/events/rss?app_key=gjCTspt6Npz4LwxC&keywords=concerts&location=seattle&sort_order=date