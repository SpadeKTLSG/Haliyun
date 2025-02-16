package xyz.spc.infra.special.Guest.levels;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Guest.levels.LevelMapper;
import xyz.spc.infra.repo.Guest.levels.LevelService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class LevelsRepo {

    public final LevelService levelService;
    public final LevelMapper levelMapper;
}
