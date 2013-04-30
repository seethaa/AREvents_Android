package edu.cmu.arevents;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LazyAdapter extends ArrayAdapter<HashMap<String, String>> {
	
	private ArrayList<HashMap<String, String>> data;
    private Activity activity;
    private LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
    	super(a, R.layout.list_item,d);
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size();
    }
 
    @Override
    public HashMap<String, String> getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        	
//            vi = inflater.inflate(R.layout.list_item, null);
        vi = inflater.inflate(R.layout.list_item, parent, false);
        	
//        	vi = LayoutInflater.from(activity).inflate(R.layout.list_item, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView address = (TextView)vi.findViewById(R.id.address); // artist name
        TextView highestBid = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> item = new HashMap<String, String>();
        item = data.get(position);
 
        
        setActiveOrdersTab(item, name, address, highestBid, thumb_image );
        // Setting all values in listview

        
     
        return vi;
    }
    
    
    

	private void setActiveOrdersTab( HashMap<String, String> item, TextView name,TextView address, TextView highestBid, ImageView thumb_image ) {
    	Log.d("TAG", "NAME: " +item.get(ActiveBidsActivity.TAG_TITLE));
        name.setText(item.get(ActiveBidsActivity.TAG_TITLE));
        
        Log.d("TAG", "address: " +item.get(ActiveBidsActivity.TAG_ADDRESS));
       // address.setText(item.get(ActiveBidsActivity.KEY_address));
        String addr = item.get(ActiveBidsActivity.TAG_ADDRESS);
      
        address.setText(addr);
      
//	    	imageLoader.DisplayImage("http://ibmsmartercommerce.sourceforge.net/wp-content/uploads/2012/09/Roses_Bunch_Of_Flowers.jpeg", thumb_image);

//        imageLoader.DisplayImage("http://"+localURL+":3000/"+item.get(ActiveBidsActivity.KEY_THUMB_URL), thumb_image);
        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
        
//        imageLoader.DisplayImage("http://10.0.2.2:3000"+"/system/items/pictures/000/000/004/original/Autumn.jpg?1354124701", thumb_image);
		
	}

	
}
