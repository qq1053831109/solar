package org.solar.exception;
/**
 * Created by xianchuanwu on 2017/9/26.
 */
public class SolarRuntimeException extends RuntimeException {
    public SolarRuntimeException() {
    }

    public SolarRuntimeException(String message) {
        super(message);
    }

    public SolarRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SolarRuntimeException(Throwable cause) {
        super(cause);
    }

    public SolarRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}