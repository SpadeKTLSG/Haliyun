package xyz.spc.common.funcpack.commu.exception;


import java.util.Optional;

/**
 * 远程服务调用异常
 */
public class RemoteException extends AbstractException {

    public RemoteException(ErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public RemoteException(String message) {
        this(message, null, ErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, ErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, ErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.getMessage()), throwable, errorCode);
    }

    public static void cast(String message) {
        throw new RemoteException(message);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
