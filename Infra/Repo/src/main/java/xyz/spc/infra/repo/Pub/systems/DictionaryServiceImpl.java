package xyz.spc.infra.repo.Pub.systems;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Pub.systems.DictionaryDO;
import xyz.spc.infra.mapper.Pub.systems.DictionaryMapper;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, DictionaryDO> implements DictionaryService {
}
