package xyz.spc.common.funcpack.commu.exception;


import java.util.Optional;

/**
 * 客户端异常
 */
public class ClientException extends AbstractException {

    public ClientException(ErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, ErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, ErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, ErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.getMessage()), throwable, errorCode);
    }

    public static void cast(String message) {
        throw new ClientException(message);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
