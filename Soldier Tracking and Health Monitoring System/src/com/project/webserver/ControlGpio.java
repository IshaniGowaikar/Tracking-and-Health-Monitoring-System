package com.project.webserver;

import com.pi4j.io.gpio.GpioController; 
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;


public class ControlGpio {

	 public static void ContorlPin(Pin pinToContol, PinState pinState) throws InterruptedException {
	        
	        System.out.println("<--Pi4J--> GPIO Control  ... started.");
	        
	        // create gpio controller
	        final GpioController gpio = GpioFactory.getInstance();
	        
	        // provision gpio pin #00 as an output pin and turn on
	        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(pinToContol, "MyLED", pinState);
	        
	        // set shutdown state for this pin
	        pin.setShutdownOptions(true, PinState.LOW);

	        // stop all GPIO activity/threads by shutting down the GPIO controller
	        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
	        gpio.shutdown();
	        gpio.unprovisionPin(pin);
	    }
}

