package com.bryanthornbury.rcgd.networking;

import android.os.AsyncTask;
import android.util.Log;

import com.bryanthornbury.rcgd.DeviceCommManager;
import com.bryanthornbury.rcgd.XboxControllerInput;

import org.json.JSONObject;

public class ConnectTask extends AsyncTask<String, String, String> {

    private TCPClient mTcpClient;
    private DeviceCommManager deviceCommManager;

    public ConnectTask(DeviceCommManager device){
        this.deviceCommManager = device;
    }


    protected String doInBackground(String... message) {
        //we create a TCPClient object and
        mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                publishProgress(message);
                Log.i("Debug", "Input message: " + message);
            }
        });

        mTcpClient.run();

        return null;
    }

    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.i("onProgressUpdate", values[0]);

        String message = values[0];

        //push this to
        try{
            JSONObject obj = new JSONObject(message);

            //get Key of control
            String control = obj.getString("id");

            if(XboxControllerInput.LEFT_STICK.name().equalsIgnoreCase(control)){
                int y = obj.getJSONObject("value").getInt("y");

                //throttle
                this.deviceCommManager.writeData(XboxControllerInput.LEFT_STICK, y);
            }else if(XboxControllerInput.RIGHT_STICK.name().equalsIgnoreCase(control)){
                //turn
                int x = obj.getJSONObject("value").getInt("x");

                //throttle
                this.deviceCommManager.writeData(XboxControllerInput.LEFT_STICK, x);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}