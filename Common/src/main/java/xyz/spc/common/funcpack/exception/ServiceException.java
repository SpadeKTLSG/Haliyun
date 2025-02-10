package xyz.spc.common.funcpack.exception;


import xyz.spc.common.funcpack.errorcode.ServerError;

import java.util.Optional;

/**
 * 服务端异常
 */
public class ServiceException extends AbstractException {

    public ServiceException(ServerError errorCode) {
        this(null, null, errorCode);
    }

    public ServiceException(String message) {
        this(message, null, ServerError.SERVICE_ERROR);
    }


    public ServiceException(String message, ServerError errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, ServerError errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.getMessage()), throwable, errorCode);
    }

    public static void cast(String message) {
        throw new ServiceException(message);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
