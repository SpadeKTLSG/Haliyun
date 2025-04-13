package xyz.spc.domain.model.Guest.records;

import lombok.*;
import lombok.experimental.Accessors;
import xyz.spc.domain.model.BaseModel;

/**
 * 统计
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Statistics extends BaseModel {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 累计评论次数
     */
    private Integer comment;

    /**
     * 累计下载次数
     */
    private Integer download;

    /**
     * 累计上传次数
     */
    private Integer upload;

    /**
     * 累计活动参加次数
     */
    private Integer outlet;

    /**
     * 累计发私信次数
     */
    private Integer mail;

    /**
     * 累计收藏次数
     */
    private Integer collect;

    /**
     * 累计点赞次数
     */
    private Integer like;

    /**
     * 累计干坏事次数
     */
    private Integer trick;


    /**
     * 统计的目标字段枚举
     */
    @Getter
    public enum StatisticsField {

        COMMENT("comment"),
        DOWNLOAD("download"),
        UPLOAD("upload"),
        OUTLET("outlet"),
        MAIL("mail"),
        COLLECT("collect"),
        LIKE("like"),
        TRICK("trick");

        private final String field;

        StatisticsField(String field) {
            this.field = field;
        }

    }


    /**
     * 判断对应的String是否属于上面的目标字段枚举
     */
    public boolean isFieldName(String fieldName) {
        for (StatisticsField field : StatisticsField.values()) {
            if (field.getField().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

}
