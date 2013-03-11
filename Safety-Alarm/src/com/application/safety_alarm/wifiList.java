package com.application.safety_alarm;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class wifiList extends ListActivity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
        
		Log.i("debug", "In wifiList.onCreate");
		
        // New WiFiManager
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        boolean connected = false;
	    WifiInfo info = wifi.getConnectionInfo();
	   
	    // If wifi not on, we must turn it on in order to get the list of configured networks
	    if(info.getBSSID() != null){
		    connected = true;
	    }else{ 
		   wifi.setWifiEnabled(true);
		    try {
		    	Thread.sleep(4000);
		    } catch (InterruptedException e) {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    }
	    }
	    
        // Get the list of configured networks
        List<WifiConfiguration> configs = wifi.getConfiguredNetworks();
        
		// Display the name (SSID) of the networks in a list
        int listSize = configs.size();
		String[] accessPoints = new String[listSize];
		
		int i = 0;
		for (WifiConfiguration config : configs) {
			accessPoints[listSize-1-i] = config.SSID.substring(1,config.SSID.length()-1);
			i++;
		}
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, accessPoints));
        final ListView listView = getListView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
       
        
        // Set the onClickItemListener
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				String selectedFromList =(String) (listView.getItemAtPosition(arg2));
				Log.i("debug", "wifiList.onItemClick -> Selected network: " + selectedFromList);
				Intent intent=new Intent();
		        intent.putExtra("RESULT_STRING", selectedFromList);
		        setResult(RESULT_OK, intent);
		        finish();
			}
        });   
        
        
        // If wifi was off -> turn off wifi
        if(connected == false){	
			wifi.setWifiEnabled(false);
		}
    }

}
