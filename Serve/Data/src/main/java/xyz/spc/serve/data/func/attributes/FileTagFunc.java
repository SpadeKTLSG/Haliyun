package xyz.spc.serve.data.func.attributes;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.snowflake.SnowflakeIdUtil;
import xyz.spc.domain.dos.Data.attributes.FileTagDO;
import xyz.spc.infra.special.Data.attributes.AttributesRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileTagFunc {

    /**
     * Repo
     */
    private final AttributesRepo attributesRepo;


    /**
     * id 查找
     */
    public FileTagDO getById(Long tagId) {
        return attributesRepo.fileTagService.getById(tagId);
    }

    /**
     * 创建标签
     */
    public Long createTag(FileTagDO toInsert) {

        Long id = SnowflakeIdUtil.nextId();
        toInsert.setId(id);

        attributesRepo.fileTagService.save(toInsert);

        return id;
    }


    /**
     * 删除标签 (没有逻辑删除)
     */
    public void deleteTag(Long tagId) {
        attributesRepo.fileTagService.removeById(tagId);
    }


    /**
     * 更新标签 为 指定状态
     */
    public void updateTag4Status(Long tagId, int statusPause) {
        attributesRepo.fileTagService.update(Wrappers.lambdaUpdate(FileTagDO.class)
                .eq(FileTagDO::getId, tagId)
                .set(FileTagDO::getStatus, statusPause));
    }
}
