import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;

import com.gps.reader.GPSReader;
import com.project.webserver.ADCReader;
import com.project.webserver.HTTPServer;
import com.project.webserver.ListenGpio;
import com.project.webserver.ListenGpioEmergency;


public class Main {

	public static void main(String[] args) {

		HTTPServer httpServer = new HTTPServer();
	    new Thread(new ADCReader()).start();
	    new Thread(new ListenGpio()).start();
	    new Thread(new ListenGpioEmergency()).start();
	
		
		try {
			
			httpServer.start();
			System.out.println("Server started");
			System.out.println("ADCReader started");
			System.out.println("ListenGpio started");
			System.out.println("ListenGpioEmergency started");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("press any number to exit");
		GPSReader gps = new GPSReader();
		
		BufferedInputStream br = new BufferedInputStream(System.in);
    	byte [] arr = new byte[1];
    	try {
			br.read(arr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		/*
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		
		int n = reader.nextInt(); // Scans the next token of the input as an int.
*/		
		
	}


