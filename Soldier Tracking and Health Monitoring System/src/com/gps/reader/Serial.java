package com.gps.reader;

import com.pi4j.io.serial.*;

import java.io.IOException;

public class Serial {
	
	public com.pi4j.io.serial.Serial serial = SerialFactory.createInstance();
	
	public Serial() {
		
	}

	public void openConnection()
	{
		SerialConfig config = new SerialConfig();

		config.device("/dev/ttyAMA0")
        .baud(Baud._4800)
        .dataBits(DataBits._8)
        .parity(Parity.NONE)
        .stopBits(StopBits._1)
        .flowControl(FlowControl.NONE);
		
	    try {
			serial.open(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void registerInputCallback(SerialDataEventListener listener)
	{
		// create and register the serial data listener
        serial.addListener(listener);
	}
	
	public void closeConnection()
	{
	    try {
			serial.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

