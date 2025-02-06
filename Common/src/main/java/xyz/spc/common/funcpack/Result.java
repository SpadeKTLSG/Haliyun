package xyz.spc.common.funcpack;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import xyz.spc.common.constant.ReqRespCT;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {


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


    //? 成功

    public static <T> Result<T> success() {
        return success(null, ReqRespCT.SUCCESS_MESSAGE);
    }

    public static <T> Result<T> success(T data) {
        return success(data, ReqRespCT.SUCCESS_MESSAGE);
    }

    public static <T> Result<T> success(T data, String message) {
        Result<T> Result = new Result<>();
        Result.setCode(ReqRespCT.SUCCESS_CODE);
        Result.setMessage(message);
        Result.setData(data);
        return Result;
    }


    //? 失败

    public static <T> Result<T> fail() {
        return fail(ReqRespCT.FAIL_MESSAGE, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(message, null);
    }

    public static <T> Result<T> fail(String message, T data) {
        Result<T> Result = new Result<>();
        Result.setCode(ReqRespCT.FAIL_CODE);
        Result.setMessage(message);
        Result.setData(data);
        return Result;
    }


    public boolean isSuccess() {
        return ReqRespCT.SUCCESS_CODE.equals(code);
    }


}
