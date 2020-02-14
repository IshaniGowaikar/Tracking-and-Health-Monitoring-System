package com.project.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;



import com.Bean.SoldierBean;
import com.Bean.Soldier_Value_Bean;
import com.database.DataBaseHelper;
import com.gps.reader.GPSReader;
import com.java.SMSApi.SMSApi;



public class HTTPServer extends MyHTTP {

	//DataBaseHelper db = new DataBaseHelper();

	public static boolean isChecked = true;
	boolean flag=false;
	public boolean isLogin = false;
	public static String emergencyStatus;
	String innerFolderName = File.separator;
	//ADCReader reader=new ADCReader();
	int patientdoctorid=0;
	int patientbedid=0;
	int pid=0;
	//ListenGpio listenGpio=new ListenGpio();

	private final String REFRESH	="<meta http-equiv=\"refresh\" content=\"4\">";

	//for project menu navigation without login
	private final String NAVIGATION_WITHOUT_LOGIN="<div id=\"menu\">"+
			"<ul>"+
			"<li class=\"current_page_item\"><a href=\"/HTTPServer/index.html\">Home</a></li>"+
			"<li><a href=\"/HTTPServer/login\">Login</a></li>"+
			"<li><a href=\"/HTTPServer/About\">About Us</a></li>"+
			"</ul>"+
			"</div>";

	//for project menu navigation with login
	private final String NAVIGATION_WITH_LOGIN="<div id=\"menu\">"+
			"<ul>"+
			"<li class=\"current_page_item\"><a href=\"/HTTPServer/index.html\">Home</a></li>"+
			"<li><a href=\"/HTTPServer/registration\">Soldier</a></li>"+
			"<li><a href=\"/HTTPServer/SensorDetails\">Soldier Values</a></li>"+
			"<li><a href=\"/HTTPServer/logout\">Logout</a></li>"+
			"</ul>"+
			"</div>";

	//for project header with login
	private final String HEADER_WITH_LOGIN = "<div id=\"wrapper\"><div id=\"header-wrapper\" class=\"container\"><div id=\"header\" class=\"container\">"+
			"<div id=\"logo\"><h1><right><a href=\"#\">Soldier Monitoring</a></right></h1></div>"+
			NAVIGATION_WITH_LOGIN+
			"</div>"+
			"<div><img src=\"/HTTPServer/files/images/img03.png\" width=\"1000\" height=\"40\" /></div>"+
			"</div></div>";

	//for project header without login

	private final String HEADER_WITHOUT_LOGIN = "<div id=\"wrapper\"><div id=\"header-wrapper\" class=\"container\"><div id=\"header\" class=\"container\">"+
			"<div id=\"logo\"><h1><right><a href=\"#\">Soldier Monitoring</a></right></h1></div>"+
			NAVIGATION_WITHOUT_LOGIN+
			"</div>"+
			"<div><img src=\"/HTTPServer/files/images/img03.png\" width=\"1000\" height=\"40\" /></div>"+
			"</div></div>";



	//adding footer on the page
	private final String FOOTER ="<div id=\"footer-content\"></div>"+
			"<div id=\"footer\">"+
			"<p>Copyright (c)2017 Soldier Tracking and Monitoring All rights reserved</p>"+
			"</div>";


	public HTTPServer() {
		super(8088);

	}

