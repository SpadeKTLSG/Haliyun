package xyz.spc.common.funcpack.commu.exception;


import xyz.spc.common.funcpack.commu.errorcode.ClientError;

import java.util.Optional;

/**
 * 客户端异常
 */
public class ClientException extends AbstractException {

    public ClientException(ClientError errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, ClientError.CLIENT_ERROR);
    }

    public ClientException(String message, ClientError errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, ClientError errorCode) {
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
