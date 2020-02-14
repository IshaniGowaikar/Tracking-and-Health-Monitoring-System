package com.project.webserver;


import com.gps.reader.GPSReader;
import com.java.SMSApi.SMSApi;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class ListenGpioEmergency implements Runnable{

	public static PinState PinState1; 
	public static String emergencyStatus;
	public static GpioController gpio = GpioFactory.getInstance();
	@Override
	public void run() {
		final GpioPinDigitalInput inputpin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
		for (;;) {
			try {
				Thread.sleep(100);


				Thread.sleep(100);
				PinState1 = inputpin.getState();
				System.out.println("Emergency: "+PinState1.toString());
				if(PinState1.toString().equals("HIGH"))
				{
					emergencyStatus="YES";
				//	SMSApi.sendSMSMesage("7276025881", "Hello, Your Soldier in Danger,he need help Location is Latitude ="+GPSReader.getLat()+" Longitude="+GPSReader.getLong()+"");
					HTTPServer.emergencyStatus="YES";
				}
				else {
					emergencyStatus="NO";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
