package com.bryanthornbury.rcgd;

import android.content.Context;

/**
 * Created by mozoby on 4/4/15.
 * The Control server manager will
 */
public class ControlServerManager implements IManager{

    private Context context;

    public ControlServerManager(Context c, DeviceCommManager deviceCommManager){
        this.context = c;
    }
}
