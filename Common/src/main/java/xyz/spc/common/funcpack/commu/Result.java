package xyz.spc.common.funcpack.commu;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 正确返回码 / -1
     */
    public static final String SUCCESS_CODE = "0";
    /**
     * 正确返回消息 / fail
     */
    public static final String SUCCESS_MESSAGE = "success";

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> Result<T> success() {
        return success(SUCCESS_MESSAGE);
    }

    //? 成功

    public static <T> Result<T> success(String message) {
        return success(message, null);
    }

    public static <T> Result<T> success(T data) {
        return success(SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(SUCCESS_CODE);
        Result.setMessage(message);
        Result.setData(data);
        return Result;
    }

    public static <T> Result<T> fail() {
        return fail("fail");
    }


    //? 失败

    public static <T> Result<T> fail(String message) {
        return fail("-1", message);
    }

    public static <T> Result<T> fail(String message, T data) {
        return fail("-1", message, data);
    }

    public static <T> Result<T> fail(String code, String message) {
        return fail(code, message, null);
    }

    public static <T> Result<T> fail(String code, String message, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(code);
        Result.setMessage(message);
        Result.setData(data);
        return Result;
    }

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }


}
