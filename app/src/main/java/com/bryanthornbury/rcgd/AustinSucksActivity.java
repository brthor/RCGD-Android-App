package com.bryanthornbury.rcgd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.majorkernelpanic.streaming.MediaStream;
import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspClient;
import net.majorkernelpanic.streaming.video.VideoQuality;

public class AustinSucksActivity extends Activity  {

    public final static String TAG = "MainActivity";


    public VideoServerManager videoManager;
    public ControlServerManager controlManager;
    public DeviceCommManager deviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        videoManager = new VideoServerManager(this);
        controlManager = new ControlServerManager(this);
        deviceManager = new DeviceCommManager(this);

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        videoManager.dispose();
        controlManager.dispose();
        deviceManager.dispose();
    }
    @Override
    public void onResume(){
        this.videoManager.resume();
        this.controlManager.resume();
        this.deviceManager.resume();
        super.onResume();
    }

    public void onPause(){
        this.videoManager.pause();
        this.controlManager.pause();
        this.deviceManager.pause();
        super.onPause();
    }

}
