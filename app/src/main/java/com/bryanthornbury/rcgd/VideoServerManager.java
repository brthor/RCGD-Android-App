package com.bryanthornbury.rcgd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.SurfaceHolder;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspClient;
import net.majorkernelpanic.streaming.video.VideoQuality;

/**
 * The Video Server Manager is purely responsible for streaming video
 * Created by mozoby on 4/4/15.
 */
public class VideoServerManager implements IManager,
        RtspClient.Callback,
        Session.Callback,
        SurfaceHolder.Callback {

        public static final String TAG = "Video Manager";

        private SurfaceView mSurfaceView;
        private Session mSession;
        private RtspClient mClient;

        private final String streamUrl = "1d1589.entrypoint.cloud.wowza.com";
        private final String streamPath = "/app-ebe1/6ea99c5d";
        private final Integer streamPort = 1935;

        private final String streamUsername = "client795";
        private final String streamPassword = "0e281950";
        private Context context;


    public VideoServerManager(Context c){
        this.context = c;
        init();

    }

    private void init(){
        mSurfaceView = (SurfaceView) ((Activity)context).findViewById(R.id.surface);
        // Configures the SessionBuilder
        mSession = SessionBuilder.getInstance()
                .setContext(context.getApplicationContext())
                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
                        //.setAudioQuality(new AudioQuality(8000,16000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setSurfaceView(mSurfaceView)
                .setPreviewOrientation(0)
                .setCallback(this)
                .build();

        // Configures the RTSP client
        mClient = new RtspClient();
        mClient.setSession(mSession);
        mClient.setCallback(this);

        // Use this to force streaming with the MediaRecorder API
        // mSession.getVideoTrack().setStreamingMethod(MediaStream.MODE_MEDIARECORDER_API);

        // Use this to stream over TCP, EXPERIMENTAL!
        // mClient.setTransportMode(RtspClient.TRANSPORT_TCP);

        // Use this if you want the aspect ratio of the surface view to
        // respect the aspect ratio of the camera preview
        //mSurfaceView.setAspectRatioMode(SurfaceView.ASPECT_RATIO_PREVIEW);

        mSurfaceView.getHolder().addCallback(this);
        selectQuality();
    }


    @Override
    public void pause() {
        this.toggleStream();
    }

    @Override
    public void resume() {
        this.toggleStream();
    }

    public void dispose(){
        mClient.release();
        mSession.release();
        mSurfaceView.getHolder().removeCallback(this);
    }




    private void selectQuality() {

        int width = Integer.parseInt("480");
        int height = Integer.parseInt("320");
        int framerate = 30;
        int bitrate = 150*1000;

        mSession.setVideoQuality(new VideoQuality(width, height, framerate, bitrate));

        Log.d(TAG, "Selected resolution: " + width + "x" + height);
    }


    // Connects/disconnects to the RTSP server and starts/stops the stream
    public void toggleStream() {
        if (!mClient.isStreaming()) {
            mClient.setCredentials(streamUsername, streamPassword);
            mClient.setServerAddress(streamUrl, streamPort);
            mClient.setStreamPath(streamPath);
            mClient.startStream();

        } else {
            // Stops the stream and disconnects from the RTSP server
            mClient.stopStream();
        }
    }

    private void logError(final String msg) {
        final String error = (msg == null) ? "Error unknown" : msg;
        // Displays a popup to report the eror to the user
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    @Override
    public void onSessionConfigured() {

    }

    @Override
    public void onSessionStarted() {
        Log.d("Session", "start");
//        enableUI();
//        mButtonStart.setImageResource(R.drawable.ic_switch_video_active);
//        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSessionStopped() {
        Log.d("Session", "stop");
//        enableUI();
//        mButtonStart.setImageResource(R.drawable.ic_switch_video);
//        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        switch (reason) {
            case Session.ERROR_CAMERA_ALREADY_IN_USE:
                break;
            case Session.ERROR_CAMERA_HAS_NO_FLASH:
                break;
            case Session.ERROR_INVALID_SURFACE:
                break;
            case Session.ERROR_STORAGE_NOT_READY:
                break;
            case Session.ERROR_CONFIGURATION_NOT_SUPPORTED:
                VideoQuality quality = mSession.getVideoTrack().getVideoQuality();
                logError("The following settings are not supported on this phone: "+
                        quality.toString()+" "+
                        "("+e.getMessage()+")");
                e.printStackTrace();
                return;
            case Session.ERROR_OTHER:
                break;
        }

        if (e != null) {
            logError(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onRtspUpdate(int message, Exception e) {
        switch (message) {
            case RtspClient.ERROR_CONNECTION_FAILED:
            case RtspClient.ERROR_WRONG_CREDENTIALS:
                logError(e.getMessage());
                e.printStackTrace();
                break;
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSession.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mClient.stopStream();
    }
}

