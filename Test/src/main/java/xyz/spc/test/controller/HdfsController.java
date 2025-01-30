package xyz.spc.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.spc.common.funcpack.commu.Result;

/**
 * Create by yandan
 * 2022/2/24  12:55
 */
@RestController
public class HdfsController {


    @GetMapping("/hdfs/get")
    @ResponseBody
    public Result getHDFSInfo() {

        return Result.success();

    }
}
