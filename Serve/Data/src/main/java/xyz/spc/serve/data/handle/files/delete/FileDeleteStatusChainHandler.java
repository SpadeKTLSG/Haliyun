package xyz.spc.serve.data.handle.files.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Data.files.File;
import xyz.spc.domain.model.Data.files.FileFunc;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.serve.data.func.files.FilesFunc;

/**
 * 文件删除校验状态责任链处理器
 */
@Component
@RequiredArgsConstructor
public class FileDeleteStatusChainHandler implements FileDeleteChainFilter<File, FileGreatVO> {

    private final FilesFunc filesFunc;

    @Override
    public void handler(File file, FileGreatVO fileGreatVO) {

        // 需要判断对应文件状态是否正常
        boolean stable;

        stable = fileGreatVO.getStatus() == FileFunc.STATUS_NORMAL;


        if (!stable) {
            throw new ClientException("文件状态异常 - 无法删除");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
