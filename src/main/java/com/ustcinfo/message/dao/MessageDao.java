package com.ustcinfo.message.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ustcinfo.message.db.DatabaseBean;
import com.ustcinfo.message.enity.Message;

public class MessageDao {
	DatabaseBean db = new DatabaseBean();

	public List<Message> listAllSms() throws SQLException {
		db.DBConnect();
		String sql = "select * from upp.ods_base_sms where send_no!='' and sms_state=0 and create_time>=DATE_SUB(NOW(),interval '01:00:00' hour_second)";// 查询待发送的人员的全部信息
		List<Message> listsMessage = new ArrayList<Message>();
		ResultSet rs = null;
		try {
			rs = db.query(sql);
			while (rs.next()) {
				Message message = new Message();
				message.setSms_id(rs.getString(1));// 短信编码
				message.setSms_type(rs.getString(2));// 短信类别
				message.setSms_sys_id(rs.getString(3));// 源短信编码
				message.setSend_no(rs.getString(4));// 发送号码
				message.setSms_note(rs.getString(5));// 短信内容
				listsMessage.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.DBDisconnect();
		}
		return listsMessage;
	}

	public int today_has_been_sent() throws SQLException {
		db.DBConnect();
		// 查询当天发送信息总数
		String sql = "select count(1) from upp.ods_base_sms where left(send_time,10)=left(now(),10)";
		ResultSet rs = null;
		int result = 0;
		try {
			rs = db.query(sql);
			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.DBDisconnect();
		}
		return result;
	}

	// 短信发送阙值
	public int Transmission_threshold() throws SQLException {
		db.DBConnect();
		// 查询当天发送信息总数
		String sql = "select num from ods_base_sms_conf where id =1";
		ResultSet rs = null;
		int result = 0;
		try {
			rs = db.query(sql);
			while (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.DBDisconnect();
		}
		return result;
	}

	public String updateAllSms(String sms_id, String send_no, String send_time) throws SQLException {
		db.DBConnect();
		String result = null;
		// 查询待发送的人员的全部信息
		String sql = "UPDATE upp.ods_base_sms SET sms_state=1," + "send_time='" + send_time + "' " + "WHERE sms_id='"
				+ sms_id + "'" + "and send_no='" + send_no + "'" + " and sms_state=0";
		result = db.execute_sql(sql);
		db.DBDisconnect();
		return result;
	}

	public String getString(String sql) throws SQLException {
		db.DBConnect();
		String result = null;
		result = db.execute_sql(sql);
		db.DBDisconnect();
		return result;
	}

	public static void main(String[] args) throws Exception {
		try {
			List<Message> list = new MessageDao().listAllSms();
			for (Message l : list) {
				System.out.println(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MessageDao().today_has_been_sent();
	}
}
