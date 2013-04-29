package edu.cmu.arevents;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
public class MainActivity extends Activity implements OnClickListener {
	
	private String callURL = "http://api.eventful.com/json/events/search?app_key=test_key&location=San+Diego&sort_order=popularity&include=categories";

	private RadioButton distanceButton;
	private RadioButton timeButton;
	
@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.filter_screen);
	
	// find the radiobutton by returned id
	 RadioGroup rDistanceGroup = (RadioGroup) this.findViewById(R.id.radioDistanceGroup);
     int distId = rDistanceGroup.getCheckedRadioButtonId(); 
	 distanceButton = (RadioButton) findViewById(distId);
	        
	 RadioGroup rTimeGroup = (RadioGroup) this.findViewById(R.id.radioTimeGroup);
	 int timeId = rTimeGroup.getCheckedRadioButtonId();
	 timeButton = (RadioButton) findViewById(timeId);

     
     EditText locationText = (EditText) this.findViewById(R.id.locationText);
     locationText.setOnClickListener(curr_location_listen);  
     
     findViewById(R.id.searchButton).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
	Button searchB = (Button)findViewById(R.id.searchButton);
	searchB.setClickable(false);
	new LongRunningGetIO().execute();
	}

	 private OnClickListener curr_location_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
		        	Intent intent = new Intent(MainActivity.this, TakePhoto.class); 
		        	startActivity(intent);

	        }
	    };

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
		EditText et = (EditText)findViewById(R.id.locationText);
		et.setText(results);
	}
	Button searchB = (Button)findViewById(R.id.searchButton);
	searchB.setClickable(true);
	}
}
}






//http://api.eventful.com/rest/events/search?app_key=test_key&location=San+Diego&sort_order=popularity&include=categories
//	
//	
///rest/users/locales/list?app_key=gjCTspt6Npz4LwxC
//
//
//http://api.eventful.com/rest/events/rss?app_key=gjCTspt6Npz4LwxC&keywords=concerts&location=seattle&sort_order=date