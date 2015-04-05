package com.bryanthornbury.rcgd.networking;

import com.bryanthornbury.rcgd.DeviceCommManager;
import com.bryanthornbury.rcgd.XboxControllerInput;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mozoby on 4/5/15.
 */
public class TcpMessageListener implements TCPClient.OnMessageReceived {

    public DeviceCommManager manager;

    public TcpMessageListener(DeviceCommManager device){
        manager = device;
    }

    @Override
    public void messageReceived(String message) {
        //All messages are json!


    }
}
