package xyz.spc.gate.vo.Guest.users;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.gate.vo.BaseVO;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserGreatVO extends BaseVO {

    //! id
    private Long id;

    //    UserVO

    private Integer admin;

    private Integer status;

    private Integer loginType;

    private String account;

    private String password;


    //    UserDetailVO


    private Integer gender;

    private String phone;

    private String email;

    private String avatar;

    private String area;

    private String nickname;

    private String introduce;


    //    UserFuncVO

    private Long levelId;

    private Integer vip;

    private Integer createClusterCount;

    private Integer createClusterMax;

    private Integer joinClusterCount;

    private Integer joinClusterMax;

    private Long coin;

    private Long energyCoin;

    private String registerCode;

    // 临时字段

    /**
     * 用户加入的群组名
     */
    private List<String> groupNames;

    /**
     * 等级层级
     */
    private Integer levelFloor;

    /**
     * 等级名称
     */
    private String levelName;
}
