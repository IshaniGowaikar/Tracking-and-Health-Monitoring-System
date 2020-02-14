package com.project.webserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


public class ListenGpio implements Runnable{

	public static PinState PinState1; 
	public static int Count;
	public static GpioController gpio = GpioFactory.getInstance();
	@Override
	public void run() {
		// create gpio controller
		
		System.out.println("1");

		// provision gpio pin #02 as an input pin with its internal pull down resistor enabled
		final GpioPinDigitalInput inputpin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_DOWN);
		System.out.println("2");


		// keep program running until user aborts (CTRL-C)
		for (;;) {
			try {
				
				int i = 0;
				int high = 0;
			//	ControlGpio.ContorlPin(RaspiPin.GPIO_01, PinState.HIGH);
				Thread.sleep(100);
				while(i<100)
				{
					Thread.sleep(100);
					PinState1 = inputpin.getState();
					System.out.println("Pinstate:"+ PinState1);
				//	System.out.println("GPIO_01 High");
					if(PinState1.toString().equals("HIGH"))
					{
						high++;
					}
					i++;
				}

				high = high/3 * 6 + 10;
				System.out.println("Pinstate high "+high);
				Count=high;
				
				//System.out.println("Pinstate:"+ PinState);
				//ControlGpio.ContorlPin(RaspiPin.GPIO_01, PinState.LOW);
				//System.out.println("GPIO_01 Low");
				//Thread.sleep(200);

				/*if(high >= 350)
				{
					
					try {
							SMSApi.sendSMSMesage("7276025881", "Heartrate extends the higher value");
						} catch (Exception e) {
							System.out.println("SMS Sending Failed... Check your Internet connection");
						}
					
				}*/
				high=0;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
