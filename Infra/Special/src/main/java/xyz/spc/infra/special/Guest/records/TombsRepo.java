package xyz.spc.infra.special.Guest.records;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.records.TombMapper;
import xyz.spc.infra.repo.Guest.records.TombService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class TombsRepo {

    public final TombService tombService;
    public final TombMapper tombMapper;
}
