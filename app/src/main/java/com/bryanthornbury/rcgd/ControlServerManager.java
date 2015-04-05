package com.bryanthornbury.rcgd;

import android.content.Context;

import com.bryanthornbury.rcgd.networking.ConnectTask;

/**
 * Created by mozoby on 4/4/15.
 * The Control server manager will
 */
public class ControlServerManager implements IManager{

    public static final String SERVER_HOST = "...";
    public static final String SERVER_PORT = "...";

    private Context context;

    private ConnectTask task;

    public ControlServerManager(Context c, DeviceCommManager deviceCommManager){
        this.context = c;
        this.task = new ConnectTask(deviceCommManager);
        task.execute();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
