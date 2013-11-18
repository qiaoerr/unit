package com.tixa.industry.util;

/**
 * 自定义错误
 * @author shengy
 *
 */
public class TixaException extends Exception{

	private static final long serialVersionUID = -8689266658212033997L;
	
	private int statusCode = -1;
	
    public TixaException(String msg) {
        super(msg);
    }

    public TixaException(Exception cause) {
        super(cause);
    }

    public TixaException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public TixaException(String msg, Exception cause) {
        super(msg, cause);
    }

    public TixaException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
    
    
	public TixaException() {
		super(); 
	}

	public TixaException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public TixaException(Throwable throwable) {
		super(throwable);
	}

	public TixaException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	} 
}
