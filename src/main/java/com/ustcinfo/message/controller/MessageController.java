package com.ustcinfo.message.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustcinfo.message.dao.MessageDao;
import com.ustcinfo.message.enity.Message;

@Controller
public class MessageController {
	private MessageDao md = new MessageDao();

	// 查询短信信息
	@ResponseBody
	@RequestMapping(value = "querymessage", produces = { "application/json;charset=UTF-8" })
	public String Value001() throws SQLException {
		int have_sending_nums = md.today_has_been_sent(); // 当天发送短信数量
		int max_send_nums = md.Transmission_threshold(); // 短信量发送最大值
		String phone_number = md.getString("select telephone from ods_base_sms_conf where id =2");

		List<Message> lists = md.listAllSms();
		System.out.println(lists);

		// 当天短信发送量超过发送阙值时，向维护人员发送告警信息。
		if (have_sending_nums < max_send_nums && (max_send_nums - have_sending_nums) >= lists.size()) {
			return lists.toString();
		} else if (lists.size() > 0) {
			return "[{sms_id:'999', sms_type:'999', sms_sys_id:'null', send_no:'" + phone_number
					+ "', sms_note:'今天短信量超过发送阙值!!!'}," + lists.toString().substring(1);
		} else {
			return "[]";
		}

	}

	// 更新短信信息
	@ResponseBody
	@RequestMapping(value = "updatemessage", produces = { "application/json;charset=UTF-8" })
	public String Value003(@RequestParam("sms_id") String sms_id, @RequestParam("send_no") String send_no,
			@RequestParam("send_time") String send_time) throws SQLException {
		String result = "失败";
		System.out.println(sms_id + " " + send_no + " " + send_time);
		result = md.updateAllSms(sms_id, send_no, send_time);
		return result;
	}
}
