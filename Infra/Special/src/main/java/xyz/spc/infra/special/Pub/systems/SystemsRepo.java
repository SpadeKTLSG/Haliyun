package xyz.spc.infra.special.Pub.systems;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Pub.systems.DictionaryMapper;
import xyz.spc.infra.repo.Pub.systems.DictionaryService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class SystemsRepo {

    public final DictionaryService dictionaryService;
    public final DictionaryMapper dictionaryMapper;

}
