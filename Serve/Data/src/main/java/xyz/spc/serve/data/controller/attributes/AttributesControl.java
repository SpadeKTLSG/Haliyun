package xyz.spc.serve.data.controller.attributes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.spc.common.funcpack.Result;
import xyz.spc.gate.vo.Data.attributes.FileTagVO;
import xyz.spc.gate.vo.Data.files.FileVO;
import xyz.spc.serve.auxiliary.config.log.MLog;
import xyz.spc.serve.data.flow.AttributesFlow;

import java.util.List;

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
     * 通过标签来查询该标签下的所有文件, 做 FileVO 的列表展示即可
     */
    @GetMapping("/tag/file4tag")
    public Result<List<FileVO>> getAllMyFilesByTag(
            @RequestParam Long tagId
    ) {
        return Result.success(attributesFlow.getFilesByTag(tagId));
    }


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
    @DeleteMapping("/tag/{tagId}")
    public Result<Object> deleteMyTag(
            @PathVariable Long tagId
    ) {
        attributesFlow.deleteTag(tagId);
        return Result.success();
    }


    //! UPDATE


    /**
     * 重命名一个标签 (add + delete) | 不支持单单修改一个文件的标签
     */
    @PutMapping("/tag/{tagId}/{tagName}")
    public Result<Object> updateTag(
            @PathVariable Long tagId,
            @PathVariable String tagName
    ) {
        attributesFlow.updateTag(tagId, tagName);
        return Result.success();
    }

    /**
     * 暂停一个标签
     */
    @PutMapping("/tag/pause/{tagId}")
    public Result<Object> pauseTag(
            @PathVariable Long tagId) {
        attributesFlow.pauseTag(tagId);
        return Result.success();
    }

    /**
     * 冻结一个标签
     */
    @PutMapping("/tag/freeze/{tagId}")
    public Result<Object> freezeTag(
            @PathVariable Long tagId) {
        attributesFlow.freezeTag(tagId);
        return Result.success();
    }


    /**
     * 正常化一个标签
     */
    @PutMapping("/tag/normal/{tagId}")
    public Result<Object> normalTag(
            @PathVariable Long tagId) {
        attributesFlow.normalTag(tagId);
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
