package xyz.spc.infra.special.Pub.compos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Pub.compos.NaviCompoMapper;
import xyz.spc.infra.repo.Pub.compos.NaviCompoService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class ComposRepo {

    public final NaviCompoService naviCompoService;
    public final NaviCompoMapper naviCompoMapper;
}
