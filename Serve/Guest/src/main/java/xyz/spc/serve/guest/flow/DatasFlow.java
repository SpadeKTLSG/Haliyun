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
import xyz.spc.infra.feign.Group.InteractsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //Feign
    private final InteractsClient interactsClient;


    //Func


    //! Client

    public CollectCountVO getUserDataOfAllCollect(@NotNull Long id) {
        return null;
    }

    public PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {
        return interactsClient.getUserDataOfPost(id, pageRequest);
    }


    public PageResponse<FileShowVO> getUserDataOfFile(@NotNull Long id, PageRequest pageRequest) {
        return null;
    }

    public PageResponse<GroupVO> getUserDataOfGroup(@NotNull Long id, PageRequest pageRequest) {
        return null;
    }

}
