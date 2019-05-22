package com.tang.to;

/**
 * 消息类<br>
 * 用于向前端返回消息
 * 
 * @author Administrator
 * @date 2019年4月19日
 */
public class CommonMessage {
	// 状态码
	private Integer code;
	// 状态
	private Boolean status;
	// 消息
	private String message;

	public CommonMessage() {
		super();
	}

	public CommonMessage(Integer code, Boolean status, String message) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CommonMessage [code=" + code + ", status=" + status + ", message=" + message + "]";
	}

}
