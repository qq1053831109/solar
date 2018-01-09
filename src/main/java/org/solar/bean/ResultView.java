package org.solar.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResultView implements Serializable {

	private int code;
	private String msg;
	private Object body;

    private String requestId;

	public ResultView() {
	}

	public ResultView(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResultView(int code, String msg, Object body) {
		this.code = code;
		this.msg = msg;
		this.body = body;
	}

	  public static ResultView success() {
	        return new ResultView(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG);
	    }
	    public static ResultView success(String msg) {
	        return new ResultView(ResultCode.SUCCESS_CODE, msg);
	    }
	    public static ResultView success(Object t) {
	        return new ResultView(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG,t);
	    }

    public static ResultView error() {
        return new ResultView(ResultCode.ERROR_CODE, ResultCode.ERROR_MSG);
    }

	public static ResultView error(String errorMsg) {
		return new ResultView(ResultCode.ERROR_CODE,errorMsg);
	}

	public static ResultView dbError(String errorMsg) {
		return new ResultView(ResultCode.DB_ERROR_CODE,errorMsg);
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public <T>T getBody() {
		return (T)body;
	}
	public void setBody(Object body) {
		this.body = body;
	}

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    @Override
    public String toString() {
        return "JsonResult{" +
                "code:" + code +
                ", msg:" + msg +
                ", body:" + body +
                ", requestId:" + requestId +
                '}';
    }
    
    
}
