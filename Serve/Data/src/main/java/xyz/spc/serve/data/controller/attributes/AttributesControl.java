package xyz.spc.serve.data.controller.attributes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.AttributesFlow;

@Slf4j
@MLog
@Tag(name = "Files", description = "文件属性合集")
@RequestMapping("/Data/attributes")
@RestController
@RequiredArgsConstructor
public class AttributesControl {

    // Flow
    private final AttributesFlow attributesFlow;

    //! Client


    //! Func

    /**
     * 通过标签来查询该标签下的我的文件, 做列表展示即可
     */


    //! ADD

    /**
     * 新增对应文件的标签 (创建文件默认调用创建空标签)
     */
    @PostMapping("/tag/{fileId}/{tagName}")
    public Result<Object> addMyTag(
            @PathVariable Long fileId,
            @PathVariable String tagName
    ) {
        attributesFlow.addTag(fileId, tagName);
        return Result.success();
    }


    //! DELETE

    /**
     * 删除某个文件的标签
     */
    @DeleteMapping("/tag/{fileId}/{tagName}")
    public Result<Object> deleteMyTag(
            @PathVariable Long fileId,
            @PathVariable String tagName
    ) {
        attributesFlow.deleteTag(fileId, tagName);
        return Result.success();
    }


    //! UPDATE

    /**
     * 更新某个文件的标签 (add + delete)
     */
    @PutMapping("/tag/{fileId}/{tagName}")
    public Result<Object> updateMyTag(
            @PathVariable Long fileId,
            @PathVariable String tagName
    ) {
        attributesFlow.updateTag(fileId, tagName);
        return Result.success();
    }


    //! Query

    /**
     * 查询对应文件id的指向的标签
     */
    @GetMapping("/tag")
    public Result<FileTagVO> getMyAllTags(
            @RequestParam Long fileId
    ) {
        return Result.success(attributesFlow.getFileTag(fileId));
    }


}
