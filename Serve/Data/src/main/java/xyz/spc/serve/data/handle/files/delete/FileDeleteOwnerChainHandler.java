package xyz.spc.serve.data.handle.files.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.spc.common.funcpack.errorcode.ClientError;
import xyz.spc.common.funcpack.exception.ClientException;
import xyz.spc.domain.model.Data.files.File;
import xyz.spc.gate.dto.Data.files.FileDTO;

/**
 * 文件删除校验用户责任链处理器
 */
@Component
@RequiredArgsConstructor
public class FileDeleteOwnerChainHandler implements FileDeleteChainFilter<File, FileDTO> {


    @Override
    public void handler(File file, FileDTO fileDTO) {

        // 需要判断用户是否有权限删除文件 : 默认仅为 上传者 / 用户 才可以
        boolean auth = false;


        if (!auth) {
            throw new ClientException(ClientError.USER_AUTH_ERROR);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
