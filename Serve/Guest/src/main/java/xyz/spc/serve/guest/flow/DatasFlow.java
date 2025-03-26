package xyz.spc.serve.guest.flow;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.gate.vo.Data.files.FileVO;
import xyz.spc.gate.vo.Group.groups.GroupVO;
import xyz.spc.gate.vo.Group.interacts.PostVO;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatasFlow {

    //todo 等群组和文件的功能实现

    public PostVO getUserDataOfPost(@NotNull Long id) {
        return null;
    }


    public FileVO getUserDataOfFile(@NotNull Long id) {
        return null;
    }

    public GroupVO getUserDataOfGroup(@NotNull Long id) {
        return null;
    }
}
