package xyz.spc.serve.guest.flow;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Data.files.FileShowVO;
import xyz.spc.gate.vo.Group.groups.GroupVO;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;
import xyz.spc.gate.vo.Guest.datas.CollectCountVO;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //todo 等群组和文件的功能实现


    public CollectCountVO getUserDataOfAllCollect(@NotNull Long id) {
        return null;
    }

    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        return null;
    }


    public PageResponse<FileShowVO> getUserDataOfFile(@NotNull Long id, PageRequest pageRequest) {
        return null;
    }

    public PageResponse<GroupVO> getUserDataOfGroup(@NotNull Long id, PageRequest pageRequest) {
        return null;
    }

}
