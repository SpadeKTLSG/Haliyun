package xyz.spc.infra.special.Guest.datas;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.datas.CollectMapper;
import xyz.spc.infra.repo.Guest.datas.CollectService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class CollectsRepo {

    public final CollectService collectService;
    public final CollectMapper collectMapper;
}
