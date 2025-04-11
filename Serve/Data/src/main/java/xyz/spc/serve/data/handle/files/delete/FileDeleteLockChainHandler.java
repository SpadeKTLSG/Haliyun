package xyz.spc.serve.data.handle.files.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Data.files.File;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.serve.data.func.files.FilesFunc;

/**
 * 文件删除校验锁责任链处理器
 */
@Component
@RequiredArgsConstructor
public class FileDeleteLockChainHandler implements FileDeleteChainFilter<File, FileGreatVO> {

    private final FilesFunc filesFunc;

    @Override
    public void handler(File file, FileGreatVO fileGreatVO) {

        // 需要判断对应文件上是否有锁存在
        boolean auth = false;

        // todo
        auth = true;

        if (!auth) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
