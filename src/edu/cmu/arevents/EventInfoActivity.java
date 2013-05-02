package edu.cmu.arevents;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
	private String city;
	private String event_lat;
	private String event_long;
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
    	city = EventResultList.city;
    	event_lat = EventResultList.event_latitude;
    	event_long = EventResultList.event_longitude;
    	
    	//details = extras.getString("details");
	   // Toast.makeText(getApplicationContext(), details, Toast.LENGTH_LONG).show();

    	//thumb_image = (ImageView) this.findViewById(R.id.list_image);
    	
//   		Toast.makeText(getApplicationContext(), title + ", " + address + ", " + description
//   				+ ", "+ start_time, Toast.LENGTH_LONG).show();

    	
//   	 Drawable d = LoadImageFromWebOperations(imgURL);
//     thumb_image.setImageDrawable(d);

    
      TextView titleText = (TextView) this.findViewById(R.id.title);
	  titleText.setText(title);

	  TextView starttimeText = (TextView) this.findViewById(R.id.starttime);
	  starttimeText.setText(start_time);
	  
	  TextView descText = (TextView) this.findViewById(R.id.descrText);
	  
	  description= replaceAll(description,"&quot;","\"");

	  description= replaceAll(description,"&amp;","&");

	  description= replaceAll(description,"&rsquo;","Õ");
	  
	  descText.setText(description);
	  descText.setMovementMethod(new ScrollingMovementMethod());
	  
	  fbButton = (Button) this.findViewById(R.id.shareFBButton);
	  fbButton.setOnClickListener(fb_button_listen); 
    
//	  calButton = (Button) this.findViewById(R.id.addToCalButton);
//	  calButton.setOnClickListener(cal_button_listen); 
	
	  addrButton = (Button) this.findViewById(R.id.addressButton);
	  addrButton.setText(address);
	  addrButton.setOnClickListener(addr_button_listen); 
             
	}
	
	private String replaceAll(String source, String pattern, String replacement) {
        if (source == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int index;
        int patIndex = 0;
        while ((index = source.indexOf(pattern, patIndex)) != -1) {
            sb.append(source.substring(patIndex, index));
            sb.append(replacement);
            patIndex = index + pattern.length();
        }
        sb.append(source.substring(patIndex));
        return sb.toString();
    }
	
	public static String html2text(String html) {
		
		String noHTMLString = html.replaceAll("\\<.*?>","");
	    return noHTMLString;
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
	    
	    		String addr = address + "+"+ city;
	    		Toast.makeText(getApplicationContext(), addr, Toast.LENGTH_LONG).show();
	    		
	    		String s_lat = MainActivity.s_lat + "";
	    		String s_long = MainActivity.s_long + "";
	    		
	    		String gps_addr = "http://maps.google.com/maps?saddr="+ s_lat + ","+s_long + "&daddr="+event_lat + ","+event_long;
	    		
	    		
	    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
	    			    Uri.parse(gps_addr));
	    			startActivity(intent);
	    			
//	    		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//	    			    Uri.parse("google.navigation:q="+addr));
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
