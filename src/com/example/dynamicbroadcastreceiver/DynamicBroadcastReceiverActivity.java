package com.example.dynamicbroadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DynamicBroadcastReceiverActivity extends Activity {
	private final String TAG = "DynamicBroadcastReceiverActivity";
	private boolean bListening = true;
	private Button bToggleButton;
	
	private BroadcastReceiver receiver;
	
//	private BroadcastReceiver receiver = new BroadcastReceiver(){
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//        	Log.i(TAG, "BroadcastReceiver.onReceiver() called");
//            Intent serviceIntent = new Intent();
//            serviceIntent.setAction("android.intent.action.PHONE_STATE");
//            context.startService(serviceIntent);
//        }
//
//    };
    
    private IntentFilter filter = new IntentFilter("android.intent.action.PHONE_STATE");
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_broadcast_receiver);
		
//		if (savedInstanceState != null) {
//	        this.onRestoreInstanceState(savedInstanceState);
//	    } 

		receiver = createBroadcastReceiver();
		
		bListening = true;
		bToggleButton = (Button) findViewById(R.id.button1);
		bToggleButton.setBackgroundColor(Color.GREEN);
        Log.i(TAG, "onCreate() called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dynamic_broadcast_receiver, menu);
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item){
		Log.i(TAG, "onOptionsItemSelected() called");
		switch(item.getItemId()){
		
			case R.id.menu_exit:
				Log.i(TAG, "onOptionsItemSelected() menu_exit called");
				finish();
				break;

			case R.id.menu_settings:
				Log.i(TAG, "onOptionsItemSelected() menu_settings called");
//				Intent serviceIntent = new Intent();
//	            serviceIntent.setAction("android.intent.action.PHONE_STATE");
//	            sendBroadcast(serviceIntent);
//	            //this.startService(serviceIntent);
				break;
				
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
    }
	
	public void onClick(View view){
		Log.i(TAG, "onClick() called bListening: "+bListening+" :: receiver: "+receiver);
		
		
		if(bListening){
			if (receiver != null){
				this.registerReceiver(receiver, filter);
			}
			bToggleButton.setText(R.string.stop_broadcastreceiver);
			bToggleButton.setBackgroundColor(Color.RED);
			
			Toast.makeText(this, "Make a call from DDMS.", Toast.LENGTH_LONG).show();
			bListening = false;
		}else{
			if (receiver != null){
				this.unregisterReceiver(receiver);
			}
			bToggleButton.setText(R.string.start_broadcastreceiver);
			bToggleButton.setBackgroundColor(Color.GREEN);
			
			Toast.makeText(this, "BroadcastReceiver is unregistered.", Toast.LENGTH_LONG).show();
			bListening = true;
		}
	}
	
    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause() called bListening: "+bListening+" :: receiver: "+receiver);
		if (receiver != null){
			this.unregisterReceiver(receiver);
			//receiver = null;
		}
    }
    
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume() called bListening: "+bListening+" :: receiver: "+receiver);
		if(bListening == true){
			
			if(receiver == null){
				receiver = createBroadcastReceiver();
			}
			this.registerReceiver(receiver, filter);
		}                       
    }

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		Log.i(TAG, "finish() called");
	}

	public BroadcastReceiver createBroadcastReceiver(){
		return new BroadcastReceiver(){
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	Log.i(TAG, "BroadcastReceiver.onReceiver() called");
	            Intent serviceIntent = new Intent();
	            serviceIntent.setAction("android.intent.action.PHONE_STATE");
	            context.startService(serviceIntent);
	            Toast.makeText(context, "onReceive() of BroadcastReceiver is called.", Toast.LENGTH_SHORT).show();
	            
	        }
	
	    };
	}
    
	    
}
