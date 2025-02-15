package xyz.spc.domain.model.Guest.users;


import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;


/**
 * 用户群组关联
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup extends BaseModel {


    private Long id;

    private Long userId;

    private Long groupId;

    private String collect;

    private Integer sort;


    //? 转换器

    //! 基础信息
}
