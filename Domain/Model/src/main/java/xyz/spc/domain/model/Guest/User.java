package xyz.spc.domain.model.Guest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 用户 Model
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseModel {

}
