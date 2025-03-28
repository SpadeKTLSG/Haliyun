package xyz.spc.serve.guest.func.datas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.datas.CollectDO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectFunc {




    public List<CollectDO> getUserCollectListOfPost(Long userId) {
    }
}
