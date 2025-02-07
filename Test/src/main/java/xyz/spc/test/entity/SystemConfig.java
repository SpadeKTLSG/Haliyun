package xyz.spc.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置 entity
 *
 * @author zhaojun
 */
@Data
@TableName(value = "system_config")
public class SystemConfig implements Serializable {

    public static final String DIRECT_LINK_PREFIX_NAME = "directLinkPrefix";

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;


    @TableField(value = "name")

    private String name;


    @TableField(value = "`value`")

    private String value;


    @TableField(value = "title")
    private String title;

}
