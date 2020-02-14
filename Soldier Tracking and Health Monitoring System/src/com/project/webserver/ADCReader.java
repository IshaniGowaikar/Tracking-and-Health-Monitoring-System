package com.project.webserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Read an Analog to Digital Converter
 */
 public class ADCReader implements Runnable
 {
	 private final static boolean DEBUG         = true;
	 // Note: "Mismatch" 23-24. The wiring says DOUT->#23, DIN->#24
	 // 23: DOUT on the ADC is IN on the GPIO. ADC:Slave, GPIO:Master
	 // 24: DIN on the ADC, OUT on the GPIO. Same reason as above.
	 // SPI: Serial Peripheral Interface
	 private static Pin spiClk  = RaspiPin.GPIO_14; // Pin #18, clock
	 private static Pin spiMiso = RaspiPin.GPIO_13; // Pin #23, data in.  MISO: Master In Slave Out
	 private static Pin spiMosi = RaspiPin.GPIO_12; // Pin #24, data out. MOSI: Master Out Slave In
	 private static Pin spiCs   = RaspiPin.GPIO_10; // Pin #25, Chip Select

	 private static int ADC_CHANNEL = 0; // Between 0 and 7, 8 channels on the MCP3008

	 private static GpioPinDigitalInput  misoInput        = null;
	 private static GpioPinDigitalOutput mosiOutput       = null;
	 private static GpioPinDigitalOutput clockOutput      = null;
	 private static GpioPinDigitalOutput chipSelectOutput = null;

	 private static boolean go = true;

	 public static int adc;


	 private static int readAdc()
	 {
		 chipSelectOutput.high();

		 clockOutput.low();
		 chipSelectOutput.low();

		 int adccommand = ADC_CHANNEL; //0
		 adccommand |= 0x18; // 0x18: 00011000
		 adccommand <<= 3;
		 // Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
		 for (int i=0; i<5; i++) //
		 {
			 if ((adccommand & 0x80) != 0x0) // 0x80 = 0&10000000
				 mosiOutput.high();
			 else
				 mosiOutput.low();
			 
			 
			 adccommand <<= 1;      
			 clockOutput.high();
			 clockOutput.low();      
		 }

		 int adcOut = 0;
		 for (int i=0; i<12; i++) // Read in one empty bit, one null bit and 10 ADC bits
		 {
			 clockOutput.high();
			 clockOutput.low();      
			 adcOut <<= 1;

			 if (misoInput.isHigh())
			 {
				 //      System.out.println("    " + misoInput.getName() + " is high (i:" + i + ")");
				 // Shift one bit on the adcOut
				 adcOut |= 0x1;
			 }
			
		 }
		 chipSelectOutput.high();

		 adcOut >>= 1; // Drop first bit
			 return adcOut;
	 }

	 private static String lpad(String str, String with, int len)
	 {
		 String s = str;
		 while (s.length() < len)
			 s = with + s;
		 return s;
	 }

	 public static int getADC()
	 {
		 return adc;
	 }

	 @Override
	 public void run() {
		 try {
			 GpioController gpio = ListenGpio.gpio;
			 mosiOutput       = gpio.provisionDigitalOutputPin(spiMosi, "MOSI", PinState.LOW);
			 clockOutput      = gpio.provisionDigitalOutputPin(spiClk,  "CLK",  PinState.LOW);
			 chipSelectOutput = gpio.provisionDigitalOutputPin(spiCs,   "CS",   PinState.LOW);
			 misoInput        = gpio.provisionDigitalInputPin(spiMiso, "MISO");

			 Runtime.getRuntime().addShutdownHook(new Thread()
			 {
				 public void run()
				 {
					 System.out.println("Shutting down.");
					 go = false;
				 }
			 });
			 int lastRead  = 0;
			 int tolerance = 5;
			 while (go)
			 {
				 Thread.sleep(500);
				 boolean trimPotChanged = false;
				 adc = readAdc()/3;
				 System.out.println(" Soldeir adc:"+ adc);
				 int postAdjust = Math.abs(adc - lastRead);
				 System.out.println("1");
				 if (postAdjust > tolerance)
				 {
					 trimPotChanged = true;
					 int volume = (int)(adc / 10.23); // [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
					 System.out.println("Volume:" + volume + "%");
					 lastRead = adc/3;
				 }
				 try { Thread.sleep(100L); } catch (InterruptedException ie) { ie.printStackTrace(); }
			 }
			 System.out.println("Bye...");
			 gpio.shutdown();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

 }


