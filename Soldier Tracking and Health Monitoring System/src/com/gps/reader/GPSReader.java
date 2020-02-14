package com.gps.reader;

import java.io.IOException;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
public class GPSReader {
	public static String lattitude ="18.475276";
	public static String longitude="73.823168";
	Serial serial =null;
	static String gpsDataline = "";
	static boolean appendFlag = false;
	public GPSReader() {
		// TODO Auto-generated constructor stub
		serial = new Serial();
		serial.registerInputCallback(new SerialDataEventListener() {
			@Override
			public void dataReceived(SerialDataEvent arg0) 
			{
				try {
					if(appendFlag == true)
					{
						gpsDataline += arg0.getAsciiString();
						if(gpsDataline.substring(2).contains("$"))
						{
							//System.out.print(gpsDataline);

							//Extract Lat Long Here...

							String dataArr[]= gpsDataline.split(",");
							if (dataArr.length > 6)
							{
								lattitude = dataArr[4];
								longitude=dataArr [6];
								System.out.println("lat: "+lattitude+" and long are: "+longitude);
							}
							appendFlag = false;
							gpsDataline = "";
						}
					}
					if(arg0.getAsciiString().contains("$GPRMC"))
					{
						gpsDataline = arg0.getAsciiString();
						appendFlag = true;	
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		serial.openConnection();
	}
	public static String getLat()
	{
		return lattitude;
	}
	public static String getLong()
	{
		return longitude;
	}
}

