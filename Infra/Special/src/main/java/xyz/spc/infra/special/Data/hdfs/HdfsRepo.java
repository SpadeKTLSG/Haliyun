package xyz.spc.infra.special.Data.hdfs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.exception.ServiceException;
import xyz.spc.common.util.hdfsUtil.HdfsFuncUtil;
import xyz.spc.common.util.hdfsUtil.HdfsIOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HdfsRepo {

    //? 主要对工具类进行封装. 业务层代码统一调用这个方法 (做业务导向的封装)


    // HdfsFuncUtil util;


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


    /**
     * 本地磁盘文件导入到HDFS, InputStream模式
     */
    public boolean upload2HDFS(String tagetPath, InputStream is) throws Exception {

        try {
            HdfsIOUtil.upByIS(tagetPath, is);
        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
