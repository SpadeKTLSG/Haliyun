package xyz.spc.infra.repo.Guest.levels;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.spc.domain.dos.Guest.levels.LevelDO;
import xyz.spc.infra.mapper.Guest.levels.LevelMapper;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl extends ServiceImpl<LevelMapper, LevelDO> implements LevelService {
}
