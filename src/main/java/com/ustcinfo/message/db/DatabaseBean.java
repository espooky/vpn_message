package com.ustcinfo.message.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ustcinfo.message.enity.Message;

public class DatabaseBean {

	private String DBDriver = "com.mysql.jdbc.Driver";
	private String DBLocation = "jdbc:mysql://10.10.121.102:3306/upp?user=datauser&password=KqN9m9Jhev3s5vYZ&useUnicode=true&characterEncoding=utf-8";
	private Connection conn = null;

	public DatabaseBean() {
	}
	// 通过set方法可以灵活设置数据库的连接
	public void setDBLocation(String location) {
		DBLocation = location;
	}

	public void setDBDriver(String driver) {
		DBDriver = driver;
	}

	public void setconn(Connection conn) {
		this.conn = conn;
	}

	public String getDBLocation() {
		return (DBLocation);
	}

	public String getDBDriver() {
		return (DBDriver);
	}

	public Connection getconn() {
		return (conn);
	}

	/*
	 * public String DBConnect(){} public String DBDisconnect(){} public
	 * ResultSet query(String sql){} public int getTotalPage(String sql,int
	 * pageSize){} public ResultSet getPagedRs(String sql,int pageSize,int
	 * pageNumber){} public String execute_sql(String sql){}
	 */
	// 建立连接
	@Autowired
	public String DBConnect() {
		String strExc = "Success!";// strExc默认为Success，如果有例外抛出，即数据库连接不成功，则下面几个catch中被赋予其他抛出信息
		try {
			Class.forName(DBDriver);
			conn = DriverManager.getConnection(DBLocation);
		} catch (ClassNotFoundException e) {
			strExc = "数据库驱动没有找到，错误提示：<br>" + e.toString();
		} catch (SQLException e) {
			strExc = "sql语句错误，错误提示<br>" + e.toString();
		} catch (Exception e) {
			strExc = "错误提示：<br>" + e.toString();
		}
		return (strExc);
	}

	// then end of DBConnect
	// 断开连接
	public String DBDisconnect() {
		String strExc = "Success!";// strExc默认为Success，如果有例外抛出，即数据库断开连接不成功，则下面几个catch中被赋予其他抛出信息
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			strExc = e.toString();
		}
		return (strExc);
	}

	// 通过传入sql语句来返回一个结果集
	public ResultSet query(String sql) throws SQLException, Exception {
		ResultSet rs = null;
		Statement st = null;
		if (conn == null) {
			DBConnect();
		}
		if (conn == null) {
			rs = null;
		} else {
			try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
			} catch (SQLException e) {
				throw new SQLException("Cound not execute query.");
			} catch (Exception e) {
				throw new Exception("Cound not execute query.");
			} 
		}
		return (rs);
	}

	public String execute_sql(String sql) throws SQLException {
		String strExc;
		strExc = "Success!";
		PreparedStatement ps = null;
		if (conn != null) {
			try {

				ps = conn.prepareStatement(sql);
				ps.execute();
			} catch (SQLException e) {
				strExc = e.toString();
			} catch (Exception e) {
				strExc = e.toString();
			} 
		} else {
			strExc = "Connection Lost!";
		}
		return (strExc);
	}

}
