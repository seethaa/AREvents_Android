package edu.cmu.arevents;


import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
    	//Log.d("TAG", "NAME: " +item.get(EventResultList.TAG_TITLE));
        name.setText(item.get(EventResultList.TAG_TITLE));
        
       // Log.d("TAG", "address: " +item.get(EventResultList.TAG_ADDRESS));
       // address.setText(item.get(ActiveBidsActivity.KEY_address));
        String addr = item.get(EventResultList.TAG_ADDRESS);
      
        address.setText(addr);
      
      Drawable d = LoadImageFromWebOperations(item.get(EventResultList.TAG_IMAGE));
      thumb_image.setImageDrawable(d);
//        imageLoader.DisplayImage("http://japanese.pages.tcnj.edu/files/2011/09/Maccha_200.jpg", thumb_image);
//        imageLoader.DisplayImage(EventResultList.TAG_IMAGE, thumb_image);

		
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
	
}
