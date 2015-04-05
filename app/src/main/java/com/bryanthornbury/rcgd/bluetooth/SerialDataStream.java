package com.bryanthornbury.rcgd.bluetooth;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialDataStream {
	public InputStream mInput;
	public OutputStream mOutput;
	
	public SerialDataStream(InputStream in, OutputStream out){
		mInput = in;
		mOutput = out;
	}

}
