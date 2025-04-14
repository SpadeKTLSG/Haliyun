package xyz.spc.serve.data.func.attributes;


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
}