	@Override
	public Response serve(IHTTPSession session) {

		//session for getting current uri
		Method method = session.getMethod();
		String uri = session.getUri();
		System.out.println(method + " '" + uri + "' ");
		System.out.println( method + " '" + uri + "' ");
		String[] uriArray = uri.split("/");
		System.out.println( "uri array length :" + uriArray.length);
		System.out.println( "after array length");

		//first index page
		if (uri.equalsIgnoreCase("/HTTPServer/")
				|| uri.equalsIgnoreCase("/HTTPServer")
				|| uri.equalsIgnoreCase("/HTTPServer/index.html")
				|| uri.equalsIgnoreCase("/HelloServer/index.htm")) {



			isLogin = false;
			System.out.println( "set to false islogin == false");
			System.out.println( "inside  HTTPServer");
			String msg="<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
					"<head>"+
					"<meta name=\"keywords\" content=\"\"/>"+
					"<meta name=\"description\" content=\"\" />"+
					"<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
					"<link href='http://fonts.googleapis.com/css?family=Abel' rel='stylesheet' type='text/css'/>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"</head>"+

			"<body>"+

			HEADER_WITHOUT_LOGIN+
			"<div id=\"page\">"+
			"<div id=\"content\">"+
			"<h2 class=\"title\">Welcome</h2>"


			+"</div>"
			+"<div class=\"container\"><img src=\"/HTTPServer/files/images/img03.png\" width=\"1000\" height=\"40\" alt=\"\" /></div>"+
			"</div>"+
			FOOTER+

			"</body>"+
			"</html>";


			return new MyHTTP.Response(msg);
		}

		//function for validating admin's username and password
		else if (uri.equalsIgnoreCase("/HTTPServer/validateLogin")) {
			Map<String, String> parms = session.getParms();
			System.out.println("username :" + parms.get("username") + " password:"
					+ parms.get("password"));
			String msg = "";
			if (parms.get("username").equalsIgnoreCase("admin")
					&& parms.get("password").equalsIgnoreCase("admin")) {
				isLogin = true;
				System.out.println("set to false islogin == false");

				msg = "<html>"
						+"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+

						"<body>"
						+HEADER_WITH_LOGIN
						+"<div id=\"page\">"
						+"<div id=\"content\">"
						+"<h1>Welcome Admin</h1>"
						+"<div align=\"center\">"
						+ "<table border=\"1\" align=\"center\">"
						//+"<tr>"+"<th>Sensor-1</th>"+"<th>Sensor-2</th>"+"<th>Patient_Id</th>"+"<th>Bed_Id</th>"+"</tr>"+
						//"<tr>"+"<td>"+ADCReader.getADC()+"</td>"+"<td>"+ListenGpio.PinState+"</td>"+"<td>"+pid+"</td>"+"<td>"+patientbedid+"</td>"+"</tr>"+

						+"</div>"
						+ "</table>"
						+ "</div>"
						+ "<div data-role=\"page\" data-theme=\"b\">"
						+ "</br>" + "</br>" + "</br>"
						+

						"<div id=\"SelectClientType\" align=\"center\">"
						+ "</div>"  + "</div>"+ "</div>" + "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"+ FOOTER+"</body>" + "</html>";

			}


			//if uname and password is invalid it will again display login page
			else {
				isLogin = false;
				msg = 	"<html>"+
						"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
						"<body>"
						+HEADER_WITHOUT_LOGIN
						+"<div align=\"center\">"
						+ "<form name=\"frmNewClient\" action=\"/HTTPServer/validateLogin\" method=\"GET\">"
						+ "<table border=\"0\" align=\"center\">"
						+ "<tr>"
						+ "<h1><center>Login</center></h1>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Enter User Name:</td>"
						+ "<td><input type=\"text\" id=\"txtUserName\" name=\"username\" placeholder=\"User Name\" required=\"required\"/></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Enter Password:</td>"
						+ "<td><input type=\"password\" id=\"txtPassword\" name=\"password\" placeholder=\"Password\" required=\"required\"/></td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>"
						+"<input type=\"submit\" id=\"btnSubmit\" value=\"Submit\"/></td>"
						+ "<td><input type=\"button\" id=\"btnCancel\" value=\"Cancel\"/></td>"
						+ "</tr>"
						+ "</table>"
						+ "</form>"
						+"</div>"
						+"<br>"
						+"<br>"
						+"<br>"

						+FOOTER

						+"</body>"+
						"</html>";

			}

			return new MyHTTP.Response(msg);

		}	



		//files for proper GUI
		else if (uri.startsWith("/HTTPServer/files")) {

			String filePath="/home/pi/Desktop/"+uri.substring(1);

			System.out.println("in files"+filePath);
			System.out.println("hello :" +uri.substring(1));
			InputStream fin = null;
			try {
				fin =  new FileInputStream(new File(filePath));
			} catch (Exception ex) {
				ex.printStackTrace();
				//Logger.getLogger(HelloServer.class.getName()).log(Level.SEVERE, null, ex);
			} 
			return new Response(Response.Status.OK,MIME_PLAINTEXT,fin);

		}

		//login page admin login
		else if(uri.startsWith("/HTTPServer/login"))
		{

			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITHOUT_LOGIN
					+"<div align=\"center\">"
					+ "<form name=\"frmNewClient\" action=\"/HTTPServer/validateLogin\" method=\"GET\">"
					+ "<table border=\"0\" align=\"center\">"
					+ "<tr>"
					+ "<h1><center>Login</center></h1>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Enter User Name:</td>"
					+ "<td><input type=\"text\" id=\"txtUserName\" name=\"username\" placeholder=\"User Name\" required=\"required\"/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Enter Password:</td>"
					+ "<td><input type=\"password\" id=\"txtPassword\" name=\"password\" placeholder=\"Password\" required=\"required\"/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+

					"<input type=\"submit\" id=\"btnSubmit\" value=\"Submit\"/></td>"
					+ "<td><input type=\"button\" id=\"btnCancel\" value=\"Cancel\"/></td>"
					+ "</tr>"
					+ "</table>"
					+ "</form>"
					+"</div>"
					+"<br>"
					+"<br>"
					+"<br>"

					+FOOTER

					+"</body>"+
					"</html>";


			return new MyHTTP.Response(msg);


		}

		//logout page: it will show index page when admin logout
		else if(uri.startsWith("/HTTPServer/logout"))
		{
			isLogin=false;
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITHOUT_LOGIN
					+"<div align=\"center\">"
					+ "<form name=\"frmNewClient\" action=\"/HTTPServer/validateLogin\" method=\"GET\">"
					+ "<table border=\"0\" align=\"center\">"
					+ "<tr>"
					+ "<h1><center>Login</center></h1>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Enter User Name:</td>"
					+ "<td><input type=\"text\" id=\"txtUserName\" name=\"username\" placeholder=\"User Name\" required=\"required\"/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Enter Password:</td>"
					+ "<td><input type=\"password\" id=\"txtPassword\" name=\"password\" placeholder=\"Password\" required=\"required\"/></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>"
					+

					"<input type=\"submit\" id=\"btnSubmit\" value=\"Submit\"/></td>"
					+ "<td><input type=\"button\" id=\"btnCancel\" value=\"Cancel\"/></td>"
					+ "</tr>"
					+ "</table>"
					+ "</form>"
					+"</div>"
					+"<br>"
					+"<br>"
					+"<br>"

					+FOOTER

					+"</body>"+
					"</html>";


			return new MyHTTP.Response(msg);


		}

		//about us page
		else if(uri.startsWith("/HTTPServer/About"))
		{
			isLogin=false;
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITHOUT_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>Abous Us</center></h1>"
					+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
					+FOOTER

					+"</body>"+
					"</html>";


			return new MyHTTP.Response(msg);


		}

		//registration page: for adding patient
		else if(uri.equalsIgnoreCase("/HTTPServer/registration"))
		{

			isLogin=true;
			System.out.println("new AddSoldierDetails registration");
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"+
					HEADER_WITH_LOGIN+
					"<form name= \"form\"  action=\"/HTTPServer/AddSoldierDetails\" method=\"GET\" onsubmit=\"return validateRegistration(form)\">"+
					"<a href=\"/HTTPServer/VeiwSoldier\"><b><u><center>View All Soldier</center></u></b></a>"+
					"<table border=\"0\" align=center >" +
					"<td>"+
					"<h1><center>Add Soldier Details</center></h1>"+
					"</td>"+
					"<tr>"+
					"<td>Soldier Name:</td><br>"+
					"<td> <input type=\"text\" name=\"pname\" id=\"pname\" \"\"></td>"+
					" <br>"+
					"</tr>"+
					"<tr>"+
					"<td>Mobile no:<br></td>"+
					"<td> <input type=\"text\" name=\"pmobile\"  id=\"pmobile\" \"\"></td>"+
					" <br>"+
					"</tr>"+
					"<tr>"+
					"<td>Blood Group Type:<br></td>"+
					"<td><select name=\"bloodgroup\" id=\"bloodgroup\">" +
					"<option value=\"O +\">O +</option>" +
					"<option value=\"O -\">O -</option>" +
					"<option value=\"A +\">A +</option>" +
					"<option value=\"A -\">A -</option>" +
					"<option value=\"B +\">B +</option>" +
					"<option value=\"B -\">B -</option>" +
					"<option value=\"AB +\">AB +</option>" +
					"<option value=\"AB -\">AB -</option>" +
					"</td>"+
					"<br>"+
					"</tr>"+


							"<tr>"+
							"<br>"+
							"<td><input type=\"submit\" value=\"Add Soldier\"></td>"+
							"</tr>"+
							"</form>"+
							"</body>"+
							"</html>"+

							  //doing validations on all fields
							  "<script>"+
							  "function validateRegistration(form)"+
							  "{"+
							  "if(form.pname.value== \"\")"+
							  "{"+
							  "alert(\"patient name cannot be blank!!\");"+
							  "form.pname.focus();"+
							  "return false;"+
							  "}"+


										"if(form.address.value== \"\")"+
										"{"+
										"alert(\"Address cannot be blank!!\");"+
										"form.address.focus();"+
										"return false;"+
										"}"+	
										"if(form.pmobile.value== \"\")"+
										"{"+
										"alert(\"Enter patient MobileNo!!\");"+
										"form.pmobile.focus();"+
										"return false;"+
										"}"+	


						"}"+
						"</script>";

			return new MyHTTP.Response(msg);


		}
		//getting patient details and adding it to DB
		else if(uri.equalsIgnoreCase("/HTTPServer/AddSoldierDetails"))
		{
			isLogin=true;
			System.out.println("adding Soldier");
			Map<String, String> parms = session.getParms();
			String pname=parms.get("pname");
			System.out.println("pname"+pname);
			String mobile=parms.get("pmobile");
			String bloodgroup=parms.get("bloodgroup");

			//getting all details in soldierBean object
			SoldierBean soldierBean = new SoldierBean();
			soldierBean.setSoldier_Name(pname);
			soldierBean.setSoldier_BloodGroup(bloodgroup);
			soldierBean.setSoldier_MobileNo(mobile);
			System.out.println("adding soldier");
			DataBaseHelper db = new DataBaseHelper();
			//adding details in DB
			db.addSoldier(soldierBean);
			System.out.println("New Soldier succfully added");

			//after adding it will show all Soldier details
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>All Soldier List</center></h1>";

			System.out.println("veiw Soldier");
			ArrayList<SoldierBean>soldierInfoList=(ArrayList<SoldierBean>) db.getAllSoldierInfo();
			System.out.println("showing Soldier");

			msg+="<table>"+
					"<thead>"+
					"<tr>"+
					"<th>Soldier ID</th>"+
					"<th>Soldier Name</th>"+
					"<th>Soldier Blood Group</th>"+
					"<th>Soldier Mobile</th>"+
					"<th>Delete Soldier</th>"+
					"</tr>"+
					"</thead>";
			for(int i=0;i<soldierInfoList.size();i++)
			{
				int soldier_id=soldierInfoList.get(i).getSoldier_Id();
				String soldier_name =soldierInfoList.get(i).getSoldier_Name();
				String soldier_bloodgroup =soldierInfoList.get(i).getSoldier_BloodGroup();
				String soldier_mobile =soldierInfoList.get(i).getSoldier_MobileNo();
				msg+="<tbody>"+
						"<tr>"+
						"<td><center>"+soldier_id+"</center></td>"+
						"<td><center>"+soldier_name+"</center></td>"+
						"<td><center>"+soldier_bloodgroup+"</center></td>"+
						"<td><center>"+soldier_mobile+"</center></td>"+

								//showing dialog box when admin wants to delete user and it will deleted by patientid
								"<td><center><a href=\"/HTTPServer/DeleteSoldier?id="+soldier_id+ "\" onClick=\"return confirm('Are you sure want to delete')\"> Delete</a></center></td>"+
								"</tr>";
			}
			msg+="</tbody>"+
					"</table>"+

							"</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
							+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"

							+"</body>"+
							"</html>";


			return new MyHTTP.Response(msg);



		}

		//view patient page: if admin wants to veiw all Soldier
		else if(uri.equalsIgnoreCase("/HTTPServer/VeiwSoldier"))
		{
			isLogin=true;
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>All Soldier List</center></h1>";

			//getting list of all users from DB
			System.out.println("veiw Soldier");
			DataBaseHelper db = new DataBaseHelper();
			ArrayList<SoldierBean>soldierInfoList=(ArrayList<SoldierBean>) db.getAllSoldierInfo();
			System.out.println("showing Soldier");

			//displaying column headings 
			msg+="<table>"+
					"<thead>"+
					"<tr>"+
					"<th>Soldier ID</th>"+
					"<th>Soldier Name</th>"+
					"<th>Soldier Blood Group</th>"+
					"<th>Soldier Mobile</th>"+
					"<th>Delete Soldier</th>"+
					"</tr>"+
					"</thead>";

			//loop for getting user one by one
			for(int i=0;i<soldierInfoList.size();i++)
			{
				int soldier_id=soldierInfoList.get(i).getSoldier_Id();
				String soldier_name =soldierInfoList.get(i).getSoldier_Name();
				String soldier_bloodgroup =soldierInfoList.get(i).getSoldier_BloodGroup();
				String soldier_mobile =soldierInfoList.get(i).getSoldier_MobileNo();
				msg+="<tbody>"+
						"<tr>"+
						"<td><center>"+soldier_id+"</center></td>"+
						"<td><center>"+soldier_name+"</center></td>"+
						"<td><center>"+soldier_bloodgroup+"</center></td>"+
						"<td><center>"+soldier_mobile+"</center></td>"+

								//showing dialog box when admin wants to delete user and it will deleted by patientid
								"<td><center><a href=\"/HTTPServer/DeleteSoldier?id="+soldier_id+ "\" onClick=\"return confirm('Are you sure want to delete')\"> Delete</a></center></td>"+
								"</tr>";
			}
			msg+="</tbody>"+
					"</table>"+

							"</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
							+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"

							+"</body>"+
							"</html>";


			return new MyHTTP.Response(msg);

		}


		//deleting patient
		else if(uri.startsWith("/HTTPServer/DeleteSoldier"))
		{

			isLogin=true;
			Map<String, String> parms = session.getParms();

			//getting id of perticular soldier
			String id=parms.get("id");
			System.out.println("id=="+id);
			DataBaseHelper db=new DataBaseHelper();

			db.deleteSoldier(id);

			//again showing all users after deleting
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>All Soldier List</center></h1>";

			System.out.println("veiw Soldier");
			ArrayList<SoldierBean>soldierInfoList=(ArrayList<SoldierBean>) db.getAllSoldierInfo();
			System.out.println("showing Soldier");

			msg+="<table>"+
					"<thead>"+
					"<tr>"+
					"<th>Soldier ID</th>"+
					"<th>Soldier Name</th>"+
					"<th>Soldier Blood Group</th>"+
					"<th>Soldier Mobile</th>"+
					"<th>Delete Soldier</th>"+
					"</tr>"+
					"</thead>";

			//loop for getting user one by one
			for(int i=0;i<soldierInfoList.size();i++)
			{
				int soldier_id=soldierInfoList.get(i).getSoldier_Id();
				String soldier_name =soldierInfoList.get(i).getSoldier_Name();
				String soldier_bloodgroup =soldierInfoList.get(i).getSoldier_BloodGroup();
				String soldier_mobile =soldierInfoList.get(i).getSoldier_MobileNo();
				msg+="<tbody>"+
						"<tr>"+
						"<td><center>"+soldier_id+"</center></td>"+
						"<td><center>"+soldier_name+"</center></td>"+
						"<td><center>"+soldier_bloodgroup+"</center></td>"+
						"<td><center>"+soldier_mobile+"</center></td>"+

								//showing dialog box when admin wants to delete user and it will deleted by soldierid
								"<td><center><a href=\"/HTTPServer/DeleteSoldier?id="+soldier_id+ "\" onClick=\"return confirm('Are you sure want to delete')\"> Delete</a></center></td>"+
								"</tr>";
			}
			msg+="</tbody>"+
					"</table>"+

							"</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
							+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"

							+"</body>"+
							"</html>";


			return new MyHTTP.Response(msg);
		}

		else if(uri.startsWith("/HTTPServer/SensorDetails"))
		{
			String currentDate = null;
			String currentTime = null;
			DateFormat f = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			Date date1 = new Date();
			try {
				date1 = f.parse(f.format(date1));
				DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat time = new SimpleDateFormat("hh:mm:ss");
				currentDate =date.format(date1);
				currentTime  =time.format(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					REFRESH;

			msg+="<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"+
					"<a href=\"/HTTPServer/CurrentSnap\"><b><u><center>Current SnapShot</center></u></b></a>"+
					"</br>" +
					"</br>" +
					"<a href=\"/HTTPServer/VeiwAllVaules\"><b><u><center>View All Soldier Values</center></u></b></a>"+
					"</br>" +
					"</br>" +
					"<h1><center>Current Values</center></h1>"+
					"<table border=\"1\" align=\"center\">"+
					"<tr>"+
					"<th>Soldier_Id</th>"+
					"<th>Soldier Body Temp</th>"
					+"<th>Soldier Heartbeats</th>"+
					"<th>Soldier lat</th>"+
					"<th>Soldier Long</th>"+
					"<th>Date</th>"+
					"<th>Time</th>"+
					"<tr>"+
					"<td>"+1+"</td>"+
					"<td>"+ADCReader.getADC()+"</td>"+
					"<td>"+ListenGpio.Count+"</td>"+
					"<td>"+GPSReader.getLat()+
					"</td>"+
					"<td>"+GPSReader.getLong()+
					"</td>"+
					"<td>"+currentDate+
					"</td>"+
					"<td>"+currentTime+
					"</td>"+
					"</tr>"+
					"</div>"
					+ "</table>";
			Soldier_Value_Bean value_Bean= new Soldier_Value_Bean();
			value_Bean.setSoldier_Lat(GPSReader.getLat());
			value_Bean.setSoldier_Long(GPSReader.getLong());
			value_Bean.setSoldier_Temp(String.valueOf(ADCReader.getADC()));
			value_Bean.setSoldier_HeartRate(String.valueOf(ListenGpio.Count));
			value_Bean.setDate(currentDate);
			value_Bean.setTime(currentTime);
			value_Bean.setSoldier_id(1);
			DataBaseHelper baseHelper = new DataBaseHelper();
			int id=baseHelper.addValues(value_Bean);
			System.out.println("Value Id"+id);
			msg+="</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"+
					"<h1><center>Emergency Status:  "+emergencyStatus+"</center></h1>"+
					"</form>"
					+"</div>"
					+"<br>"
					+"<br>"
					+"<br>"
					+FOOTER
					+"</body>"+
					"</html>";


			return new MyHTTP.Response(msg);


		}

		//view doctor page: if admin wants to veiw all Values
		else if(uri.equalsIgnoreCase("/HTTPServer/VeiwAllVaules"))
		{
			isLogin=true;
			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+
					"<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>All Values List</center></h1>";

			System.out.println("veiw values");
			DataBaseHelper baseHelper= new DataBaseHelper();
			//getting list of all values from DB
			ArrayList<Soldier_Value_Bean>SoldierValueList=(ArrayList<Soldier_Value_Bean>) baseHelper.getAllSoldierValue();
			System.out.println("showing values");

			//displaying column headings 
			msg+="<table>"+
					"<thead>"+
					"<tr>"+
					"<th>Soldier ID </th>"+
					"<th>Soldier Lat</th>"+
					"<th>Soldier Long</th>"+
					"<th>Soldier Temp</th>"+
					"<th>Soldier HeartRate</th>"+
					"<th>Date</th>"+
					"<th>Time</th>"+
					"</tr>"+
					"</thead>";

			//loop for getting user one by one
			for(int i=0;i<SoldierValueList.size();i++)
			{
				int soldierid=SoldierValueList.get(i).getSoldier_id();
				String lat =SoldierValueList.get(i).getSoldier_Lat();
				String longi =SoldierValueList.get(i).getSoldier_Long();
				String temp =SoldierValueList.get(i).getSoldier_Temp();
				String heartrate =SoldierValueList.get(i).getSoldier_HeartRate();
				String date =SoldierValueList.get(i).getDate();
				String time =SoldierValueList.get(i).getTime();
				//displaying values details in one row
				msg+="<tbody>"+
						"<tr>"+
						"<td><center>"+soldierid+"</center></td>"+
						"<td><center>"+lat+"</center></td>"+
						"<td><center>"+longi+"</center></td>"+
						"<td><center>"+temp+"</center></td>"+
						"<td><center>"+heartrate+"</center></td>"+
						"<td><center>"+date+"</center></td>"+
						"<td><center>"+time+"</center></td>"+
						"</tr>";
			}
			msg+="</tbody>"+
					"</table>"+

							"</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
							+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"

							+"</body>"+
							"</html>";


			return new MyHTTP.Response(msg);
		}

		//showing image
		else if(uri.startsWith("/HTTPServer/CurrentSnap"))
		{

			isLogin=true;
			String Path="/home/pi/Desktop/HTTPServer/files/images";
			try {
				Runtime.getRuntime().exec("fswebcam /home/pi/Desktop/HTTPServer/files/images/currentSnap.jpg");
				System.out.println("Picture Captured");
				/*File toHideFile = new File("/home/pi/Desktop/download.jpg");
				byte byteMessage [] = new byte[(int)toHideFile.length()];


				//Read file data from hide location
				FileInputStream fin = new FileInputStream(toHideFile);
				int byteToRead = 0;
				while(byteToRead < byteMessage.length)
				{
					byteToRead = byteToRead + fin.read(byteMessage, byteToRead, byteMessage.length - byteToRead);					
				}
				fin.close();
				System.out.println("Shyam"+byteMessage.length);
				String pathToWrite="/home/pi/Desktop/HTTPServer/files/images/download.jpg";
				System.out.println("Writing image: "+pathToWrite);
				FileOutputStream fout = new FileOutputStream(pathToWrite);
				fout.write(byteMessage);
				fout.flush();
				fout.close();*/

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			String msg = "<html>"+
					"<link href=\"/HTTPServer/files/images/style.css\" rel=\"stylesheet\" type=\"text/css\" media=\"screen\" />"+REFRESH+
					"<body>"
					+HEADER_WITH_LOGIN
					+"<div align=\"center\">"
					+ "<h1><center>Current Snap Shot</center></h1>"+
					"<div><img src=\"/HTTPServer/files/images/currentSnap.jpg\" width=\"450\" height=\"450\" />" +
					"</div>";
			System.out.println("showing image");


			msg+="</tbody>"+
					"</table>"+

							"</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"
							+ "</br>" + "</br>" + "</br>"+"</br>" + "</br>" + "</br>"

							+"</body>"+
							"</html>";


			return new MyHTTP.Response(msg);
		}


		else {

			String msg = "<html><body><h1>HTTP SERVER ERROR\"</h1>\n";

			msg += "</body></html>\n";

			return new MyHTTP.Response(msg);
		}
	}

}
