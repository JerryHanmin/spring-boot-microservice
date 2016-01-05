package com.hm.sample.model;

import java.util.Map;
import java.util.UUID;

public class MessageRequest {

	private String type;

	private String from;

	private Map<String, String> detail;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
	}

}
