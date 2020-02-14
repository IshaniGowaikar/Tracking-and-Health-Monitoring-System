package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.Bean.SoldierBean;
import com.Bean.Soldier_Value_Bean;


public class DataBaseHelper {

	Connection conn;
	PreparedStatement statement; //PreparedStatement object for sending SQL statements to the database
	DataBaseConnection db=new DataBaseConnection();

	public int addSoldier(SoldierBean soldierBean)
	{
		
		conn=db.connect();
		
			String query = "insert into soldier_table(soldier_name,soldier_bloodgroup,soldier_MobileNo) values('"
					+ soldierBean.getSoldier_Name()
					+ "','"
					+ soldierBean.getSoldier_BloodGroup()
					+ "','"
					+ soldierBean.getSoldier_MobileNo()
					+"'"
					+ ")";
			try {
				System.out.println(query);
				Statement statement = conn.createStatement();
				statement.executeUpdate(query);
				// find the max number from db....say id
				String squery = "SELECT MAX(soldier_id) FROM soldier_table;";
				ResultSet resultSet = statement.executeQuery(squery);
				resultSet.next();
				int id = resultSet.getInt(1);
				return id;
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
	}
	
	private ResultSet getAllSoldier() {
		
		conn= db.connect();
		String query = "select * from soldier_table";
		ResultSet resultSet = null;
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultSet;
	}
	public ArrayList<SoldierBean> getAllSoldierInfo() {
		ResultSet resultSet = getAllSoldier();
		ArrayList<SoldierBean> soldierInfoList = new ArrayList<SoldierBean>();
		if (resultSet != null) {
			try {
				resultSet.beforeFirst();
				while (resultSet.next()) {
					soldierInfoList.add(getSoldierFromResultSet(resultSet));
				}
				resultSet.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return soldierInfoList;
	}
	
	private SoldierBean getSoldierFromResultSet(ResultSet resultSet) {
		SoldierBean soldierinfo=new SoldierBean();
			try {
			
				soldierinfo.setSoldier_Id(resultSet.getInt("soldier_id"));
				soldierinfo.setSoldier_Name(resultSet.getString("soldier_name"));
				soldierinfo.setSoldier_BloodGroup(resultSet.getString("soldier_bloodgroup"));
				soldierinfo.setSoldier_MobileNo(resultSet.getString("soldier_MobileNo"));
			} catch (Exception e) {
				System.out.println(e);
			}
			return soldierinfo;
		}

	public int deleteSoldier(String s_id)
	{
		conn=db.connect();
		
		try {
			String query = "DELETE from soldier_table where soldier_id = " + s_id + "";
			Statement statement = conn.createStatement();
			return statement.executeUpdate(query);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}	
	
	
	public int addValues(Soldier_Value_Bean value_Bean)
	{
		
		conn=db.connect();
		
			String query = "insert into soldier_value_table(soldier_lat,soldier_long,soldier_temp,soldier_heartrate,date,time, soldier_id) values('"
					+ value_Bean.getSoldier_Lat()
					+ "','"
					+ value_Bean.getSoldier_Long()
					+ "','"
					+ value_Bean.getSoldier_Temp()
					+"','"
					+ value_Bean.getSoldier_HeartRate()
					+"','"
					+ value_Bean.getDate()
					+"','"
					+ value_Bean.getTime()
					+"',"
					+value_Bean.getSoldier_id()
					+ ")";
			try {
				System.out.println(query);
				Statement statement = conn.createStatement();
				statement.executeUpdate(query);
				// find the max number from db....say id
				String squery = "SELECT MAX(id) FROM soldier_value_table;";
				ResultSet resultSet = statement.executeQuery(squery);
				resultSet.next();
				int id = resultSet.getInt(1);
				return id;
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
	}
	
	private ResultSet getAllSoldierValues() {
		
		conn= db.connect();
		String query = "select * from soldier_value_table";
		ResultSet resultSet = null;
		try {
			Statement statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultSet;
	}
	public ArrayList<Soldier_Value_Bean> getAllSoldierValue() {
		ResultSet resultSet = getAllSoldierValues();
		ArrayList<Soldier_Value_Bean> soldiervalueList = new ArrayList<Soldier_Value_Bean>();
		if (resultSet != null) {
			try {
				resultSet.beforeFirst();
				while (resultSet.next()) {
					soldiervalueList.add(getSoldierValuesFromResultSet(resultSet));
				}
				resultSet.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return soldiervalueList;
	}
	
	private Soldier_Value_Bean getSoldierValuesFromResultSet(ResultSet resultSet) {
		Soldier_Value_Bean value_Bean=new Soldier_Value_Bean();
			try {
			
				value_Bean.setValue_Id(resultSet.getInt("id"));
				value_Bean.setSoldier_Lat(resultSet.getString("soldier_lat"));
				value_Bean.setSoldier_Long(resultSet.getString("soldier_long"));
				value_Bean.setSoldier_Temp(resultSet.getString("soldier_temp"));
				value_Bean.setSoldier_HeartRate(resultSet.getString("soldier_heartrate"));
				value_Bean.setDate(resultSet.getString("date"));
				value_Bean.setTime(resultSet.getString("time"));
				value_Bean.setSoldier_id(resultSet.getInt("soldier_id"));
			} catch (Exception e) {
				System.out.println(e);
			}
			return value_Bean;
		}
}


