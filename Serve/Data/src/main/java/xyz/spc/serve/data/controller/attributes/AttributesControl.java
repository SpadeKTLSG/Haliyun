package xyz.spc.serve.data.controller.attributes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * 新增标签
     */


    //! DELETE

    /**
     * 删除标签
     */


    //! UPDATE

    /**
     * 更新标签
     */



    //! Query

    /**
     * 查询自己所有标签
     */



}
