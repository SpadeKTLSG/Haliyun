package xyz.spc.serve.data.handle.files.delete;

import xyz.spc.domain.model.Data.files.File;
import xyz.spc.gate.vo.Data.files.FileGreatVO;
import xyz.spc.serve.auxiliary.config.design.chain.AbstractChainHandler;
import xyz.spc.serve.data.common.enums.FilesChainMarkEnum;

/**
 * 文件删除责任链过滤器
 */
public interface FileDeleteChainFilter<T extends File, Y extends FileGreatVO> extends AbstractChainHandler<T, Y> {

    @Override
    default String mark() {
        return FilesChainMarkEnum.FILE_DELETE_FILTER.name();
    }
}
