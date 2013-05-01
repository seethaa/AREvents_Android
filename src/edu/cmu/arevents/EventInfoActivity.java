package edu.cmu.arevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventInfoActivity extends Activity {

	private String title;
	private String address;
	private String description;
	private String start_time;
	private Button fbButton;
	private Button calButton;
	private String details;
	private String imgURL;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.event_info_screen);
	
	
//	Bundle extras = getIntent().getExtras();
//    if (extras != null) {
    	title = EventResultList.title; //  getIntent().getStringExtra("title");
    	address = EventResultList.addr;// extras.getString("address");
    	description = EventResultList.descr; //extras.getString("description");
    	start_time = EventResultList.st_time;// extras.getString("start_time");
    	imgURL = EventResultList.imgURL;
    	//details = extras.getString("details");
	   // Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();

    	
   		Toast.makeText(getApplicationContext(), title + ", " + address + ", " + description
   				+ ", "+ start_time, Toast.LENGTH_LONG).show();

    	
//    }
//    else{
//    	Toast.makeText(getApplicationContext(), "extras is null", Toast.LENGTH_LONG).show();
//    }
    
      TextView titleText = (TextView) this.findViewById(R.id.title);
	  titleText.setText(title);

	  TextView starttimeText = (TextView) this.findViewById(R.id.starttime);
	  starttimeText.setText(start_time);
	  
	  TextView descText = (TextView) this.findViewById(R.id.descrText);
	  descText.setText(description);
	  
	  fbButton = (Button) this.findViewById(R.id.shareFBButton);
	  fbButton.setOnClickListener(fb_button_listen); 
    
	  calButton = (Button) this.findViewById(R.id.addToCalButton);
	  calButton.setOnClickListener(cal_button_listen); 
	  
	
             
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
	

	 private OnClickListener fb_button_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
	    		Intent intent = new Intent(EventInfoActivity.this, AndroidFacebookSample.class); 
		        startActivity(intent);

	        }
	    };
	
	    private OnClickListener cal_button_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
		        	//Intent intent = new Intent(MainActivity.this, TakePhoto.class); 
		        	//startActivity(intent);

	        }
	    };
}
