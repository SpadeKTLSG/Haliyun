package xyz.spc.serve.data.controller.files;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.serve.auxiliary.config.log.MLog;

@Slf4j
@MLog
@Tag(name = "Files", description = "文件文件合集")
@RequestMapping("/Guest/users/userslevel")
@RestController
@RequiredArgsConstructor
public class FilesControl {
}
