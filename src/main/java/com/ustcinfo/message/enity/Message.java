package com.ustcinfo.message.enity;

public class Message {
	private String sms_id;
	private String sms_type;
	private String sms_sys_id;
	private String send_no;
	private String sms_note;

	@Override
	public String toString() {
		return "{sms_id:'" + sms_id + "', sms_type:'" + sms_type
				+ "', sms_sys_id:'" + sms_sys_id + "', send_no:'" + send_no
				+ "', sms_note:'" + sms_note + "'}";
	}

	public String getSms_type() {
		return sms_type;
	}

	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}

	public String getSms_sys_id() {
		return sms_sys_id;
	}

	public void setSms_sys_id(String sms_sys_id) {
		this.sms_sys_id = sms_sys_id;
	}

	public String getSend_no() {
		return send_no;
	}

	public void setSend_no(String send_no) {
		this.send_no = send_no;
	}

	public String getSms_note() {
		return sms_note;
	}

	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
	}

	public String getSms_id() {
		return sms_id;
	}

	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
}
