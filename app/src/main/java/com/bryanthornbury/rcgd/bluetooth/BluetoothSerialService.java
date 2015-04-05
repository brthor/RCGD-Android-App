package com.bryanthornbury.rcgd.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/*
 * Use this class to start the Bluetooth Service and connect to a bluetooth device
 */
public class BluetoothSerialService {
	
	private static final UUID SPP_SERVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static final String TAG = "BluetoothSerialService";
	
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothSocket btSocket;
	private Context mContext;
	private boolean mInitialized = false;
	 
	public BluetoothSerialService(Context ctx){
	
		 mContext = ctx;
	}
	
	public boolean initialize(){
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	     if (mBluetoothAdapter == null) {
	             Toast.makeText(mContext,
	                     "Bluetooth is not available.",
	                     Toast.LENGTH_LONG).show();
	             return false;
	     }
	
	     if (!mBluetoothAdapter.isEnabled()) {
	             Toast.makeText(mContext,
	                     "Please enable your BT and re-run this program.",
	                     Toast.LENGTH_LONG).show();
	             return false;
	     }
	     mInitialized = true;
	     return true;
	}
	
	/*
	 * Uses the bluetooth adapter to connect to a specific mac address
	 */
	public SerialDataStream connectToMAC(String address){
		
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		mBluetoothAdapter.cancelDiscovery();
		//set up socket
		try {
            btSocket = device.createRfcommSocketToServiceRecord(SPP_SERVICE_UUID);
	    } catch (IOException e) {
            Log.e(TAG, "Socket creation failed.", e);
            return null;
	    }
		
		//connect
		try {
            btSocket.connect();
            Log.e(TAG, "BT connection established, data transfer link open.");
	    } catch (IOException e) {
	    	e.printStackTrace();
	        try {
            	Toast.makeText(mContext,"Bluetooth connect failed.",Toast.LENGTH_LONG).show();
                    btSocket.close();
                    return null;
            } catch (IOException e2) {
                    Log.e(TAG,
                            "Unable to close socket during connection failure", e2);
                    return null;
            }
	    }
		
		//get data streams
	    OutputStream outStream;
	    InputStream inStream;
		try {
             outStream = btSocket.getOutputStream();
             inStream = btSocket.getInputStream();
	     } catch (IOException e) {
	             Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
	             return null;
	     }
		return new SerialDataStream(inStream,outStream);
	}
	
	public void closeSocket() throws IOException{
		btSocket.close();
	}

	public boolean isInitialized() {
		return mInitialized;
	}
}
