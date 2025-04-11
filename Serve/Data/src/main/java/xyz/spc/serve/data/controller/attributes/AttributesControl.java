package xyz.spc.serve.data.controller.attributes;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;

@Slf4j
@MLog
@Tag(name = "Files", description = "文件属性合集")
@RequestMapping("/Data/attributes")
@RestController
@RequiredArgsConstructor
public class AttributesControl {

    // Flow
    // private final AttributesFlow attributesFlow;

    //! Client


    //! Func

    //! ADD


    //! DELETE


    //! UPDATE



    //! Query

}
