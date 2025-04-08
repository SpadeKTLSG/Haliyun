package xyz.spc.infra.special.Data.hdfs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.util.hdfsUtil.HdfsFuncUtil;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HdfsRepo {

    //? 主要对工具类进行封装. 业务层代码统一调用这个方法 (做业务导向的封装)


    /**
     * HDFS 工具类
     */
    private final HdfsFuncUtil util;


    /**
     * 判断HDFS的存活性
     */
    public boolean isHDFSAlive() {

        try (FileSystem tmp = Optional.ofNullable(HdfsFuncUtil.getDfs())
                .orElseThrow(() -> new ServiceException("系统出问题啦!"))) {

            // 确认存在
            if (tmp == null) {
                return false;
            }

        } catch (IOException e) {
            return false;
        }

        return true;
    }

}
