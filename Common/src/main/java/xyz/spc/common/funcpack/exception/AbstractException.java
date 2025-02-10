package xyz.spc.common.funcpack.exception;

import lombok.Getter;
import org.springframework.util.StringUtils;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.errorcode.ServerError;

import java.util.Optional;

/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 */
@Getter
public abstract class AbstractException extends RuntimeException {

    public final String errorCode;

    public final String errorMessage;

    public AbstractException(String message, Throwable throwable, ClientError errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.getCode();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.getMessage());
    }

    public AbstractException(String message, Throwable throwable, ServerError errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.getCode();
        this.errorMessage = Optional.ofNullable(StringUtils.hasLength(message) ? message : null).orElse(errorCode.getMessage());
    }

}
