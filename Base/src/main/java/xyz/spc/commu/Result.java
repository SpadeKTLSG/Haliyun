package xyz.spc.commu;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "0";
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

    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }


}
