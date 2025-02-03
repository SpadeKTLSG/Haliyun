package xyz.spc.serve.auxiliary.config.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义Spring事件对象
 */
@Data
@AllArgsConstructor
public class SkEvent<T> {

    private String message;

    private T data;
}
