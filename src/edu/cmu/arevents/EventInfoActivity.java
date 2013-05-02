package edu.cmu.arevents;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventInfoActivity extends Activity {

	private String title;
	private String address;
	private String description;
	private String start_time;
	private Button fbButton;
	private Button calButton;
	private Button addrButton;
	private String details;
	private String imgURL;
	private ImageView thumb_image;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.event_info_screen);
	
//	  Bundle extras = getIntent().getExtras();
//      if (extras != null) {
//          imgURL = extras.getString("full_json_string");
//      }
    	title = EventResultList.title; //  getIntent().getStringExtra("title");
    	address = EventResultList.addr;// extras.getString("address");
    	description = EventResultList.descr; //extras.getString("description");
    	start_time = EventResultList.st_time;// extras.getString("start_time");
    	imgURL = EventResultList.i_url;
    	//details = extras.getString("details");
	   // Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();

    	thumb_image = (ImageView) this.findViewById(R.id.list_image);
    	
//   		Toast.makeText(getApplicationContext(), title + ", " + address + ", " + description
//   				+ ", "+ start_time, Toast.LENGTH_LONG).show();

    	
   	 Drawable d = LoadImageFromWebOperations(imgURL);
     thumb_image.setImageDrawable(d);
//    }
//    else{
//    	Toast.makeText(getApplicationContext(), "extras is null", Toast.LENGTH_LONG).show();
//    }
    
      TextView titleText = (TextView) this.findViewById(R.id.title);
	  titleText.setText(title);

	  TextView starttimeText = (TextView) this.findViewById(R.id.starttime);
	  starttimeText.setText(start_time);
	  
	  TextView descText = (TextView) this.findViewById(R.id.descrText);
	  //descText.setText(description);
	  
	  fbButton = (Button) this.findViewById(R.id.shareFBButton);
	  fbButton.setOnClickListener(fb_button_listen); 
    
	  calButton = (Button) this.findViewById(R.id.addToCalButton);
	  calButton.setOnClickListener(cal_button_listen); 
	
	  addrButton = (Button) this.findViewById(R.id.addressButton);
	  addrButton.setText(address);
	  addrButton.setOnClickListener(addr_button_listen); 
             
	}
	
	private Drawable LoadImageFromWebOperations(String strPhotoUrl) 
    {
        try
        {
        InputStream is = (InputStream) new URL(strPhotoUrl).getContent();
        Drawable d = Drawable.createFromStream(is, "src name");
        return d;
        }catch (Exception e) {
        System.out.println("Exc="+e);
        return null;
        }
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
	
	 private OnClickListener addr_button_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
//	    		Intent intent = new Intent(EventInfoActivity.this, AndroidFacebookSample.class); 
//	    		intent.putExtra("info",title +" @ " +start_time);
//
//	    		startActivity(intent);

	        }
	    };

	 private OnClickListener fb_button_listen = new OnClickListener(){
	    	@Override
	    	public void onClick(View v) {
	    
	    		Intent intent = new Intent(EventInfoActivity.this, AndroidFacebookSample.class); 
	    		intent.putExtra("info",title +" @ " +start_time);

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
