package xyz.spc.common.funcpack.commu.exception;


import java.util.Optional;

/**
 * 服务端异常
 */
public class ServiceException extends AbstractException {

    public ServiceException(ErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ServiceException(String message) {
        this(message, null, ErrorCode.SERVICE_ERROR);
    }


    public ServiceException(String message, ErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, ErrorCode errorCode) {
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
